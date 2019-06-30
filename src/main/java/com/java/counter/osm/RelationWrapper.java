/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.counter.osm;

import java.util.Map.Entry;

import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.RelationMember;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;

/**
 *
 * @author WJ293NU
 */
public class RelationWrapper {
    
    private final Relation mEntity;
    
    public RelationWrapper(Relation entity){
        this.mEntity  = entity;
    }
    
    /**
     * To find if a particular tag is present
     * 
     * @param key
     * @return
     */
    public boolean containsKey(String key){
        boolean containsKey = false;
        if(this.mEntity != null){
            containsKey = this.mEntity.getTags().stream().anyMatch((tag) -> (tag.getKey().equals(key)));
        }
        return containsKey;
    }
    
    /**
     * Check if the current relation is a regional train
     * 
     * @return
     */
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
    
    /**
     * Get printable version of a relation
     * 
     * @return
     */
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
    	//jsonString.deleteCharAt(jsonString.length() - 2);
    	jsonString.append(quote)
    	.append("via\" :")
    	.append(quote)
    	.append(getViaStations())
    	.append(quote).append(comma)
    	.append(line)
    	.append("\"viaIDs\" :")
    	.append(quote)
    	.append(getViaIds())
    	.append(quote)
    	.append("},");
    	return jsonString.toString();
    }
    
    /**
     * Get Node name from node object. This should be inside NodeUtils soon.
     * 
     * @param node
     * @return
     */
    private String getNodeName(Node node) {
    	for(Tag tag: node.getTags()) {
    		if(tag.getKey().equals("name")) {
    			return tag.getValue();
    		}
    	}
    	return "";
    	
    }
    
    /**
     * Get all station names* (if available) between source and destination
     * 
     * @return
     */
	private String getViaStations() {
		StringBuilder viaStations = new StringBuilder("");
		
		Relation relation =  (Relation)this.mEntity;
		for (RelationMember member: relation.getMembers()) {
			
			if(member.getMemberRole().contains("stop")) {
				Node node = OsmPbfReader.getNodeForId(member.getMemberId());
				if(node != null) {
					viaStations.append(getNodeName(node))
					.append(",");
				}
			}
		}
		if(viaStations.length() > 0) {
			viaStations.deleteCharAt(viaStations.length() - 1);
		}
		return viaStations.toString();
	}
	
	/**
	 * Get IDs of the intermediate stops in this route
	 * 
	 * @return
	 */
	private String getViaIds() {
		StringBuilder viaStationIds = new StringBuilder("");
		
		Relation relation =  (Relation)this.mEntity;
		for (RelationMember member: relation.getMembers()) {
			viaStationIds.append(member.getMemberId())
			.append(",");
		}
		if(viaStationIds.length() > 0) {
			viaStationIds.deleteCharAt(viaStationIds.length() - 1);
		}
		return viaStationIds.toString();
	}
    
    
}
