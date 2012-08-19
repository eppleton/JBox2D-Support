/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene;

import de.eppleton.jbox2d.Box2DUtilities;
import de.eppleton.physics.editor.palette.items.B2DActiveEditorDrop;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.AffineTransform;
import java.beans.BeanInfo;
import java.io.IOException;
import javax.swing.JComponent;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.nodes.Node;
import org.openide.nodes.NodeTransfer;
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

    public WorldScene(final World world, Callback callback) {
        this.world = world;
        this.callback = callback;
        Box2DUtilities.scaleToFit(world, this);
        super.getActions().addAction(ActionFactory.createZoomAction());
        super.getActions().addAction(ActionFactory.createAcceptAction(new AcceptProvider() {
            @Override
            public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
                try {
                    if (transferable.isDataFlavorSupported(ActiveEditorDrop.FLAVOR)
                            && (transferable.getTransferData(ActiveEditorDrop.FLAVOR) instanceof B2DActiveEditorDrop)) {
                        return ConnectorState.ACCEPT;
                    }
                } catch (UnsupportedFlavorException | IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
                return ConnectorState.REJECT;
            }

            @Override
            public void accept(Widget widget, Point point, Transferable transferable) {
                try {
                    if (transferable.isDataFlavorSupported(ActiveEditorDrop.FLAVOR)
                            && (transferable.getTransferData(ActiveEditorDrop.FLAVOR) instanceof B2DActiveEditorDrop)) {
                        B2DActiveEditorDrop transferData = (B2DActiveEditorDrop) transferable.getTransferData(ActiveEditorDrop.FLAVOR);
                        handleTransfer(widget.convertLocalToScene(point), transferData);
                    }
                } catch (UnsupportedFlavorException | IOException ex) {
                    Exceptions.printStackTrace(ex);
                }

            }
        }));
        updateBodies();
    }

    private void handleTransfer(Point point, B2DActiveEditorDrop transferData) {
        Body newBody = transferData.createBody(world);
        newBody.getPosition().x = NodeManager.sceneToWorld(point.x, scale, offsetX, false);
        newBody.getPosition().y = NodeManager.sceneToWorld(point.y, scale, offsetY, true);
        updateBodies();
    }

    private void updateBodies() {
        Body nextBody = world.getBodyList();
        while (nextBody != null) {
            Shape shape = nextBody.getFixtureList().getShape();
            NodeProvider nodeProvider = NodeManager.getNodeProvider(nextBody, shape);
            Widget widget = super.findWidget(nextBody);
            nodeProvider.configureNode(this, widget, nextBody, shape, offsetX, offsetY, scale);//, transform);
            nextBody = nextBody.getNext();
        }
        repaint();
    }

    void fireChange() {
        callback.changed();
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public World getWorld() {
        return world;
    }
}
