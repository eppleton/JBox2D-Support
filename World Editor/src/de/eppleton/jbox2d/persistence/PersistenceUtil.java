/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import org.jbox2d.builders.GearJointBuilder;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.CircleContact;
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
            org.jbox2d.dynamics.Body body = result.createBody(bodyDef);
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
                // checked with PBDeserializer
                FrictionJoint frictionJoint = (FrictionJoint) joint;
                FrictionJointDef newFrictionJointDef = new FrictionJointDef();
                newFrictionJointDef.localAnchorA.set(frictionJoint.localAnchorA);
                newFrictionJointDef.localAnchorB.set(frictionJoint.localAnchorB);
                newFrictionJointDef.maxForce = frictionJoint.maxForce;
                newFrictionJointDef.maxTorque = frictionJoint.maxTorque;
                newJointDef = newFrictionJointDef;
            } else if (joint.type == JointType.GEAR) {
                // checked with PBDeserializer

                GearJoint gearJoint = (GearJoint) joint;
                GearJointDef newGearJointDef = new GearJointDef();
                newGearJointDef.joint1 = jointMap.get(gearJoint.joint1);
                newGearJointDef.joint2 = jointMap.get(gearJoint.joint2);
                newGearJointDef.ratio = gearJoint.ratio;
                newJointDef = newGearJointDef;
            } else if (joint.type == JointType.MOUSE) {
                // checked with PBDeserializer

                MouseJoint mouseJoint = (MouseJoint) joint;
                MouseJointDef newMouseJointDef = new MouseJointDef();
                newMouseJointDef.dampingRatio = mouseJoint.dampingRatio;
                newMouseJointDef.frequencyHz = mouseJoint.frequencyHz;
                newMouseJointDef.maxForce = mouseJoint.maxForce;
                newMouseJointDef.target.set(mouseJoint.target);
                newJointDef = newMouseJointDef;
            } else if (joint.type == JointType.PRISMATIC) {
                // checked with PBDeserializer

                PrismaticJoint prismaticJoint = (PrismaticJoint) joint;
                PrismaticJointDef newPrismaticJointDef = new PrismaticJointDef();
                newPrismaticJointDef.enableLimit = prismaticJoint.enableLimit;
                newPrismaticJointDef.enableMotor = prismaticJoint.enableMotor;
                newPrismaticJointDef.lowerTranslation = prismaticJoint.lowerTranslation;
                newPrismaticJointDef.localAnchorA.set(prismaticJoint.localAnchorA);
                newPrismaticJointDef.localAnchorB.set(prismaticJoint.localAnchorB);
                newPrismaticJointDef.localAxisA.set(prismaticJoint.axis);
                newPrismaticJointDef.maxMotorForce = prismaticJoint.maxMotorForce;
                newPrismaticJointDef.motorSpeed = prismaticJoint.motorSpeed;
                newPrismaticJointDef.referenceAngle = prismaticJoint.referenceAngle;
                newPrismaticJointDef.upperTranslation = prismaticJoint.upperTranslation;
                newJointDef = newPrismaticJointDef;
            } else if (joint.type == JointType.PULLEY) {
                // checked with PBDeserializer

                PulleyJoint pulleyJoint = (PulleyJoint) joint;
                PulleyJointDef newPulleyJointDef = new PulleyJointDef();

                newPulleyJointDef.localAnchorA.set(pulleyJoint.localAnchorA);
                newPulleyJointDef.localAnchorB.set(pulleyJoint.localAnchorB);
                newPulleyJointDef.groundAnchorA.set(pulleyJoint.groundAnchorA);
                newPulleyJointDef.groundAnchorB.set(pulleyJoint.groundAnchorB);
                newPulleyJointDef.ratio = pulleyJoint.ratio;
                newPulleyJointDef.lengthA = pulleyJoint.lengthA;
                newPulleyJointDef.lengthB = pulleyJoint.lengthB;
                newJointDef = newPulleyJointDef;
            } else if (joint.type == JointType.REVOLUTE) {
                // checked with PBDeserializer
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
                // checked with PBDeserializer

                WeldJoint weldJoint = (WeldJoint) joint;
                WeldJointDef newWeldJointDef = new WeldJointDef();
                newWeldJointDef.localAnchorA.set(weldJoint.localAnchorA);
                newWeldJointDef.localAnchorB.set(weldJoint.localAnchorB);

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
        World result = new World();
        result.gravity = world.getGravity();
        result.bodyList = new ArrayList<Body>();
        HashMap<org.jbox2d.dynamics.Body, Body> bodyMap = new HashMap<org.jbox2d.dynamics.Body, Body>();
        HashMap<org.jbox2d.dynamics.joints.Joint, Joint> jointMap = new HashMap<org.jbox2d.dynamics.joints.Joint, Joint>();
        org.jbox2d.dynamics.Body nextBody = world.getBodyList();
        while (nextBody != null) {
            Body body = new Body();
            body.active = nextBody.isActive();
            body.allowSleep = nextBody.isSleepingAllowed();
            body.angle = nextBody.getAngle();
            body.angularDamping = nextBody.getAngularDamping();
            body.angularVelocity = nextBody.getAngularVelocity();
            body.awake = nextBody.isAwake();
            body.bullet = nextBody.isBullet();
            body.fixedRotation = nextBody.isFixedRotation();
            body.gravityScale = nextBody.getGravityScale();
            body.linearDamping = nextBody.getLinearDamping();
            body.linearVelocity = nextBody.getLinearVelocity();
            body.position = nextBody.getPosition();
            body.type = nextBody.getType();
            body.userData = nextBody.getUserData();
            result.bodyList.add(body);
            bodyMap.put(nextBody, body);
            body.fixtureList = new ArrayList<Fixture>();
            org.jbox2d.dynamics.Fixture nextFixture = nextBody.getFixtureList();
            while (nextFixture != null) {
                Fixture fixture = new Fixture();
                fixture.density = nextFixture.getDensity();
                // TODO
                fixture.filter = nextFixture.getFilterData();
                fixture.friction = nextFixture.getFriction();
                fixture.restitution = nextFixture.getRestitution();
                fixture.sensor = nextFixture.isSensor();
                fixture.userData = nextFixture.getUserData();
                Shape newShape = null;
                org.jbox2d.collision.shapes.Shape shape = nextFixture.getShape();
                if (shape instanceof org.jbox2d.collision.shapes.ChainShape) {
                    org.jbox2d.collision.shapes.ChainShape chainShape = (org.jbox2d.collision.shapes.ChainShape) shape;
                    ChainShape newChainShape = new ChainShape();
                    newChainShape.m_count = chainShape.m_count;
                    newChainShape.m_hasNextVertex = chainShape.m_hasNextVertex;
                    newChainShape.m_hasPrevVertex = chainShape.m_hasPrevVertex;
                    newChainShape.m_vertices = chainShape.m_vertices;
                    newChainShape.m_nextVertex = chainShape.m_nextVertex;
                    newChainShape.m_prevVertex = chainShape.m_prevVertex;
                    newShape = newChainShape;
                } else if (shape instanceof org.jbox2d.collision.shapes.CircleShape) {
                    org.jbox2d.collision.shapes.CircleShape circleShape = (org.jbox2d.collision.shapes.CircleShape) shape;
                    CircleShape newCircleShape = new CircleShape();
                    newCircleShape.radius = circleShape.getRadius();
                    newShape = newCircleShape;
                } else if (shape instanceof org.jbox2d.collision.shapes.EdgeShape) {
                    org.jbox2d.collision.shapes.EdgeShape edgeShape = (org.jbox2d.collision.shapes.EdgeShape) shape;
                    EdgeShape newEdgeShape = new EdgeShape();
                    newEdgeShape.vertex1 = edgeShape.m_vertex1;
                    newEdgeShape.vertex2 = edgeShape.m_vertex2;
                    newShape = newEdgeShape;
                } else if (shape instanceof org.jbox2d.collision.shapes.PolygonShape) {
                    org.jbox2d.collision.shapes.PolygonShape polygonShape = (org.jbox2d.collision.shapes.PolygonShape) shape;
                    PolygonShape newPolygonShape = new PolygonShape();
                    newPolygonShape.m_count = polygonShape.getVertexCount();
                    newPolygonShape.m_vertices = polygonShape.getVertices();
                    newShape = newPolygonShape;
                }
                if (newShape != null) {
                    newShape.radius = shape.getRadius();
                    fixture.shape = newShape;
                }
                body.fixtureList.add(fixture);
                nextFixture = nextFixture.getNext();
            }
            nextBody = nextBody.getNext();
        }
        org.jbox2d.dynamics.joints.Joint nextJoint = world.getJointList();
        while (nextJoint != null) {
            Joint newJoint = null;

            if (nextJoint instanceof org.jbox2d.dynamics.joints.DistanceJoint) {
                // checked with PBSerializer

                DistanceJoint newDistanceJoint = new DistanceJoint();
                org.jbox2d.dynamics.joints.DistanceJoint distanceJoint = (org.jbox2d.dynamics.joints.DistanceJoint) nextJoint;
                newDistanceJoint.localAnchorA = distanceJoint.m_localAnchorA;
                newDistanceJoint.localAnchorB = distanceJoint.m_localAnchorB;
                newDistanceJoint.dampingRatio = distanceJoint.getDampingRatio();
                newDistanceJoint.frequencyHz = distanceJoint.getFrequency();
                newDistanceJoint.length = distanceJoint.getLength();
                newJoint = newDistanceJoint;
            } else if (nextJoint instanceof org.jbox2d.dynamics.joints.FrictionJoint) {
                // checked with PBSerializer

                org.jbox2d.dynamics.joints.FrictionJoint frictionJoint = (org.jbox2d.dynamics.joints.FrictionJoint) nextJoint;
                FrictionJoint newFrictionJoint = new FrictionJoint();
                newFrictionJoint.localAnchorA = frictionJoint.getLocalAnchorA();
                newFrictionJoint.localAnchorB = frictionJoint.getLocalAnchorB();
                newFrictionJoint.maxForce = frictionJoint.getMaxForce();
                newFrictionJoint.maxTorque = frictionJoint.getMaxTorque();
                newJoint = newFrictionJoint;
            } /*else if (nextJoint instanceof GearJoint){
             * not implemented yet
             }*/ else if (nextJoint instanceof org.jbox2d.dynamics.joints.MouseJoint) {
                // checked with PBSerializer

                org.jbox2d.dynamics.joints.MouseJoint mouseJoint = (org.jbox2d.dynamics.joints.MouseJoint) nextJoint;
                MouseJoint newMouseJoint = new MouseJoint();
                newMouseJoint.dampingRatio = mouseJoint.getDampingRatio();
                newMouseJoint.frequencyHz = mouseJoint.getFrequency();
                newMouseJoint.maxForce = mouseJoint.getMaxForce();
                newMouseJoint.target = mouseJoint.getTarget();
                newJoint = newMouseJoint;
            } else if (nextJoint instanceof org.jbox2d.dynamics.joints.PrismaticJoint) {
                // checked with PBSerializer
                org.jbox2d.dynamics.joints.PrismaticJoint prismaticJoint = (org.jbox2d.dynamics.joints.PrismaticJoint) nextJoint;
                PrismaticJoint newPrismaticJoint = new PrismaticJoint();
                newPrismaticJoint.referenceAngle = prismaticJoint.m_referenceAngle;
                newPrismaticJoint.enableLimit = prismaticJoint.m_enableLimit;
                newPrismaticJoint.lowerTranslation = prismaticJoint.m_lowerTranslation;
                newPrismaticJoint.upperTranslation = prismaticJoint.m_upperTranslation;
                newPrismaticJoint.enableMotor = prismaticJoint.m_enableMotor;
                newPrismaticJoint.motorSpeed = prismaticJoint.m_motorSpeed;
                newPrismaticJoint.localAnchorA = prismaticJoint.m_localAnchorA;
                newPrismaticJoint.localAnchorB = prismaticJoint.m_localAnchorB;
                newPrismaticJoint.maxMotorForce = prismaticJoint.m_maxMotorForce;
                newJoint = newPrismaticJoint;
            } else if (nextJoint instanceof org.jbox2d.dynamics.joints.PulleyJoint) {
                // checked with PBSerializer
                org.jbox2d.dynamics.joints.PulleyJoint pulleyJoint = (org.jbox2d.dynamics.joints.PulleyJoint) nextJoint;
                PulleyJoint newPulleyJoint = new PulleyJoint();
                newPulleyJoint.localAnchorA = pulleyJoint.m_localAnchorA;
                newPulleyJoint.localAnchorB = pulleyJoint.m_groundAnchorB;
                newPulleyJoint.groundAnchorA = pulleyJoint.m_groundAnchorA;
                newPulleyJoint.groundAnchorB = pulleyJoint.m_groundAnchorB;
                newPulleyJoint.lengthA = pulleyJoint.getLengthA();
                newPulleyJoint.lengthB = pulleyJoint.getLengthB();
                newPulleyJoint.ratio = pulleyJoint.getRatio();

                newJoint = newPulleyJoint;
            } else if (nextJoint instanceof org.jbox2d.dynamics.joints.RevoluteJoint) {
                // checked with PBSerializer

                org.jbox2d.dynamics.joints.RevoluteJoint revoluteJoint = (org.jbox2d.dynamics.joints.RevoluteJoint) nextJoint;
                RevoluteJoint newRevoluteJoint = new RevoluteJoint();
                newRevoluteJoint.referenceAngle = revoluteJoint.m_referenceAngle;
                newRevoluteJoint.enableLimit = revoluteJoint.m_enableLimit;
                newRevoluteJoint.lowerAngle = revoluteJoint.m_lowerAngle;
                newRevoluteJoint.upperAngle = revoluteJoint.m_upperAngle;
                newRevoluteJoint.enableMotor = revoluteJoint.m_enableMotor;
                newRevoluteJoint.motorSpeed = revoluteJoint.m_motorSpeed;
                newRevoluteJoint.maxMotorTorque = revoluteJoint.m_maxMotorTorque;
                newRevoluteJoint.localAnchorA = revoluteJoint.m_localAnchorA;
                newRevoluteJoint.localAnchorB = revoluteJoint.m_localAnchorB;
                newJoint = newRevoluteJoint;
            } else if (nextJoint instanceof org.jbox2d.dynamics.joints.WeldJoint) {
                // not available in  PBSerializer, probably incomplete...
                org.jbox2d.dynamics.joints.WeldJoint weldJoint = (org.jbox2d.dynamics.joints.WeldJoint) nextJoint;
                WeldJoint newWeldJoint = new WeldJoint();
                newWeldJoint.localAnchorA = weldJoint.getLocalAnchorA();
                newWeldJoint.localAnchorB = weldJoint.getLocalAnchorB();
                newWeldJoint.dampingRatio = weldJoint.getDampingRatio();
                newWeldJoint.frequencyHz = weldJoint.getFrequency();
                newJoint = newWeldJoint;
            }
            if (newJoint != null) {
                newJoint.bodyA = bodyMap.get(nextJoint.m_bodyA);
                newJoint.bodyB = bodyMap.get(nextJoint.m_bodyB);
                newJoint.collideConnected = nextJoint.getCollideConnected();
                newJoint.userData = nextJoint.getUserData();
                jointMap.put(nextJoint, newJoint);
                result.jointList.add(newJoint);
            }
        }

        return result;
    }
}
