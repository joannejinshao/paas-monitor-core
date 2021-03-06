// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.seforge.monitor.domain;

import org.seforge.monitor.domain.Condition;
import org.seforge.monitor.domain.Constraint;
import org.seforge.monitor.domain.ResourceGroup;
import org.seforge.monitor.domain.ResourcePrototype;

privileged aspect Constraint_Roo_JavaBean {
    
    public String Constraint.getName() {
        return this.name;
    }
    
    public void Constraint.setName(String name) {
        this.name = name;
    }
    
    public String Constraint.getDescription() {
        return this.description;
    }
    
    public void Constraint.setDescription(String description) {
        this.description = description;
    }
    
    public int Constraint.getPriority() {
        return this.priority;
    }
    
    public void Constraint.setPriority(int priority) {
        this.priority = priority;
    }
    
    public boolean Constraint.isActive() {
        return this.active;
    }
    
    public void Constraint.setActive(boolean active) {
        this.active = active;
    }
    
    public String Constraint.getOtherReceipts() {
        return this.otherReceipts;
    }
    
    public void Constraint.setOtherReceipts(String otherReceipts) {
        this.otherReceipts = otherReceipts;
    }
    
    public int Constraint.getAlertDefinitionId() {
        return this.alertDefinitionId;
    }
    
    public void Constraint.setAlertDefinitionId(int alertDefinitionId) {
        this.alertDefinitionId = alertDefinitionId;
    }
    
    public Condition Constraint.getCondition() {
        return this.condition;
    }
    
    public void Constraint.setCondition(Condition condition) {
        this.condition = condition;
    }
    
    public ResourcePrototype Constraint.getResourcePrototype() {
        return this.resourcePrototype;
    }
    
    public void Constraint.setResourcePrototype(ResourcePrototype resourcePrototype) {
        this.resourcePrototype = resourcePrototype;
    }
    
    public ResourceGroup Constraint.getResourceGroup() {
        return this.resourceGroup;
    }
    
    public void Constraint.setResourceGroup(ResourceGroup resourceGroup) {
        this.resourceGroup = resourceGroup;
    }
    
}
