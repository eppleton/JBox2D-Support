/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d;

import com.google.protobuf.TextFormat;
import org.box2d.proto.Box2D;
import org.jbox2d.dynamics.World;
import org.jbox2d.serialization.pb.PbDeserializer;
import org.jbox2d.serialization.pb.PbSerializer;

/**
 *
 * @author antonepple
 */
public class WorldUtilities {

    public static String serializeWorld(World world) {
        PbSerializer s = new PbSerializer();
        Box2D.PbWorld.Builder serializeWorld = s.serializeWorld(world);
        return serializeWorld.build().toString();
    }

    public static World parseWorld(String worldDescription) {
        World deserializedWorld = null;
        try {
            final Box2D.PbWorld.Builder builder = Box2D.PbWorld.newBuilder();
            TextFormat.merge(worldDescription, builder);
            Box2D.PbWorld pbWorld = builder.build();
            PbDeserializer d = new PbDeserializer();
            deserializedWorld = d.deserializeWorld(pbWorld);
        } catch (Exception ex) {
            // happens a lot, is ok, ignore
            //Exceptions.printStackTrace(ex);
        }
        return deserializedWorld;
    }
    
    public static World copy(World world){
        String worldString = serializeWorld(world);
        return parseWorld(worldString);
    }
}
