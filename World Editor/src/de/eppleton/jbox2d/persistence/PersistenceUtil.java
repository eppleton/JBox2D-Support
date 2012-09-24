/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.persistence;

import java.util.HashMap;
import org.jbox2d.builders.GearJointBuilder;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.FrictionJointDef;
import org.jbox2d.dynamics.joints.GearJointDef;
import org.jbox2d.dynamics.joints.JointType;
import org.jbox2d.dynamics.joints.MouseJointDef;
import org.jbox2d.dynamics.joints.PrismaticJointDef;
import org.jbox2d.dynamics.joints.PulleyJointDef;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.jbox2d.dynamics.joints.WeldJointDef;

/**
 *
 * @author antonepple
 */
public class PersistenceUtil {

    public static org.jbox2d.dynamics.World getWorldFromJAXBWorld(World world) {
        HashMap<Body, org.jbox2d.dynamics.Body> bodyMap = new HashMap<Body, org.jbox2d.dynamics.Body>();
        HashMap<Joint, org.jbox2d.dynamics.joints.Joint> jointMap = new HashMap<Joint, org.jbox2d.dynamics.joints.Joint>();

        org.jbox2d.dynamics.World result = new org.jbox2d.dynamics.World(world.gravity);
        for (Body b : world.bodyList) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.active = b.active;
            bodyDef.allowSleep = b.allowSleep;
            bodyDef.angle = b.angle;
            bodyDef.angularDamping = b.angularDamping;
            bodyDef.angularVelocity = b.angularVelocity;
            bodyDef.awake = b.awake;
            bodyDef.bullet = b.bullet;
            bodyDef.fixedRotation = b.fixedRotation;
            bodyDef.gravityScale = b.gravityScale;
            bodyDef.linearDamping = b.linearDamping;
            bodyDef.linearVelocity = b.linearVelocity;
            bodyDef.position = b.position;
            bodyDef.type = b.type;
            bodyDef.userData = b.userData;
            org.jbox2d.dynamics.Body body = new org.jbox2d.dynamics.Body(bodyDef, result);
            bodyMap.put(b, body);
            for (Fixture fixture : b.fixtureList) {
                FixtureDef fd = new FixtureDef();
                org.jbox2d.collision.shapes.Shape newShape = null;
                Shape shape = fixture.shape;
                if (shape.type == ShapeType.CHAIN) {
                    ChainShape chain = (ChainShape) shape;
                    org.jbox2d.collision.shapes.ChainShape chainShape = new org.jbox2d.collision.shapes.ChainShape();
                    chainShape.m_vertices = chain.m_vertices;
                    chainShape.m_hasNextVertex = chain.m_hasNextVertex;
                    chainShape.m_hasPrevVertex = chain.m_hasPrevVertex;
                    chainShape.m_nextVertex.set(chain.m_nextVertex);
                    chainShape.m_prevVertex.set(chain.m_prevVertex);
                    newShape = chainShape;
                } else if (shape.type == ShapeType.CIRCLE) {
                    CircleShape circle = (CircleShape) shape;
                    org.jbox2d.collision.shapes.CircleShape newCircle = new org.jbox2d.collision.shapes.CircleShape();
                    newCircle.m_radius = circle.radius;
                    newShape = newCircle;
                } else if (shape.type == ShapeType.EDGE) {
                    EdgeShape edge = (EdgeShape) shape;
                    org.jbox2d.collision.shapes.EdgeShape newEdge = new org.jbox2d.collision.shapes.EdgeShape();
                    newEdge.set(edge.vertex1, edge.vertex2);
                    newShape = newEdge;
                } else if (shape.type == ShapeType.POLYGON) {
                    PolygonShape polygon = (PolygonShape) shape;
                    org.jbox2d.collision.shapes.PolygonShape newPolygon = new org.jbox2d.collision.shapes.PolygonShape();
                    newPolygon.set(polygon.m_vertices, polygon.m_count);
                    newShape = newPolygon;
                }
                if (newShape != null) {
                    fd.shape = newShape;
                }
                fd.density = fixture.density;
                //Filter filter = new org.jbox2d.dynamics.Filter();
                fd.friction = fixture.friction;
                fd.isSensor = fixture.sensor;
                fd.restitution = fixture.restitution;
                fd.userData = fixture.userData;
                body.createFixture(fd);
            }
        }
        for (Joint joint : world.jointList) {
            org.jbox2d.dynamics.joints.JointDef newJointDef = null;
            if (joint.type == JointType.DISTANCE) {
            } else if (joint.type == JointType.FRICTION) {
                FrictionJoint frictionJoint = (FrictionJoint) joint;
                FrictionJointDef newFrictionJointDef = new FrictionJointDef();
                newFrictionJointDef.initialize(bodyMap.get(frictionJoint.bodyA), bodyMap.get(frictionJoint.bodyB), frictionJoint.anchor);
                newJointDef = newFrictionJointDef;
            } else if (joint.type == JointType.GEAR) {
                GearJoint gearJoint = (GearJoint) joint;
                GearJointDef newGearJointDef = new GearJointDef();
                newGearJointDef.joint1 = jointMap.get(gearJoint.joint1);
                newGearJointDef.joint2 = jointMap.get(gearJoint.joint2);
                newGearJointDef.ratio = gearJoint.ratio;
                newJointDef = newGearJointDef;
            } else if (joint.type == JointType.MOUSE) {
                MouseJoint mouseJoint = (MouseJoint) joint;
                MouseJointDef newMouseJointDef = new MouseJointDef();
                newMouseJointDef.dampingRatio = mouseJoint.dampingRatio;
                newMouseJointDef.frequencyHz = mouseJoint.frequencyHz;
                newMouseJointDef.maxForce = mouseJoint.maxForce;
                newJointDef = newMouseJointDef;
            } else if (joint.type == JointType.PRISMATIC) {
                PrismaticJoint prismaticJoint = (PrismaticJoint) joint;
                PrismaticJointDef newPrismaticJointDef = new PrismaticJointDef();
                newPrismaticJointDef.initialize(bodyMap.get(prismaticJoint.bodyA), bodyMap.get(prismaticJoint.bodyB), prismaticJoint.anchor, prismaticJoint.axis);
                newPrismaticJointDef.enableLimit = prismaticJoint.enableLimit;
                newPrismaticJointDef.enableMotor = prismaticJoint.enableMotor;
                newPrismaticJointDef.lowerTranslation = prismaticJoint.lowerTranslation;
                newPrismaticJointDef.maxMotorForce = prismaticJoint.maxMotorForce;
                newPrismaticJointDef.motorSpeed = prismaticJoint.motorSpeed;
                newPrismaticJointDef.referenceAngle = prismaticJoint.referenceAngle;
                newPrismaticJointDef.upperTranslation = prismaticJoint.upperTranslation;
                newJointDef = newPrismaticJointDef;
            } else if (joint.type == JointType.PULLEY) {
                PulleyJoint pulleyJoint = (PulleyJoint) joint;
                PulleyJointDef newPulleyJointDef = new PulleyJointDef();
                newPulleyJointDef.initialize(bodyMap.get(joint.bodyA), bodyMap.get(joint.bodyB), pulleyJoint.groundAnchorA, pulleyJoint.groundAnchorB,
                        pulleyJoint.localAnchorA, pulleyJoint.localAnchorB, pulleyJoint.ratio);
                newPulleyJointDef.lengthA = pulleyJoint.lengthA;
                newPulleyJointDef.lengthB = pulleyJoint.lengthB;
                newJointDef = newPulleyJointDef;
            } else if (joint.type == JointType.REVOLUTE) {
                RevoluteJoint revoluteJoint = (RevoluteJoint) joint;
                RevoluteJointDef newRevoluteJointDef = new RevoluteJointDef();
                newRevoluteJointDef.enableLimit = revoluteJoint.enableLimit;
                newRevoluteJointDef.enableMotor = revoluteJoint.enableMotor;
                newRevoluteJointDef.localAnchorA = revoluteJoint.localAnchorA;
                newRevoluteJointDef.localAnchorB = revoluteJoint.localAnchorB;
                newRevoluteJointDef.lowerAngle = revoluteJoint.lowerAngle;
                newRevoluteJointDef.maxMotorTorque = revoluteJoint.maxMotorTorque;
                newRevoluteJointDef.motorSpeed = revoluteJoint.motorSpeed;
                newRevoluteJointDef.referenceAngle = revoluteJoint.referenceAngle;
                newRevoluteJointDef.upperAngle = revoluteJoint.upperAngle;
                newJointDef = newRevoluteJointDef;
            } else if (joint.type == JointType.ROPE) {
                // not implemented yet
            } else if (joint.type == JointType.UNKNOWN) {
                // not implemented yet
            } else if (joint.type == JointType.WELD) {
                WeldJoint weldJoint = (WeldJoint) joint;
                WeldJointDef newWeldJointDef = new WeldJointDef();
                newWeldJointDef.initialize(bodyMap.get(joint.bodyA),bodyMap.get(joint.bodyB), weldJoint.anchor);
                newWeldJointDef.dampingRatio = weldJoint.dampingRatio;
                newWeldJointDef.frequencyHz = weldJoint.frequencyHz;
                newWeldJointDef.referenceAngle = weldJoint.referenceAngle;
                newJointDef = newWeldJointDef;
            } else if (joint.type == JointType.WHEEL) {
                // not implemented
            }
            if (newJointDef != null) {
                newJointDef.bodyA = bodyMap.get(joint.bodyA);
                newJointDef.bodyB = bodyMap.get(joint.bodyB);
                newJointDef.collideConnected = joint.collideConnected;
                newJointDef.userData = joint.userData;
                jointMap.put(joint, result.createJoint(newJointDef));
            }
        }

        return result;
    }

    public static World getJAXBWorldFromWorld(org.jbox2d.dynamics.World world) {
        return null;
    }
}
