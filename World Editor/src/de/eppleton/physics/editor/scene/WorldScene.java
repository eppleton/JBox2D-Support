/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene;

import com.sun.istack.internal.logging.Logger;
import de.eppleton.jbox2d.Box2DUtilities;
import de.eppleton.jbox2d.WorldUtilities;
import de.eppleton.physics.editor.palette.items.B2DActiveEditorDrop;
import de.eppleton.physics.editor.scene.widgets.CircleWidget;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.ResizeProvider;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Widget;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.Node;
import org.openide.text.ActiveEditorDrop;
import org.openide.util.Exceptions;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

/**
 *
 * @author antonepple
 */
public class WorldScene extends ObjectScene implements LookupListener {

    private static Logger LOGGER = Logger.getLogger(WorldScene.class);
    private World world;
    private int scale = 300;
    private float offsetX = 0;
    private float offsetY = 0;
    private final Callback callback;
    private Result<Body> lookupResult;
    private ExplorerManager em;
    private final WidgetAction selectAction;
    private ResizeProvider resizeProvider;

    public WorldScene(final ExplorerManager em,
            final World world, Callback callback) {
        this.em = em;
        this.world = world;
        world.step( 1.0f / 60 , 1,1);
        this.callback = callback;
        selectAction = ActionFactory.createSelectAction(new SelectProviderImpl(em));
        Box2DUtilities.scaleToFit(world, this);
        super.getActions().addAction(ActionFactory.createZoomAction());
        super.getActions().addAction(ActionFactory.createAcceptAction(new AcceptProviderImpl()));
        updateBodies();
        lookupResult = Utilities.actionsGlobalContext().lookupResult(Body.class);
        lookupResult.addLookupListener(this);
    }

    private void handleTransfer(Point point, B2DActiveEditorDrop transferData) {
        LOGGER.info("handle Transfer: get Bodies");

        HashMap<Integer, Body> addBodies = transferData.addBodies(world);
        LOGGER.info("retrieved Bodies");
        float x = WorldUtilities.sceneToWorld(point.x, scale, offsetX, false);
        float y = WorldUtilities.sceneToWorld(point.x, scale, offsetX, false);
        LOGGER.info("Configuring the Bodies");

        configureBodies(addBodies, x, y);
        /*for (Body newBody : newBodies) {
         newBody.getPosition().x = 
         newBody.getPosition().y = ≈
         } */
        LOGGER.info("Updateing the scene");

        updateBodies();
        LOGGER.info("Ready updateing the scene");
    }

    private void configureBodies(HashMap<Integer, Body> bodies, float x, float y) {
        Collection<Body> values = bodies.values();
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        for (Body body : values) {
            minX = body.getPosition().x < minX ? body.getPosition().x : minX;
            minY = body.getPosition().y < minY ? body.getPosition().y : minY;
        }
        for (Body body : values) {
            body.getPosition().x = body.getPosition().x - minX + x;
            body.getPosition().y = body.getPosition().y - minY + y;
        }
    }

    public void updateBodies() {
        Body nextBody = world.getBodyList();
        while (nextBody != null) {
            if (nextBody.getFixtureList() != null) {
                Shape shape = nextBody.getFixtureList().getShape();
                NodeProvider nodeProvider = NodeManager.getNodeProvider(nextBody, shape);
                Widget widget = super.findWidget(nextBody);
                nodeProvider.configureNode(this, widget, nextBody, shape, offsetX, offsetY, scale);//, transform);
            }
            nextBody = nextBody.getNext();
        }
        if (getView() != null && getView().isShowing()) {
            Joint nextJoint = world.getJointList();
            while (nextJoint != null) {
                JointProvider jointProvider = JointManager.getJointProvider(nextJoint);
                ConnectionWidget widget = (ConnectionWidget) super.findWidget(nextJoint);
                jointProvider.configureWidget(this, widget, nextJoint, offsetX, offsetY, scale);
                nextJoint = nextJoint.getNext();

            }
        }
        LOGGER.info("update ready");
        repaint();
    }

    void addWidgetToScene(final Widget widget,
            final Object payload,
            final float offset_x,
            final float offset_y,
            final int scale) {
        addChild(widget);
        addObject(payload, widget);
        if (payload instanceof Body) {

            widget.getActions().addAction(ActionFactory.createResizeAction(null, resizeProvider));
            widget.getActions().addAction(ActionFactory.createMoveAction());
            widget.getActions().addAction(selectAction);
            widget.addDependency(
                    new Widget.Dependency() {
                        int x, y, width, height;

                        @Override
                        public void revalidateDependency() {

                            if (widget.getLocation() != null ) {
                                int newX = widget.getLocation().x ;
                                int newY = widget.getLocation().y ;
                                if ((newX != x || newY != y)) {
                                    //  System.out.println("old "+payload.getPosition());
                                    ((Body) payload).getPosition().x = WorldUtilities.sceneToWorld(newX, scale, offset_x, false);
                                    ((Body) payload).getPosition().y = WorldUtilities.sceneToWorld(newY, scale, offset_y, true);
                                    // System.out.println("new "+payload.getPosition());
                                    fireChange();
                                }
                            }
                            Rectangle bounds = widget.getBounds();
                            if (bounds != null) {
                                int newHeight = bounds.height;
                                int newWidth = bounds.width;
                                if (newHeight != height || newWidth != width) {
                                    Shape shape = ((Body) payload).getFixtureList().getShape();
                                    if (shape instanceof CircleShape && widget instanceof CircleWidget) {
                                        shape.m_radius = (float) ((CircleWidget) widget).getRadius() / (float) scale;

                                    }
                                }
                            }


                        }
                    });
        }
        fireChange();

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

    @Override
    public void resultChanged(LookupEvent ev) {
        Collection<? extends Body> allInstances = lookupResult.allInstances();
        HashSet hashSet = new HashSet();
        for (Body body : allInstances) {
            if (getObjects().contains(body)) {
                hashSet.add(body);
            }
        }
        this.setSelectedObjects(hashSet);
        getView().repaint();
    }

    private static class SelectProviderImpl implements SelectProvider {

        private final ExplorerManager em;

        public SelectProviderImpl(ExplorerManager em) {
            this.em = em;
        }

        @Override
        public boolean isAimingAllowed(Widget widget, Point localLocation, boolean invertSelection) {
            return false;
        }

        @Override
        public boolean isSelectionAllowed(Widget widget, Point localLocation, boolean invertSelection) {
            return true;
        }

        @Override
        public void select(Widget widget, Point localLocation, boolean invertSelection) {
            try {
                ObjectScene scene = ((ObjectScene) widget.getScene());
                Object object = scene.findObject(widget);

                scene.setFocusedObject(object);
                if (object != null) {
                    if (!invertSelection && scene.getSelectedObjects().contains(object)) {
                        return;
                    }

                    scene.userSelectionSuggested(Collections.singleton(object), invertSelection);
                } else {
                    scene.userSelectionSuggested(Collections.emptySet(), invertSelection);
                }

                if (object instanceof Node) {
                    Set selected = scene.getSelectedObjects();
                    ArrayList<Node> selectedNodes = new ArrayList<Node>();
                    for (Object object1 : selected) {
                        if (object1 instanceof Node) {
                            selectedNodes.add((Node) object1);
                        }
                    }
                    em.setSelectedNodes(selectedNodes.toArray(new Node[0]));
                }
            } catch (PropertyVetoException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    private class AcceptProviderImpl implements AcceptProvider {

        public AcceptProviderImpl() {
        }

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
                    fireChange();

                }
            } catch (UnsupportedFlavorException | IOException ex) {
                Exceptions.printStackTrace(ex);
            }

        }
    }
}
