/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.persistence;

import java.util.HashMap;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.Filter;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author antonepple
 */
public class PersistenceUtil {

    public static org.jbox2d.dynamics.World getWorldFromJAXBWorld(World world) {
        HashMap<Body, org.jbox2d.dynamics.Body> bodyMap = new HashMap<Body, org.jbox2d.dynamics.Body>();
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


        return result;
    }

    public static World getJAXBWorldFromWorld(org.jbox2d.dynamics.World world) {
        return null;
    }
}
