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
    private ArrayList<Point> originalPoints;
    private Polygon shape;
    private final PolygonShape polygonShape;
    private final WorldEditorScene worldScene;

    public PolygonWidget(WorldEditorScene scene, List<Point> pointList, PolygonShape polygonShape) {
        super(scene);
        this.worldScene = scene;
        this.polygonShape = polygonShape;
        int[] xpoints = new int[pointList.size()];
        int[] ypoints = new int[pointList.size()];
        for (int i = 0; i < pointList.size(); i++) {
            xpoints[i] = pointList.get(i).x;
            ypoints[i] = pointList.get(i).y;
        }
        shape = new Polygon(xpoints, ypoints, pointList.size());

        this.originalPoints = new ArrayList<Point>(pointList);
        this.points = new ArrayList<Point>();
        for (Point p : originalPoints) {
            points.add(new Point(p));
        }
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    @Override
    protected Rectangle calculateClientArea() {
        return shape.getBounds();
    }

    @Override
    protected void paintWidget() {
        if (oldBounds== null)oldBounds = getParentWidget().getBounds();
        Graphics2D g = getGraphics();
        Paint paint = g.getPaint();
        Color fg = getForeground();
        Color c = new Color(fg.getRed(), fg.getGreen(), fg.getBlue(), 100);
        g.setPaint(c);

        transform(getParentWidget().getBounds());
        g.fill(shape);
        g.setPaint(fg);
        g.draw(shape);
        g.setColor(fg);
        g.setPaint(paint);
        
    }
    Rectangle oldBounds;

    public void transform(Rectangle newBounds) {
        if (newBounds.equals(oldBounds))return;
        
        int x = oldBounds.x;
        int y = oldBounds.y;

       
        int[] xpoints = new int[originalPoints.size()];
        int[] ypoints = new int[originalPoints.size()];

        for (int i = 0; i < originalPoints.size(); i++) {
            points.get(i).setLocation(
                    (int) ((originalPoints.get(i).x - x) * ((double) newBounds.width / (double) oldBounds.width)
                    + x + (newBounds.x - oldBounds.x)),
                    (int) ((originalPoints.get(i).y - y) * ((double) newBounds.height / (double) oldBounds.height)
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
