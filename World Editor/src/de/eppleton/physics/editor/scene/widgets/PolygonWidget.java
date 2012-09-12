/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene.widgets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author antonepple
 */
public class PolygonWidget extends Widget {

    private ArrayList<Point> points;
    private Polygon shape;

    public PolygonWidget(Scene scene, List<Point> points) {
        super(scene);
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

    @Override
    protected void paintWidget() {
        Graphics2D g = getGraphics();
        Paint paint = g.getPaint();
        Color fg = getForeground();
        Color c = new Color(fg.getRed(), fg.getGreen(), fg.getBlue(), 100);
        g.setPaint(c);
        System.out.print(".");
        transform(shape.getBounds(), getBounds());
        g.fill(shape);
        g.setColor(fg);
        /*
         int x = points.get(0).x;
         int y = points.get(0).y;
         for (int i = 1; i < points.size(); i++) {
         int xTo = points.get(i).x;
         int yTo = points.get(i).y;
         g.drawLine(x, y, xTo, yTo);
         x = xTo;
         y = yTo;
         }
         g.drawLine(x, y, points.get(0).x, points.get(0).y);*/
        g.setPaint(paint);
    }
//
//    @Override
//    protected void notifyStateChanged(ObjectState previousState, ObjectState state) {
//        super.notifyStateChanged(previousState, state);
//        setBorder(state.isSelected() ? BorderFactory.createResizeBorder(6, Color.GRAY, true) : BorderFactory.createEmptyBorder(6));
//        if (state.isSelected()) {
//            setBackground(Color.BLUE);
//        } else {
//            setBackground(Color.WHITE);
//        }

//    }
    
    public void transform(Rectangle oldBounds, Rectangle newBounds) {
        int x = shape.getBounds().x;
        int y = shape.getBounds().y;

        int[] xpoints = new int[points.size()];
        int[] ypoints = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            points.get(i).setLocation(
                    (int) ((points.get(i).x - x) * ((double) newBounds.width / (double) oldBounds.width)
                    + x +(newBounds.x - oldBounds.x)),
                    (int) ((points.get(i).y - y) * ((double) newBounds.height / (double) oldBounds.height)
                    + y+(newBounds.y - oldBounds.y)));
            xpoints[i] = points.get(i).x;
            ypoints[i] = points.get(i).y;

        }
        shape = new Polygon(xpoints, ypoints, points.size());
        getScene().getView().invalidate();

    }
}
