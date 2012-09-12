/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene;

import de.eppleton.jbox2d.WorldUtilities;
import de.eppleton.physics.editor.nodes.FakeChildFactory;
import de.eppleton.physics.editor.palette.items.B2DActiveEditorDrop;
import de.eppleton.physics.editor.scene.widgets.ContainerWidget;
import de.eppleton.physics.editor.scene.widgets.PolygonWidget;
import de.eppleton.physics.editor.scene.widgets.actions.SynchronizingResizeProvider;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.MoveProvider;
import org.netbeans.api.visual.action.ResizeProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.model.ObjectSceneEvent;
import org.netbeans.api.visual.model.ObjectSceneEventType;
import org.netbeans.api.visual.model.ObjectSceneListener;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.text.ActiveEditorDrop;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;

/**
 *
 * @author antonepple
 */
public class WorldScene extends ObjectScene implements Serializable {

    private static HashMap zoom = new HashMap();

    static {
        zoom.put("50%", new Double(0.5));
        zoom.put("75%", new Double(0.75));
        zoom.put("100%", new Double(1));
        zoom.put("150%", new Double(1.5));
        zoom.put("200%", new Double(2));
        zoom.put("400%", new Double(4));
        zoom.put("Fit page", "Fit page");
        zoom.put("Fit width", "Fit width");
        zoom.put("Fit height", "Fit height");
    }
    private static int FIXED_WIDTH = 100;
    public static final String DELETE_ACTION = "deleteAction";
    private static Logger LOGGER = Logger.getLogger(WorldScene.class.getName());
    private transient World world;
    private int scale = 30;
    private float offsetX = 0;
    private float offsetY = 0;
    private transient final Callback callback;
    private transient ExplorerManager em;
    private transient ResizeProvider resizeProvider;
    private transient LayerWidget mainLayer;
    private transient LayerWidget connectionLayer;
    private transient LayerWidget interractionLayer = new LayerWidget(this);
    private transient LayerWidget backgroundLayer = new LayerWidget(this);
    private transient Widget backgroundLayerWidget;
    private transient WidgetAction moveAction = ActionFactory.createMoveAction(null, new MultiMoveProvider());
    private transient FakeChildFactory fakeChildren;
    private transient Node root;
    
    public WorldScene(final ExplorerManager em,
            final World world, Callback callback) {
        this.em = em;
        this.callback = callback;
        this.world = world;
        initScene();
    }

    public void initScene() {
        addChild(backgroundLayer);
        mainLayer = new LayerWidget(this);
        addChild(mainLayer);
        connectionLayer = new LayerWidget(this);
        addChild(connectionLayer);
        addChild(interractionLayer);
        getActions().addAction(ActionFactory.createRectangularSelectAction(this, backgroundLayer));
        super.getActions().addAction(ActionFactory.createZoomAction());
        super.getActions().addAction(ActionFactory.createAcceptAction(new AcceptProviderImpl()));
        initBackground();
        fakeChildren = new FakeChildFactory(this);
        root = new AbstractNode(Children.create(fakeChildren, false));
        em.setRootContext(root);
        
        addObjectSceneListener(new ObjectSceneListener() {
            public void objectAdded(ObjectSceneEvent objectSceneEvent, Object object) {
            }

            public void objectRemoved(ObjectSceneEvent objectSceneEvent, Object object) {
            }

            public void objectStateChanged(ObjectSceneEvent objectSceneEvent, Object object, ObjectState objectState, ObjectState objectState0) {
            }

            public void selectionChanged(ObjectSceneEvent objectSceneEvent, Set<Object> oldSelection, Set<Object> newSelection) {
                try {
                    em.setSelectedNodes(new Node[0]);
                    ArrayList<Body> selectedBodies = new ArrayList<Body>();
                    for (Object object : newSelection) {
                        if (object instanceof Body) {
                            selectedBodies.add((Body) object);
                        }
                    }
                    fakeChildren.setKeys(selectedBodies);
                    em.setSelectedNodes(root.getChildren().getNodes());
                    Node[] nodes = root.getChildren().getNodes();


                } catch (PropertyVetoException ex) {
                    ex.printStackTrace();
                }
            }

            public void highlightingChanged(ObjectSceneEvent objectSceneEvent, Set<Object> oldHighlighte, Set<Object> newHighlighted) {
            }

            public void hoverChanged(ObjectSceneEvent objectSceneEvent, Object object, Object object0) {
            }

            public void focusChanged(ObjectSceneEvent objectSceneEvent, Object object, Object object0) {
            }
        }, ObjectSceneEventType.OBJECT_SELECTION_CHANGED);
    }

    private void handleTransfer(Point point, B2DActiveEditorDrop transferData) {
        HashMap<Integer, Body> addBodies = transferData.addBodies(world);
        float x = WorldUtilities.sceneToWorld(point.x, scale, offsetX, false);
        float y = WorldUtilities.sceneToWorld(point.y, scale, offsetY, false);
        configureBodies(addBodies, x, y);
        updateBodies();
    }

    private void configureBodies(HashMap<Integer, Body> bodies, float x, float y) {
        Collection<Body> values = bodies.values();
        float minX = Float.MAX_VALUE;
        float maxY = Float.MIN_VALUE;
        for (Body body : values) {
            minX = body.getPosition().x < minX ? body.getPosition().x : minX;
            maxY = body.getPosition().y > maxY ? body.getPosition().y : maxY;
        }
        for (Body body : values) {
            body.getPosition().x = body.getPosition().x - minX + x;
            body.getPosition().y = body.getPosition().y - maxY - y;
        }
    }

    public void updateBodies() {
        Body nextBody = world.getBodyList();
        while (nextBody != null) {
            if (nextBody.getFixtureList() != null) {
                Fixture fixture = nextBody.getFixtureList();
                while (fixture != null) {
                    Shape shape = fixture.getShape();
                    WidgetProvider nodeProvider = WidgetManager.getWidgetProvider(nextBody, shape);
                    Widget widget = super.findWidget(shape);
                    nodeProvider.configureWidget(this, widget, nextBody, shape, offsetX, offsetY, scale);//, transform);}
                    fixture = fixture.getNext();
                }
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

    void fireChange() {
        validate();
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

   

    public ResizeProvider getResizeProvider() {
        if (resizeProvider == null) resizeProvider = new SynchronizingResizeProvider();
        return resizeProvider;
    }

    public WidgetAction getMoveAction() {
        return moveAction;
    }

    public LayerWidget getMainLayer() {
        return mainLayer;
    }

    public World getWorld() {
        return world;
    }

 

    public float getOffsetY() {
        return offsetY;
    }

    private Widget createBackgroundWidget(int x, int y, int width, int height) {
        Widget back = new Widget(this);
        back.setCheckClipping(true);
        back.setOpaque(true);
        back.setPreferredBounds(new Rectangle(x, y, width, height));
        back.setBorder(BorderFactory.createLineBorder(1));
        return back;
    }

    public void initBackground() {
        Image sourceImage = ImageUtilities.loadImage("de/eppleton/physics/editor/scene/resources/paper_grid17.png"); // NOI18N
        int width = sourceImage.getWidth(null);
        int height = sourceImage.getHeight(null);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(sourceImage, 0, 0, null);
        graphics.dispose();
        TexturePaint PAINT_BACKGROUND = new TexturePaint(image, new Rectangle(0, 0, width, height));
        backgroundLayerWidget = createBackgroundWidget(-1000, -600, 2000, 1200);
        backgroundLayerWidget.setBackground(PAINT_BACKGROUND);
        // add the center of the coordinate system
        float sceneXO = WorldUtilities.worldToScene(0, scale, offsetX, false);
        float sceneXMIN = WorldUtilities.worldToScene(-1, scale, offsetX, false);
        float sceneXMAX = WorldUtilities.worldToScene(1, scale, offsetX, false);
        float sceneYO = WorldUtilities.worldToScene(0, scale, offsetY, true);
        float sceneYMIN = WorldUtilities.worldToScene(-1, scale, offsetY, true);
        float sceneYMAX = WorldUtilities.worldToScene(1, scale, offsetY, true);
        ArrayList<Point> pointsXAxis = new ArrayList<Point>();
        pointsXAxis.add(new Point((int) sceneXO, (int) sceneYMIN));
        pointsXAxis.add(new Point((int) sceneXO, (int) sceneYMAX));
        pointsXAxis.add(new Point((int) sceneXMIN, (int) sceneYO));
        pointsXAxis.add(new Point((int) sceneXMAX, (int) sceneYO));
        backgroundLayerWidget.addChild(new PolygonWidget(this, pointsXAxis));
        backgroundLayer.addChild(backgroundLayerWidget);
        repaint();
        revalidate(false);
        validate();
    }

    void addConnection(ConnectionWidget widget, Joint joint) {
        connectionLayer.addChild(widget);
        addObject(joint, widget);
        validate();
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
            } catch (Exception ex) {
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
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }

        }
    }

    @Override
    public JComponent createView() {
        super.createView();
        addKeyboardActions();
        return getView();
    }

    private void deleteselectedWidgets() {
        HashSet<Joint> jointsToDestroy = new HashSet< Joint>();
        HashSet<Body> bodiesToDestroy = new HashSet<Body>();


        Set<?> selectedObjects = getSelectedObjects();
        for (Object object : selectedObjects) {
            if (object instanceof Joint) {
                jointsToDestroy.add((Joint) object);
            } else if (object instanceof Body) {
                bodiesToDestroy.add((Body) object);
            }
        }
        for (Joint joint : jointsToDestroy) {
            Widget w = findWidget(joint);
            if (w != null) {

                Widget parentWidget = w.getParentWidget();
                if (parentWidget != null) {
                    parentWidget.removeChild(w);
                } else {
                    System.out.println("Could not find a ParentWidget for selected Joint's widget, that smells like a bug!");
                }
                this.removeObject(joint);
                System.out.println("Destroy Joint " + joint);
                world.destroyJoint(joint);
            } else {
                System.out.println("Could not find a Widget for selected Joint, that smells like a bug!");
            }
        }
        for (Body body : bodiesToDestroy) {
            Widget w = findWidget(body);
//            System.out.println("1. destroy Body " + body);
            if (w != null) {
                Widget parentWidget = w.getParentWidget();
                if (parentWidget != null) {
//                    System.out.println("2.remove widget from parent " + w);
                    parentWidget.removeChild(w);
                } else {
                    System.out.println("Could not find a ParentWidget for selected body's widget, that smells like a bug!");
                }
//                System.out.println("3.remove object from scene " + body);

                this.removeObject(body);

                // first we'll remove all the Joints of this body
                Joint joint = null;
                if (body.getJointList() != null) {
                    joint = body.getJointList().joint;
                }
                while (joint != null) {
//                    System.out.println("4. Checking Joint " + joint);
                    Widget findWidget = findWidget(joint);
                    if (findWidget != null && findWidget.getParentWidget() != null) {
//                        System.out.println("5. Removing Joint widget from parent " + joint);

                        findWidget.getParentWidget().removeChild(findWidget);
//                        System.out.println("6. Removing Joint object from scene " + joint);

                        this.removeObject(joint);
//                        System.out.println("7. destroy joint  " + joint);

                        world.destroyJoint(joint);
                    } else {
                        System.out.println("Either widget not found or ParentWidget is null for a joint of this body, that smells like a bug!");
                    }
                    joint = joint.getNext();
//                    System.out.println("7.5 joint next "+joint);
                }
//                System.out.println("8. now destroy the body "+body);

                world.destroyBody(body);
            } else {
                System.out.println("Could not find a Widget for selected Body, that smells like a bug!");
            }
        }


        repaint();
        revalidate(false);
        validate();
        fireChange();
    }

    public void addKeyboardActions() {
        getView().setFocusable(true);
        getActions().addAction(new MouseClickedAction(getView()));

        getView().getInputMap().put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0), "deleteSelectedWidgets");
        getView().getActionMap().put("deleteSelectedWidgets", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                deleteselectedWidgets();
            }
        });

    }

    
// unwichtig
    private class MultiMoveProvider implements MoveProvider {

        private HashMap<Widget, Point> originals = new HashMap<Widget, Point>();
        private Point original;

        public void movementStarted(Widget widget) {
            originals.put(widget, widget.getPreferredLocation());
            for (Object o : getSelectedObjects()) {
                Widget w = findWidget(o);
                if (w != null && (w instanceof ContainerWidget)) {
                    originals.put(w, w.getPreferredLocation());
                }
            }
        }

        public void movementFinished(Widget widget) {
            originals.clear();
            original = null;
        }

        public Point getOriginalLocation(Widget widget) {
            original = widget.getPreferredLocation();
            return original;
        }

        public void setNewLocation(Widget widget, Point location) {
            try {
                int dx = location.x - original.x;
                int dy = location.y - original.y;
                for (Map.Entry<Widget, Point> entry : originals.entrySet()) {
                    Point point = entry.getValue();
                    entry.getKey().setPreferredLocation(new Point(point.x + dx, point.y + dy));
                }
            } catch (NullPointerException nex) {
                System.out.println("original: " + original);
                System.out.println("location " + location);

                nex.printStackTrace();
            }
        }
    }
    
    public JComboBox getZoomComboBox() {
        JComboBox combo = new JComboBox(new String[]{"50%", "75%", "100%", "150%", "200%", "400%", "Fit to screen", "Fit width", "Fit height"}) {
            @Override
            public java.awt.Dimension getMinimumSize() {
                return new java.awt.Dimension(FIXED_WIDTH, getPreferredSize().height);
            }

            @Override
            public java.awt.Dimension getMaximumSize() {
                return new java.awt.Dimension(FIXED_WIDTH, getPreferredSize().height);
            }
        };
        combo.setSelectedIndex(2);
        combo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                Object item = e.getItem();
                if (zoom.get(item) instanceof Double) {
                    setZoomFactor(((Double) zoom.get(item)).doubleValue());
                } else {
                    Rectangle viewRect = getView().getVisibleRect();
                    double height = viewRect.getHeight();
                    double width = viewRect.getWidth();
                    Rectangle bounds = convertSceneToView(backgroundLayerWidget.getBounds());
                    double dHeight = height / bounds.getHeight();
                    double dWidth = width / bounds.getWidth();

                    if ("Fit to screen".equals(item)) {
                        //   System.out.println("dWidth" +dWidth+"dHeight"+dHeight);
                        setZoomFactor(getZoomFactor() * (dWidth < dHeight ? dWidth : dHeight));
                    } else if ("Fit width".equals(item)) {
                        setZoomFactor(getZoomFactor() * dWidth);
                    } else if ("Fit height".equals(item)) {
                        setZoomFactor(getZoomFactor() * dHeight);
                    }
                }
                getView().revalidate();
            }
        });
        return combo;
    }
}
