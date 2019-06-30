/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.counter.osm;

import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;

/**
 *
 * @author WJ293NU
 */
public class EntityWrapper {
    
    private final Entity mEntity;
    
    private final String routeString = "route";
    private final String routeMasterString = "route_master";
    
    public EntityWrapper(Entity entity){
        this.mEntity  = entity;
    }
    
    public boolean containsKey(String key){
        boolean containsKey = false;
        if(this.mEntity != null){
            containsKey = this.mEntity.getTags().stream().anyMatch((tag) -> (tag.getKey().equals(key)));
        }
        return containsKey;
    }
    
    public boolean isTrainStation(){
        if(this.containsKey("railway")){
            if (this.mEntity.getTags().stream().anyMatch((tag) -> ("railway".equalsIgnoreCase(tag.getKey()) && ("buffer_stop".equalsIgnoreCase(tag.getValue()))))) {
                return true;
            }
        }
        return false;
    }
    
}
