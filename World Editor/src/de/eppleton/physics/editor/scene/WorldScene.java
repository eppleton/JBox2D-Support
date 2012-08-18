/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene;

import de.eppleton.physics.editor.palette.items.B2DActiveEditorDrop;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import javax.swing.JComponent;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;
import org.openide.text.ActiveEditorDrop;
import org.openide.util.Exceptions;

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
        super.getActions().addAction(ActionFactory.createAcceptAction(new AcceptProvider() {
            @Override
            public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
                try {
                    if (transferable.isDataFlavorSupported(ActiveEditorDrop.FLAVOR)
                            && (transferable.getTransferData(ActiveEditorDrop.FLAVOR) instanceof B2DActiveEditorDrop)) {

                        return ConnectorState.ACCEPT;
                    }
                } catch (UnsupportedFlavorException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
                return ConnectorState.REJECT;
            }

            @Override
            public void accept(Widget widget, Point point, Transferable transferable) {
                DataFlavor[] transferDataFlavors = transferable.getTransferDataFlavors();
                for (DataFlavor dataFlavor : transferDataFlavors) {
                    try {
                        Object transferData = transferable.getTransferData(dataFlavor);
                    } catch (UnsupportedFlavorException ex) {
                        Exceptions.printStackTrace(ex);
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
                Widget w = new LabelWidget(WorldScene.this, "Test");
                WorldScene.this.addChild(w);
                w.setPreferredLocation(widget.convertLocalToScene(point));
            }
        }));
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
            Vec2[] vertices = ((PolygonShape) body.m_fixtureList.getShape()).getVertices();
            float maxVX = 0;
            for (Vec2 vec2 : vertices) {
                if (vec2.x > maxVX) {
                    maxVX = vec2.x;
                }
            }
            maxX += maxVX;
        }
        if (body.m_fixtureList.getShape() instanceof CircleShape) {
            maxX += ((CircleShape) body.m_fixtureList.getShape()).m_radius;
        }
        return maxX;
    }

    public float getMaxY(Body body) {
        float maxY = body.getPosition().y;
        if (body.m_fixtureList.getShape() instanceof PolygonShape) {
            Vec2[] vertices = ((PolygonShape) body.m_fixtureList.getShape()).getVertices();
            float maxVY = 0;
            for (Vec2 vec2 : vertices) {
                if (vec2.y > maxVY) {
                    maxVY = vec2.x;
                }
            }
            maxY += maxVY;
        }
        if (body.m_fixtureList.getShape() instanceof CircleShape) {
            maxY += ((CircleShape) body.m_fixtureList.getShape()).m_radius;
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
