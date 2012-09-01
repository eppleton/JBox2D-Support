/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d;

import de.eppleton.physics.editor.scene.WorldScene;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

/**
 *
 * @author antonepple
 */
public class Box2DUtilities {

    public static float getMaxX(Body body) {
        float maxX = body.getPosition().x;
        if (body.getFixtureList() != null && body.m_fixtureList.getShape() instanceof PolygonShape) {
            Vec2[] vertices = ((PolygonShape) body.m_fixtureList.getShape()).getVertices();
            float maxVX = 0;
            for (Vec2 vec2 : vertices) {
                if (vec2.x > maxVX) {
                    maxVX = vec2.x;
                }
            }
            maxX += maxVX;
        }
        if (body.getFixtureList() != null && body.m_fixtureList.getShape() instanceof CircleShape) {
            maxX += ((CircleShape) body.m_fixtureList.getShape()).m_radius;
        }
        return maxX;
    }

    public static float getMaxY(Body body) {
        float maxY = body.getPosition().y;
        if (body.getFixtureList() != null && body.m_fixtureList.getShape() instanceof PolygonShape) {
            Vec2[] vertices = ((PolygonShape) body.m_fixtureList.getShape()).getVertices();
            float maxVY = 0;
            for (Vec2 vec2 : vertices) {
                if (vec2.y > maxVY) {
                    maxVY = vec2.x;
                }
            }
            maxY += maxVY;
        }
        if (body.getFixtureList() != null && body.m_fixtureList.getShape() instanceof CircleShape) {
            maxY += ((CircleShape) body.m_fixtureList.getShape()).m_radius;
        }
        return maxY;
    }

    public static void scaleToFit(World world, WorldScene worldScene) {
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        float maxY = Float.MIN_VALUE;
        Body nextBody = world.getBodyList();
        while (nextBody != null) {
            if (nextBody.getPosition().x < minX) {
                minX = nextBody.getPosition().x;
            }
            if (nextBody.getPosition().y < minY) {
                minY = nextBody.getPosition().y;
            }
            if (Box2DUtilities.getMaxX(nextBody) > maxX) {
                maxX = Box2DUtilities.getMaxX(nextBody);
            }
            if (Box2DUtilities.getMaxY(nextBody) > maxY) {
                maxY = Box2DUtilities.getMaxY(nextBody);
            }

            nextBody = nextBody.getNext();
        }
        worldScene.setOffsetX((minX < 0) ? -minX : 0);
        worldScene.setOffsetY((minY < 0) ? -minY : 0);
        float width = maxX - minX;
        float height = maxY - minY;
        worldScene.setOffsetY(worldScene.getOffsetY()+height);
        worldScene.setScale((int) (600 / ((width > height) ? width : height)));
    }
}
