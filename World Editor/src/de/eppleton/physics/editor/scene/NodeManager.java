 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene;

import com.sun.xml.internal.ws.message.source.PayloadSourceMessage;
import de.eppleton.physics.editor.scene.widgets.CircleWidget;
import java.awt.Point;
import java.util.ArrayList;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 * NodeManager gives you a NodeProvider, which will create and configure JavaFX
 * Nodes for you. Add your own Providers for custom rendering.
 *
 * @author eppleton
 */
public class NodeManager {

    private static ArrayList<CircleProvider> circleProviders = new ArrayList<CircleProvider>();
    private static ArrayList<PolygonProvider> polygonProviders = new ArrayList<PolygonProvider>();
    private static PolygonProvider DEFAULT_POLYGON_PROVIDER;
    private static CircleProvider DEFAULT_CIRCLE_PROVIDER;

    public static void addCircleProvider(CircleProvider provider) {
        circleProviders.add(provider);
    }

    public static void removeProvider(CircleProvider provider) {
        circleProviders.remove(provider);
    }

    public static void addPolygonProvider(PolygonProvider provider) {
        polygonProviders.add(provider);
    }

    public static void removeProvider(PolygonProvider provider) {
        polygonProviders.remove(provider);
    }

    public static NodeProvider getNodeProvider(Body body, Shape shape) {

        if (shape instanceof CircleShape) {
            for (NodeProvider provider : circleProviders) {
                if (provider.providesNodeFor(body, shape)) {
                    return provider;
                }
            }
            if (DEFAULT_CIRCLE_PROVIDER == null) {
                DEFAULT_CIRCLE_PROVIDER = new DefaultCircleProvider();
            }
            return DEFAULT_CIRCLE_PROVIDER;
        } else if (shape instanceof PolygonShape) {
            for (NodeProvider provider : polygonProviders) {
                if (provider.providesNodeFor(body, shape)) {
                    return provider;
                }
            }
            if (DEFAULT_POLYGON_PROVIDER == null) {
                DEFAULT_POLYGON_PROVIDER = new DefaultPolygonProvider();
            }
            return DEFAULT_POLYGON_PROVIDER;
        }
        return null;
    }

    public static class DefaultPolygonProvider implements PolygonProvider<ConnectionWidget> {

        @Override
        public ConnectionWidget configureNode(WorldScene scene, ConnectionWidget polygon, Body body, PolygonShape shape, float offset_x, float offset_Y, int scale) {//, Transform[] transform) {
            if (polygon == null) {
                polygon = new ConnectionWidget(scene);
                ArrayList<Point> points = new ArrayList<Point>();
                for (int i = 0; i < shape.getVertexCount(); i++) {
                    Vec2 vec2 = shape.getVertex(i);
                    Vec2 transformed = org.jbox2d.common.Transform.mul(body.m_xf, vec2);
                    points.add(
                            new Point(
                            (int) ((transformed.x + offset_x) * scale),
                            (int) (((transformed.y * -1) + offset_Y) * scale)));
                }
                points.add(new Point(points.get(0).x, points.get(0).y));
                polygon.setControlPoints(points, false);
                addWidgetToScene(polygon, scene, body, offset_x, offset_Y, scale);


            } else {
                for (int i = 0; i < shape.getVertexCount(); i++) {
                    Vec2 vec2 = shape.getVertex(i);
                    Vec2 transformed = org.jbox2d.common.Transform.mul(body.m_xf, vec2);

                    polygon.getControlPoints().get(i).x =
                            (int) ((transformed.x + offset_x) * scale);
                    polygon.getControlPoints().get(i).y = (int) (((transformed.y * -1) + offset_Y) * scale);

                }
            }
            return polygon;
        }

        @Override
        public boolean providesNodeFor(Body body, PolygonShape shape) {
            // dummy, because this will never be asked for
            return shape instanceof PolygonShape;
        }
    }

    public static class DefaultCircleProvider implements CircleProvider<CircleWidget> {

        @Override
        public CircleWidget configureNode(WorldScene scene, CircleWidget circle, Body body, CircleShape shape, float offset_x, float offset_Y, int scale) {//, Transform[] transform) {

            if (circle == null) {
                circle = new CircleWidget(scene, (int) (shape.m_radius * scale));
                addWidgetToScene(circle, scene, body, offset_x, offset_Y, scale);

            }
            circle.setPreferredLocation(new Point((int) ((body.getPosition().x + offset_x) * scale),
                    (int) (((body.getPosition().y * -1) + offset_Y) * scale)));
//            
            return circle;
        }

        @Override
        public boolean providesNodeFor(Body body, CircleShape shape) {
            // dummy, because this will never be asked for
            return shape instanceof CircleShape;
        }
    }

    private static void addWidgetToScene(final Widget widget, final WorldScene scene, final Body payload, final float offset_x, final float offset_y, final int scale) {
        scene.addChild(widget);
        scene.addObject(payload, widget);
        widget.getActions().addAction(ActionFactory.createMoveAction());
        widget.getActions().addAction(ActionFactory.createResizeAction());
        widget.addDependency(new Widget.Dependency() {
            int x, y;

            @Override
            public void revalidateDependency() {
                if (widget.getPreferredLocation() != null) {
                    int newX = widget.getPreferredLocation().x;
                    int newY = widget.getPreferredLocation().y;
                    if ((newX != x || newY != y)) {
                        System.out.println("old "+payload.getPosition());
                        payload.getPosition().x = ((float) newX / (float) scale) - offset_x;
                        payload.getPosition().y = -1* (((float) newY / (float) scale) - offset_y);
                        System.out.println("new "+payload.getPosition());
                        scene.fireChange();
                    }
                }
            }
        });
    }
}
