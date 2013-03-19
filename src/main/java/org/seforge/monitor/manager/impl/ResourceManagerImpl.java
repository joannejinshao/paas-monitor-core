package org.seforge.monitor.manager.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hyperic.hq.hqapi1.ResourceApi;
import org.hyperic.hq.hqapi1.types.ResourcePrototype;
import org.hyperic.hq.hqapi1.types.ResourceResponse;
import org.hyperic.hq.hqapi1.types.ResponseStatus;
import org.seforge.monitor.common.ResourceType;
import org.seforge.monitor.domain.Resource;
import org.seforge.monitor.domain.ResourceGroup;
import org.seforge.monitor.exception.NotMonitoredException;
import org.seforge.monitor.hqapi.HQProxy;
import org.seforge.monitor.manager.ResourceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResourceManagerImpl implements ResourceManager{
	
	@Autowired
	private HQProxy proxy;

	@Transactional
	public List<Resource> getAllVims() {
		return Resource.findResourcesByTypeIdEquals(ResourceType.OPERATING_SYSTEM).getResultList();		
	}
	
	@Transactional
	public List<Resource> getAllPhyms() {
		List<Resource> phyms = Resource.findResourcesByTypeIdEquals(ResourceType.PHYSICAL_MACHINE).getResultList();	
		for(Resource phym : phyms){
			phym.setChildrenCount(phym.getChildren().size());
		}
		return phyms;
	}
	
	@Transactional
	public List<Resource> getPhymsByGroup(int groupId){
		List<Resource> resources = Resource.findResourcesByTypeIdEquals(ResourceType.PHYSICAL_MACHINE).getResultList();
		List<Resource> result = new ArrayList<Resource>();
		for(Resource r : resources){
			for(ResourceGroup rg : r.getResourceGroups()){
				if(rg.getId() == groupId){
					result.add(r);
				}					
			}			
		}
		return result;
	}
	
	@Transactional
	public List<Resource> getAppServersByGroup(int groupId){
		List<Resource> resources = Resource.findResourcesByTypeIdEquals(ResourceType.APPLICATION_SERVER).getResultList();
		List<Resource> result = new ArrayList<Resource>();
		for(Resource r : resources){
			for(ResourceGroup rg : r.getResourceGroups()){
				if(rg.getId() == groupId){
					result.add(r);
				}					
			}			
		}
		return result;
	}

	@Override
	@Transactional
	public Integer addNewServer(String ip, String jmxPort, String path, String serviceName, String groupId, String prototype) throws IOException, NotMonitoredException {
		// TODO 
		//先调用proxy.createServerResource把resource通过hqapi添加到hyperic server中，参见test中CreateTomcatServer.java中的内容
		//如果成功，获取相关的hyperic端resource信
		//根据得到的信息，在数据库中存入该resource
		String jmxUrl = "service:jmx:rmi:///jndi/rmi://localhost:"+ jmxPort + "/jmxrmi";
		
		org.hyperic.hq.hqapi1.types.Resource vimHQ = proxy.getVimResource(ip, false, false);
		List l = Resource.findResourcesByResourceIdEquals(vimHQ.getId()).getResultList();
		Resource vim;
		if (l.size() == 0){
			vim = new Resource(vimHQ);
			vim.persist();
		}else{
			vim = (Resource)l.get(0);
		}		
		
		ResourceGroup rg = ResourceGroup.findResourceGroup(Integer.parseInt(groupId));
		if (rg == null) {
			rg = new ResourceGroup();
			rg.setId(Integer.parseInt(groupId));
		}
		
		ResourcePrototype resourcePrototype = proxy.getHQApi().getResourceApi().getResourcePrototype(prototype).getResourcePrototype();
		Map<String,String> configs = new HashMap<String,String>();
		configs.put("jmx.url", jmxUrl);		 
		configs.put("process.query", "Pid.Service.eq="+ serviceName);		
		configs.put("service_name", serviceName);
		String name = prototype + " " + jmxPort;
		ResourceResponse response = proxy.getHQApi().getResourceApi().createServer(resourcePrototype, vimHQ, name, configs);
		if (response.getStatus() == ResponseStatus.SUCCESS) {
			org.hyperic.hq.hqapi1.types.Resource newServer = response.getResource();
			
			ResourceApi resourceApi = proxy.getHQApi().getResourceApi();
			int size = 0;
			org.hyperic.hq.hqapi1.types.Resource fullServer = null;
			//hq端添加资源后，并不能马上把子资源也添加进去，需要等待添加好之后再保存相应的子资源到我们的数据库
			while(size == 0){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fullServer = resourceApi.getResource(newServer.getId(), true, true).getResource();
				size = fullServer.getResource().size();
			}			
			Resource resource = proxy.saveResource(fullServer, vim, true);
			Set<Resource> children = resource.getChildren();			
			rg.getResources().add(resource);
			rg.getResources().addAll(children);			
			rg.persist();
			
			return resource.getId();
		}
		else
		{
			System.out.println(response.getStatus().name());
			System.out.println(response.getError().getReasonText());
			return new Integer(-1);
		}
			
	}
	


	@Override
	@Transactional
	public void deleteServer(String id) throws NumberFormatException, IOException {
		Resource resource = Resource.findResource(Integer.parseInt(id));
		ResourceApi ra = proxy.getHQApi().getResourceApi();
		Set<Resource> children = resource.getChildren();
		ra.deleteResource(resource.getResourceId());
		Iterator<Resource> it = children.iterator();
		while (it.hasNext()) {
			Resource r = (Resource)it.next();
			ra.deleteResource(resource.getResourceId());
			r.remove();
		}
		resource.remove();
	}

}
