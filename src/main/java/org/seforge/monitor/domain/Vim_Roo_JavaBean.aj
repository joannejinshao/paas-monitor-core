// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.seforge.monitor.domain;

import org.seforge.monitor.domain.Vim;

privileged aspect Vim_Roo_JavaBean {
    
    public String Vim.getName() {
        return this.name;
    }
    
    public void Vim.setName(String name) {
        this.name = name;
    }
    
    public String Vim.getIp() {
        return this.ip;
    }
    
    public void Vim.setIp(String ip) {
        this.ip = ip;
    }
    
    public String Vim.getState() {
        return this.state;
    }
    
    public void Vim.setState(String state) {
        this.state = state;
    }
    
    public String Vim.getOs() {
        return this.os;
    }
    
    public void Vim.setOs(String os) {
        this.os = os;
    }
    
    public Integer Vim.getParentId() {
        return this.parentId;
    }
    
    public void Vim.setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    
}