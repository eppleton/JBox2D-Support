 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene;

import de.eppleton.physics.editor.scene.widgets.CircleWidget;
import de.eppleton.physics.editor.scene.widgets.PolygonWidget;
import java.awt.Point;
import java.util.ArrayList;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.netbeans.api.visual.widget.ConnectionWidget;

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

    public static class DefaultPolygonProvider implements PolygonProvider<PolygonWidget> {

        @Override
        public PolygonWidget configureNode(WorldScene scene, PolygonWidget polygon, Body body, PolygonShape shape, float offset_x, float offset_Y, int scale) {//, Transform[] transform) {
            Transform xf = body.getTransform();
            if (polygon == null) {
                System.out.println("scale "+scale);
                ArrayList<Point> points = new ArrayList<>();
                for (int i = 0; i < shape.getVertexCount(); i++) {
                    Vec2 transformed = new Vec2();
                    System.out.println("Originalpunkt: " + shape.m_vertices[i]);
                    Transform.mulToOutUnsafe(xf, shape.m_vertices[i], transformed);
                    System.out.println("Transformiert: " + transformed);
                    transformed.x = transformed.x - body.getPosition().x;
                    transformed.y = transformed.y - body.getPosition().y;
                    System.out.println("Relat. zu Loc: " + transformed);
                    Point point = new Point(
                            (int) ((transformed.x) * scale),
                            (int) ((transformed.y * -1) * scale));
                    System.out.println("Screen coord: " + point);

                    points.add(point);
                }



                polygon = new PolygonWidget(scene, points);
                Point point = new Point(
                                      (int) ((body.getPosition().x + offset_x) * scale),
                                      (int) (((body.getPosition().y * -1) + offset_Y) * scale));
                polygon.setPreferredLocation(point);
                // polygon.setPreferredLocation(new Point(0, 0));
                scene.addWidgetToScene(polygon, body, offset_x, offset_Y, scale);
                System.out.println("SPolygon pos: " + point);
            } else {
                for (int i = 0; i < shape.getVertexCount(); i++) {
                    Vec2 transformed = new Vec2();
                    Transform.mulToOutUnsafe(xf, shape.m_vertices[i], transformed);

                    transformed.x = transformed.x - body.getPosition().x;
                    transformed.y = transformed.y - body.getPosition().y;
                    //Vec2 transformed = vec2;
                    polygon.getPoints().get(i).x = (int) ((transformed.x) * scale);
                    polygon.getPoints().get(i).y = (int) ((transformed.y * -1) * scale);
                }
                polygon.setPreferredLocation(new Point(
                        (int) ((body.getPosition().x + offset_x) * scale),
                        (int) (((body.getPosition().y * -1) + offset_Y) * scale)));

            }
            return polygon;
        }

        @Override
        public boolean providesNodeFor(Body body, PolygonShape shape) {
            // dummy, because this will never be asked for
            return shape instanceof PolygonShape;
        }

        public int normalize(int[] values) {
            int min = Integer.MAX_VALUE;
            for (int i : values) {
                if (i < min) {
                    min = i;
                }
            }
            for (int i = 0; i < values.length; i++) {
                values[i] = values[i] + min;

            }
            return min;
        }
    }

    public static class DefaultCircleProvider implements CircleProvider<CircleWidget> {

        @Override
        public CircleWidget configureNode(WorldScene scene, CircleWidget circle, Body body, CircleShape shape, float offset_x, float offset_Y, int scale) {//, Transform[] transform) {

            if (circle == null) {

                circle = new CircleWidget(scene, (int) (shape.m_radius * scale));
                scene.addWidgetToScene(circle, body, offset_x, offset_Y, scale);
            }
            Transform xf = body.getTransform();
            Vec2 center = new Vec2();
            Transform.mulToOutUnsafe(xf, shape.m_p, center);
            circle.setPreferredLocation(new Point((int) ((center.x + offset_x) * scale),
                    (int) (((center.y * -1) + offset_Y) * scale)));
            scene.validate();
            return circle;
        }

        @Override
        public boolean providesNodeFor(Body body, CircleShape shape) {
            // dummy, because this will never be asked for
            return shape instanceof CircleShape;
        }
    }

    private void dump() {
    }
}
