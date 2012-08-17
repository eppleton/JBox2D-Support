/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author antonepple
 */
public class WorldScene extends ObjectScene {

    World world;
    private int scale = 300;
    private float offsetX = 0;
    private float offsetY = 0;
    private final Callback callback;

    public WorldScene(World world, Callback callback) {
        this.world = world;
        this.callback = callback;
        scale(world);
        super.getActions().addAction(ActionFactory.createZoomAction());
        updateBodies();
    }

    private void scale(World world) {
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
            if (getMaxX(nextBody) > maxX) {
                maxX = getMaxX(nextBody);

            }
            if (getMaxY(nextBody) > maxY) {
                maxY = getMaxY(nextBody);
            }
            offsetX = (minX < 0) ? -minX : 0;
            offsetY = (minY < 0) ? -minY : 0;
            float width = maxX - minX;
            float height = maxY - minY;
            scale = (int) (600 / ((width > height) ? width : height));
            nextBody = nextBody.getNext();
        }
    }

    public float getMaxX(Body body) {
        float maxX = body.getPosition().x;
        if (body.m_fixtureList.getShape() instanceof PolygonShape) {
            Vec2[] vertices = ((PolygonShape)body.m_fixtureList.getShape()).getVertices();
            float maxVX = 0;
            for (Vec2 vec2 : vertices) {
                if (vec2.x > maxVX ) maxVX = vec2.x;
            }
            maxX += maxVX;
        }
        if (body.m_fixtureList.getShape() instanceof CircleShape) {
            maxX += ((CircleShape)body.m_fixtureList.getShape()).m_radius;
        }
        return maxX;
    }
    
    public float getMaxY(Body body) {
        float maxY = body.getPosition().y;
        if (body.m_fixtureList.getShape() instanceof PolygonShape) {
            Vec2[] vertices = ((PolygonShape)body.m_fixtureList.getShape()).getVertices();
            float maxVY = 0;
            for (Vec2 vec2 : vertices) {
                if (vec2.y > maxVY ) maxVY = vec2.x;
            }
            maxY += maxVY;
        }
        if (body.m_fixtureList.getShape() instanceof CircleShape) {
            maxY += ((CircleShape)body.m_fixtureList.getShape()).m_radius;
        }
        return maxY;
    }

    private void updateBodies() {
        Body nextBody = world.getBodyList();
        while (nextBody != null) {
            Shape shape = nextBody.getFixtureList().getShape();
            NodeProvider nodeProvider = NodeManager.getNodeProvider(nextBody, shape);
            Widget widget = super.findWidget(nextBody);
            widget = nodeProvider.configureNode(this, widget, nextBody, shape, offsetX, offsetY, scale);//, transform);
            nextBody = nextBody.getNext();
        }
    }

    void fireChange() {
        callback.changed();
    }
}
