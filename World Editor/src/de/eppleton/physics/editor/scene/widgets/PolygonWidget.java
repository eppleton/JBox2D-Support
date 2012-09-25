/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene.widgets;

import de.eppleton.physics.editor.scene.WorldEditorScene;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import org.jbox2d.collision.shapes.PolygonShape;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author antonepple
 */
public class PolygonWidget extends Widget {

    private ArrayList<Point> points;
    private Polygon shape;
    private final PolygonShape polygonShape;
    private final WorldEditorScene worldScene;

    public PolygonWidget(WorldEditorScene scene, List<Point> points, PolygonShape polygonShape) {
        super(scene);
        this.worldScene = scene;
        this.polygonShape = polygonShape;
        int[] xpoints = new int[points.size()];
        int[] ypoints = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            xpoints[i] = points.get(i).x;
            ypoints[i] = points.get(i).y;
        }
        shape = new Polygon(xpoints, ypoints, points.size());

        this.points = new ArrayList<Point>(points);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    @Override
    protected Rectangle calculateClientArea() {
        return shape.getBounds();
    }
    int oldWidth;
    int oldHeight;

    @Override
    protected void paintWidget() {
        Graphics2D g = getGraphics();
        Paint paint = g.getPaint();
        Color fg = getForeground();
        Color c = new Color(fg.getRed(), fg.getGreen(), fg.getBlue(), 100);
        g.setPaint(c);
        if (oldWidth != getBounds().width || oldHeight != getBounds().height) {
            transform(shape.getBounds(), getBounds());
            worldScene.update();
            oldWidth = getBounds().width;
            oldHeight = getBounds().height;
        }
        g.fill(shape);
        g.setPaint(fg);
        g.draw(shape);
        g.setColor(fg);
        g.setPaint(paint);


    }

    public void transform(Rectangle oldBounds, Rectangle newBounds) {
        int x = shape.getBounds().x;
        int y = shape.getBounds().y;

        int[] xpoints = new int[points.size()];
        int[] ypoints = new int[points.size()];

        for (int i = 0; i < points.size(); i++) {
            points.get(i).setLocation(
                    (int) ((points.get(i).x - x) * ((double) newBounds.width / (double) oldBounds.width)
                    + x + (newBounds.x - oldBounds.x)),
                    (int) ((points.get(i).y - y) * ((double) newBounds.height / (double) oldBounds.height)
                    + y + (newBounds.y - oldBounds.y)));
            xpoints[i] = points.get(i).x;
            ypoints[i] = points.get(i).y;
            if (polygonShape != null) {
                polygonShape.m_vertices[i].x = (float) points.get(i).x / (float) worldScene.getScale();
                polygonShape.m_vertices[i].y = ((float) points.get(i).y * -1) / (float) worldScene.getScale();
            }
        }
        shape = new Polygon(xpoints, ypoints, points.size());

        getScene().getView().invalidate();

    }
}
