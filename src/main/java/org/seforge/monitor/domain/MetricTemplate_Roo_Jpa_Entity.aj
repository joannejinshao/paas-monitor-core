// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.seforge.monitor.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Version;
import org.seforge.monitor.domain.MetricTemplate;

privileged aspect MetricTemplate_Roo_Jpa_Entity {
    
    declare @type: MetricTemplate: @Entity;
    
    @Version
    @Column(name = "version")
    private Integer MetricTemplate.version;
    
    public MetricTemplate.new() {
        super();
    }

    public Integer MetricTemplate.getVersion() {
        return this.version;
    }
    
    public void MetricTemplate.setVersion(Integer version) {
        this.version = version;
    }
    
}