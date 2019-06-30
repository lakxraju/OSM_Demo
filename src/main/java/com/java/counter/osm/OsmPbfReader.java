/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.counter.osm;

import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;
import org.openstreetmap.osmosis.pbf2.v0_6.PbfReader;
 
import java.io.File;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
 
public class OsmPbfReader {
 
    public static void main(String[] args) {
        File osmFile = new File(".\\src\\resources\\berlin-latest.osm.pbf");
        PbfReader reader = new PbfReader(osmFile, 1);
 
        AtomicInteger numberOfNodes = new AtomicInteger();
        AtomicInteger numberOfWays = new AtomicInteger();
        AtomicInteger numberOfRelations = new AtomicInteger();
        AtomicInteger numberOfTrainRelations = new AtomicInteger();
 
        Sink sinkImplementation = new Sink() {
 
            public void process(EntityContainer entityContainer) {
 
                Entity entity = entityContainer.getEntity();
                if (entity instanceof Node) {
                    numberOfNodes.incrementAndGet();
                } else if (entity instanceof Way) {
                    numberOfWays.incrementAndGet();
                } else if (entity instanceof Relation) {
                    numberOfRelations.incrementAndGet();
                }
                
                EntityWrapper wrapper = new EntityWrapper(entity);
                if(wrapper.isTrainStation()){
                    numberOfTrainRelations.incrementAndGet();
                    System.out.println(entity.getType() + "--" );
                    for(Tag tag:entity.getTags()){
                        System.out.println(tag.getKey() + " --> " + tag.getValue());
                    }
                    for(Map.Entry entry : entity.getMetaTags().entrySet()){
                        System.out.println(entry.getKey() + "-->" + entry.getValue());
                    }
                    Node node = (Node) entity;
                    
                    //System.out.println(entity.getTags());
                }
            }
 
            public void initialize(Map<String, Object> arg0) {
            }
 
            public void complete() {
            }
 
            public void release() {
            }

            @Override
            public void close() {
            }
 
        };
        
        reader.setSink(sinkImplementation);
        reader.run();
 
        System.out.println(numberOfNodes.get() + " Nodes are found.");
        System.out.println(numberOfWays.get() + " Ways are found.");
        System.out.println(numberOfRelations.get() + " Relations are found.");
        System.out.println(numberOfTrainRelations + " train routes are found.");
    }
 
}
