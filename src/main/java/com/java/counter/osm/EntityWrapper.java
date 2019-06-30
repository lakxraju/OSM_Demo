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
    
    public boolean isRegionalTrain(){
        if(this.containsKey("name")){
            if (this.mEntity.getTags().stream().anyMatch((tag) -> ("name".contains(tag.getKey()) 
            		&& ((tag.getValue().contains("RE") && tag.getValue().contains(":")))
            		|| ((tag.getValue().contains("RB") && tag.getValue().contains(":")))
            		))) {
                return true;
            }
        }
        return false;
    }
    
    public String toString() {
    	StringBuilder builder = new StringBuilder("Entity :: ");
    	String tab = "\t";
    	for(Tag tag:this.mEntity.getTags()) {
    		builder.append(tag.getKey() + " -> " + tag.getValue() + tab);
    	}
    	return builder.toString();
    }
    
    public String getJsonString() {
    	String line = "\n";
    	String quote = "\"";
    	String comma = ",";
    	StringBuilder jsonString = new StringBuilder("{" + line);
    	for(Tag tag:this.mEntity.getTags()) {
    		jsonString.append(quote)
    		.append(tag.getKey())
    		.append(quote +":")
    		.append(quote)
    		.append(tag.getValue())
    		.append(quote).append(comma)
    		.append(line);
    	}
    	jsonString.deleteCharAt(jsonString.length() - 2);
    	jsonString.append("},");
    	return jsonString.toString();
    }
    
    
    
}
