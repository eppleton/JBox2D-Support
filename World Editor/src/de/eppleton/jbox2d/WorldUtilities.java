/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d;

import com.google.protobuf.TextFormat;
import com.google.protobuf.TextFormat.ParseException;
import java.awt.Point;
import java.util.ArrayList;
import org.box2d.proto.Box2D;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.serialization.pb.PbDeserializer;
import org.jbox2d.serialization.pb.PbSerializer;

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
            // ignore
            //  Exceptions.printStackTrace(ex);
        }
        return world;
    }

    public static World copy(final World world) {
        final String worldString = serializeWorld(world);
        return parseWorld(worldString);
    }

    public static int worldToScene(float value, int scale, float offset, boolean invert) {
        return (int) ((value * (invert ? -1 : 1) + offset) * scale);
    }

    public static float sceneToWorld(int value, int scale, float offset, boolean invert) {
        return (((float) value / (float) scale) - offset) * (invert ? -1 : 1);
    }

   
    
    public static boolean ccw(Point A, Point B, Point C) {
        return (((A.x - C.x) * (B.y - C.y) - (A.y - C.y) * (B.x - C.x)) / 2) < 0;
    }

    public static boolean ccw(Vec2 A, Vec2 B, Vec2 C) {
        return (((A.x - C.x) * (B.y - C.y) - (A.y - C.y) * (B.x - C.x)) / 2) < 0;
    }
}
