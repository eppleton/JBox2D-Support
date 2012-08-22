/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d;

import com.google.protobuf.TextFormat;
import com.google.protobuf.TextFormat.ParseException;
import org.box2d.proto.Box2D;
import org.jbox2d.dynamics.World;
import org.jbox2d.serialization.pb.PbDeserializer;
import org.jbox2d.serialization.pb.PbSerializer;
import org.openide.util.Exceptions;

/**
 *
 * @author antonepple
 */
public final class WorldUtilities {

    private WorldUtilities() {
    }

    public static String serializeWorld(final World world) {
        final PbSerializer serializer = new PbSerializer();
        final Box2D.PbWorld.Builder serializeWorld = serializer.serializeWorld(world);
        return serializeWorld.build().toString();
    }

    public static World parseWorld(final String worldDescription) {
        World world = null;
        try {
            final Box2D.PbWorld.Builder builder = Box2D.PbWorld.newBuilder();
            TextFormat.merge(worldDescription, builder);
            final Box2D.PbWorld pbWorld = builder.build();
            final PbDeserializer deserializer = new PbDeserializer();
            world = deserializer.deserializeWorld(pbWorld);
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        return world;
    }

    public static World copy(final World world) {
        final String worldString = serializeWorld(world);
        return parseWorld(worldString);
    }
}
