/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene;

import de.eppleton.jbox2d.PolygonShapeBuilder;
import de.eppleton.jbox2d.WorldUtilities;
import de.eppleton.physics.editor.nodes.FakeChildFactory;
import de.eppleton.physics.editor.palette.items.B2DActiveEditorDrop;
import de.eppleton.physics.editor.scene.widgets.BodyWidget;
import de.eppleton.physics.editor.scene.widgets.DotWidget;
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
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import org.jbox2d.builders.DistanceJointBuilder;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.MoveProvider;
import org.netbeans.api.visual.action.ResizeProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.model.ObjectSceneEvent;
import org.netbeans.api.visual.model.ObjectSceneEventType;
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
public class WorldEditorScene extends ObjectScene {
    
    private static int FIXED_WIDTH = 100;
    public static final String DELETE_ACTION = "deleteAction";
    private static Logger LOGGER = Logger.getLogger(WorldScene.class.getName());
    // Tools
    public static String CREATE_SHAPE_TOOL = "createshapetool";
    public static String DISTANCE_JOINT_TOOL = "distancejointtool";
    public static String FRICTION_JOINT_TOOL = "frictionjointtool";
    public static String GEAR_JOINT_TOOL = "gearjointtool";
    public static String MOUSE_JOINT_TOOL = "mousejointtool";
    public static String PRISMATIC_JOINT_TOOL = "prismaticjointtool";
    public static String PULLEY_JOINT_TOOL = "pulleyjointtool";
    public static String RELD_JOINT_TOOL = "weldjointtool";
    public static String REVOLUTE_JOINT_TOOL = "revolutejointtool";
    public static String ROPE_JOINT_TOOL = "ropejointtool";
    public static String SELECT_TOOL = "selecttool";
    public static String WHEEL_JOINT_TOOL = "wheeljointtool";
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
    private CloseAndCreateShapeAction connectAction = new CloseAndCreateShapeAction();
    private CreateDistanceJointAction createDistanceJointAction = new CreateDistanceJointAction();
    private WidgetAction createAction = new CreatePolygonPointsAction();
    private ArrayList<DotWidget> bodyParts = new ArrayList<DotWidget>();
    private ArrayList<ConnectionWidget> connections = new ArrayList<ConnectionWidget>();
    private final World world;
    private transient ResizeProvider resizeProvider;
    private transient LayerWidget mainLayer;
    private transient LayerWidget connectionLayer;
    private transient LayerWidget backgroundLayer;
    private transient LayerWidget interactionLayer;
    private transient Widget backgroundLayerWidget;
    private transient WidgetAction moveAction;
    private transient ExplorerManager em;
    private transient Node root;
    private transient FakeChildFactory fakeChildren;
    private int scale = 30;
    private float offsetX = 0;
    private float offsetY = 0;
    
    public WorldEditorScene(final ExplorerManager em,
            final World world) {
        this.em = em;
        initScene();
        this.world = world;
        addBodiesFromWorld(world);
    }
    
    public final void initScene() {
        // setup layers
        backgroundLayer = new LayerWidget(this);
        addChild(backgroundLayer);
        mainLayer = new LayerWidget(this);
        addChild(mainLayer);
        connectionLayer = new LayerWidget(this);
        addChild(connectionLayer);
        interactionLayer = new LayerWidget(this);
        addChild(interactionLayer);
        initBackground();

        // initialize Tools
        createActions(CREATE_SHAPE_TOOL);
        createActions(FRICTION_JOINT_TOOL);
        createActions(GEAR_JOINT_TOOL);
        createActions(MOUSE_JOINT_TOOL);
        createActions(PRISMATIC_JOINT_TOOL);
        createActions(PULLEY_JOINT_TOOL);
        createActions(RELD_JOINT_TOOL);
        createActions(REVOLUTE_JOINT_TOOL);
        createActions(ROPE_JOINT_TOOL);
        createActions(SELECT_TOOL);
        createActions(WHEEL_JOINT_TOOL);
        createActions(SELECT_TOOL);

        // initialize and add actions
        getActions().addAction(ActionFactory.createAcceptAction(new AcceptProviderImpl()));
        getActions(CREATE_SHAPE_TOOL).addAction(createAction);
        
        moveAction = ActionFactory.createMoveAction(null, new MultiMoveProvider());
        getActions(SELECT_TOOL).addAction(ActionFactory.createRectangularSelectAction(this, backgroundLayer));
        // select is active by default
        setActiveTool(SELECT_TOOL);
        //super.getActions().addAction(ActionFactory.createZoomAction());
        // TODO reenable this
        fakeChildren = new FakeChildFactory(this);
        root = new AbstractNode(Children.create(fakeChildren, false));
        em.setRootContext(root);
        addObjectSceneListener(
                new AbstractObjectSceneListener() {
                    @Override
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
                }, ObjectSceneEventType.OBJECT_SELECTION_CHANGED);
    }
    
    private void initBackground() {
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
        backgroundLayerWidget.addChild(new PolygonWidget(this, pointsXAxis, null));
        backgroundLayer.addChild(backgroundLayerWidget);
        repaint();
        revalidate(false);
        validate();
    }
    
    private Widget createBackgroundWidget(int x, int y, int width, int height) {
        Widget back = new Widget(this);
        back.setCheckClipping(true);
        back.setOpaque(true);
        back.setPreferredBounds(new Rectangle(x, y, width, height));
        back.setBorder(BorderFactory.createLineBorder(1));
        return back;
    }
    
    private void addBody(Body bodyToAdd) {
        if (bodyToAdd.getFixtureList() != null) {
            Fixture fixture = bodyToAdd.getFixtureList();
            while (fixture != null) {
                Shape shape = fixture.getShape();
                WidgetProvider nodeProvider = WidgetManager.getWidgetProvider(bodyToAdd, shape);
                Widget widget = super.findWidget(shape);
                nodeProvider.configureWidget(this, widget, bodyToAdd, shape, offsetX, offsetY, scale);//, transform);}
                fixture = fixture.getNext();
            }
        }
    }
    
    private void addJoint(Joint nextJoint) {
        JointProvider jointProvider = JointManager.getJointProvider(nextJoint);
        ConnectionWidget widget = (ConnectionWidget) super.findWidget(nextJoint);
        jointProvider.configureWidget(this, widget, nextJoint, offsetX, offsetY, scale);
    }
    
    void addConnection(ConnectionWidget widget, Joint joint) {
        connectionLayer.addChild(widget);
        addObject(joint, widget);
        validate();
    }
    
    public ResizeProvider getResizeProvider() {
        if (resizeProvider == null) {
            resizeProvider = new SynchronizingResizeProvider();
        }
        return resizeProvider;
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
    
    public float getOffsetY() {
        return offsetY;
    }
    
    public float getOffsetX() {
        return offsetX;
    }
    
    public int getScale() {
        return scale;
    }
    
    public WidgetAction getMoveAction() {
        return moveAction;
    }
    
    public LayerWidget getMainLayer() {
        return mainLayer;
    }
    
    private void ensureCCW(ArrayList<DotWidget> bodyParts) {
        if (bodyParts.size() >= 3) {
            boolean ccw = WorldUtilities.ccw(
                    bodyParts.get(0).getPreferredLocation(),
                    bodyParts.get(1).getPreferredLocation(),
                    bodyParts.get(2).getPreferredLocation());
            if (!ccw) {
                ArrayList<DotWidget> reordered = new ArrayList<DotWidget>();
                for (int i = 0; i < bodyParts.size(); i++) {
                    reordered.add(bodyParts.size() - i, bodyParts.get(i));
                }
                bodyParts.clear();
                bodyParts.addAll(reordered);
            }
        }
    }
    
    public WidgetAction getDistanceJointAction() {
        return createDistanceJointAction;
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
                }
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
            
        }
    }
    
    @Override
    public void setActiveTool(String activeTool) {
        super.setActiveTool(activeTool);
        System.out.println("active Tool " + activeTool);
    }
    
    private void handleTransfer(Point point, B2DActiveEditorDrop transferData) {
        HashMap<Integer, Joint> jointMap = new HashMap<Integer, Joint>();
        
        HashMap<Integer, Body> addBodies = new HashMap<Integer, Body>();
        transferData.addBodies(world, jointMap, addBodies);
        float x = WorldUtilities.sceneToWorld(point.x, scale, offsetX, false);
        float y = WorldUtilities.sceneToWorld(point.y, scale, offsetY, false);
        configureBodies(addBodies, x, y);
        Collection<Body> values = addBodies.values();
        for (Body body : values) {
            addBody(body);
        }
        Collection<Joint> values1 = jointMap.values();
        for (Joint joint : values1) {
            addJoint(joint);
        }
        
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
    
    public void addBodiesFromWorld(World world) {
        Body nextBody = world.getBodyList();
        while (nextBody != null) {
            addBody(nextBody);
            nextBody = nextBody.getNext();
        }
        if (getView() != null && getView().isShowing()) {
            Joint nextJoint = world.getJointList();
            while (nextJoint != null) {
                addJoint(nextJoint);
                nextJoint = nextJoint.getNext();
            }
        }
        repaint();
    }
    
    public World createWorldFromScene() {
        // TODO use real values from File
        World world = new World(new Vec2(0, -7));
        // TODO init the World from this Scenes Widgets
        return world;
    }
    
    private class MultiMoveProvider implements MoveProvider {
        
        private HashMap<Widget, Point> originals = new HashMap<Widget, Point>();
        private Point original;
        
        public void movementStarted(Widget widget) {
            originals.put(widget, widget.getPreferredLocation());
            for (Object o : getSelectedObjects()) {
                Widget w = findWidget(o);
                if (w != null && (w instanceof BodyWidget)) {
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
    
    @Override
    public JComponent createView() {
        super.createView();
        addKeyboardActions();
        
        return getView();
        
    }
    
    public void addKeyboardActions() {
        getView().setFocusable(true);
        // getActions().addAction(new MouseClickedAction(getView()));

        getView().getInputMap().put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0), "deleteSelectedWidgets");
        getView().getActionMap().put("deleteSelectedWidgets", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                // TODO implement this or remove keyboard actions
                // deleteselectedWidgets();
            }
        });
        
    }
    
    private void createNewShape(ArrayList<DotWidget> bodyParts) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        for (DotWidget dotWidget : bodyParts) {
            Point preferredLocation = dotWidget.getPreferredLocation();
            if (preferredLocation.x < minX) {
                minX = preferredLocation.x;
            }
            if (preferredLocation.y < minY) {
                minY = preferredLocation.y;
            }
        }
        
        ensureCCW(bodyParts);
        
        float worldX = WorldUtilities.sceneToWorld(minX, scale, offsetX, false);
        float worldY = WorldUtilities.sceneToWorld(minY, scale, offsetY, true);
        
        Vec2[] vertices = new Vec2[bodyParts.size()];
        for (int i = 0; i < bodyParts.size(); i++) {
            DotWidget dot = bodyParts.get(i);
            Point preferredLocation = dot.getPreferredLocation();
            float worldX1 = WorldUtilities.sceneToWorld(preferredLocation.x - minX, scale, offsetX, false);
            float worldY1 = WorldUtilities.sceneToWorld(preferredLocation.y - minY, scale, offsetY, true);
            vertices[i] = new Vec2(worldX1, worldY1);
            mainLayer.removeChild(dot);
        }
        for (ConnectionWidget con : connections) {
            connectionLayer.removeChild(con);
        }
        Body build = new PolygonShapeBuilder(world).active(true).type(BodyType.STATIC).position(worldX, worldY).vertices(vertices).build();
        addBody(build);
    }
    
    private class CloseAndCreateShapeAction extends WidgetAction.Adapter {
        
        @Override
        public WidgetAction.State mousePressed(Widget widget,
                WidgetAction.WidgetMouseEvent event) {
            if (event.getClickCount() == 1) {
                if (event.getButton() == MouseEvent.BUTTON1
                        || event.getButton() == MouseEvent.BUTTON2) {
                    
                    
                    if (bodyParts != null && bodyParts.size() > 1) {
                        ConnectionWidget conn = new ConnectionWidget(WorldEditorScene.this);
                        conn.setTargetAnchor(AnchorFactory.createCircularAnchor(widget, 3));
                        conn.setSourceAnchor(AnchorFactory.createCircularAnchor(bodyParts.get(bodyParts.size() - 1), 3));
                        widget.getActions().removeAction(connectAction);
                        // connectionLayer.addChild(conn);
                        createNewShape(bodyParts);
                        connections.clear();
                        bodyParts.clear();
                    }
                    
                    repaint();
                    return WidgetAction.State.CONSUMED;
                }
            }
            return WidgetAction.State.REJECTED;
        }
    }
    
    private class CreatePolygonPointsAction extends WidgetAction.Adapter {
        
        @Override
        public WidgetAction.State mousePressed(Widget widget,
                WidgetAction.WidgetMouseEvent event) {
            if (event.getClickCount() == 1) {
                if (event.getButton() == MouseEvent.BUTTON1
                        || event.getButton() == MouseEvent.BUTTON2) {
                    
                    DotWidget blackDotWidget = new DotWidget(WorldEditorScene.this, widget, widget.convertLocalToScene(event.getPoint()));
                    if (bodyParts.size() == Settings.maxPolygonVertices) {
                        ConnectionWidget conn = new ConnectionWidget(WorldEditorScene.this);
                        conn.setTargetAnchor(AnchorFactory.createCircularAnchor(widget, 3));
                        conn.setSourceAnchor(AnchorFactory.createCircularAnchor(bodyParts.get(bodyParts.size() - 1), 3));
                        widget.getActions().removeAction(connectAction);
                        // connectionLayer.addChild(conn);
                        createNewShape(bodyParts);
                        connections.clear();
                        bodyParts.clear();
                    } else {
                        mainLayer.addChild(blackDotWidget);
                        // TODO create new Widget and switch state to drawing Mode
                        // save this as the last drawn Widget.
                        // if last drawn not is null, make a connection
                        if (bodyParts != null && bodyParts.size() > 0 && bodyParts.size() < Settings.maxPolygonVertices) {
                            ConnectionWidget conn = new ConnectionWidget(WorldEditorScene.this);
                            conn.setTargetAnchor(AnchorFactory.createCircularAnchor(blackDotWidget, 3));
                            conn.setSourceAnchor(AnchorFactory.createCircularAnchor(bodyParts.get(bodyParts.size() - 1), 3));
                            connectionLayer.addChild(conn);
                            connections.add(conn);
                        }
                        bodyParts.add(blackDotWidget);
                        if (bodyParts.size() == 1) {
                            blackDotWidget.getActions().addAction(connectAction);
                        }
                        repaint();
                    }
                    return WidgetAction.State.CONSUMED;
                }
            }
            return WidgetAction.State.REJECTED;
        }
    }
    
    private class CreateDistanceJointAction extends WidgetAction.Adapter {
        
        private DotWidget dot1;
        
        @Override
        public WidgetAction.State mousePressed(Widget widget,
                WidgetAction.WidgetMouseEvent event) {
            if (event.getClickCount() == 1) {
                if (event.getButton() == MouseEvent.BUTTON1
                        || event.getButton() == MouseEvent.BUTTON2) {
                    
                    DotWidget blackDotWidget = new DotWidget(WorldEditorScene.this, widget, event.getPoint());
                    if (dot1 != null) {
                        Widget otherParent = dot1.getParentWidget();
                        if (widget == otherParent) {
                            return WidgetAction.State.REJECTED;
                        }
                        Point sourcePoint = otherParent.convertLocalToScene(dot1.getLocation());
                        Vec2 sourceVec = new Vec2(WorldUtilities.sceneToWorld(sourcePoint.x, scale, offsetX, false),
                                WorldUtilities.sceneToWorld(sourcePoint.y, scale, offsetX, true));
                        Body sourceBody = (Body) ((ObjectScene) widget.getScene()).findObject(otherParent);
                        
                        Point targetPoint = widget.convertLocalToScene(event.getPoint());
                        Vec2 targetVec = new Vec2(WorldUtilities.sceneToWorld(targetPoint.x, scale, offsetX, false),
                                WorldUtilities.sceneToWorld(targetPoint.y, scale, offsetX, true));
                        Body targetBody = (Body) ((ObjectScene) widget.getScene()).findObject(widget);
                        
                        Joint build = new DistanceJointBuilder(world, sourceBody, targetBody, sourceVec, targetVec).build();
                        addJoint(build);
                        otherParent.removeChild(dot1);
                        dot1 = null;
                        
                    } else {
                        widget.addChild(blackDotWidget);
                        dot1 = blackDotWidget;
                        repaint();
                    }
                    return WidgetAction.State.CONSUMED;
                }
            }
            return WidgetAction.State.REJECTED;
        }
    }
}
