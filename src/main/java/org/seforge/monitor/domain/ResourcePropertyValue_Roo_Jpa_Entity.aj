// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.seforge.monitor.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Version;
import org.seforge.monitor.domain.ResourcePropertyValue;

privileged aspect ResourcePropertyValue_Roo_Jpa_Entity {
    
    declare @type: ResourcePropertyValue: @Entity;
    
    @Version
    @Column(name = "version")
    private Integer ResourcePropertyValue.version;
    
    public Integer ResourcePropertyValue.getVersion() {
        return this.version;
    }
    
    public void ResourcePropertyValue.setVersion(Integer version) {
        this.version = version;
    }
    
}
