/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.counter.osm;

import org.openstreetmap.osmosis.core.domain.v0_6.Tag;

/**
 *
 * @author WJ293NU
 */
public class TagWrapper extends Tag{
    
    public TagWrapper(Tag tag){
        super(tag.getKey(), tag.getValue());
    }
    
    public boolean equals(Object obj){
        boolean isEquals = false;
        Tag tag;
        if(obj instanceof Tag){
            tag = (Tag)obj;
            isEquals = tag.getKey().equals(this.getKey());
        }
        return isEquals;
    }
}
