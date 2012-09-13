/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import org.box2d.proto.Box2D;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.serialization.pb.PbDeserializer;

/**
 *
 * @author antonepple
 */
public class Deserializer extends PbDeserializer {

    public void addToWorld(World targetWorld, Box2D.PbWorld world, HashMap<Integer, Joint> jointMap, HashMap<Integer, Body> bodyMap) throws IOException {
        for (int i = 0; i < world.getBodiesCount(); i++) {
            Box2D.PbBody pbBody = world.getBodies(i);
            Body body = deserializeBody(targetWorld, pbBody);
            bodyMap.put(i, body);
        }

        // first pass, indep joints
        int cnt = 0;
        for (int i = 0; i < world.getJointsCount(); i++) {
            Box2D.PbJoint pbJoint = world.getJoints(i);
            if (isIndependentJoint(pbJoint.getType())) {
                Joint joint = deserializeJoint(targetWorld, pbJoint, bodyMap, jointMap);
                jointMap.put(cnt, joint);
                cnt++;
            }
        }

        // second pass, dep joints
        for (int i = 0; i < world.getJointsCount(); i++) {
            Box2D.PbJoint pbJoint = world.getJoints(i);
            if (!isIndependentJoint(pbJoint.getType())) {
                Joint joint = deserializeJoint(targetWorld, pbJoint, bodyMap, jointMap);
                jointMap.put(cnt, joint);
                cnt++;
            }
        }

    }

    private boolean isIndependentJoint(Box2D.PbJointType argType) {
        return argType != Box2D.PbJointType.GEAR && argType != Box2D.PbJointType.CONSTANT_VOLUME;
    }
}
