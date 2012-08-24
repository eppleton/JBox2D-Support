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
import java.awt.Shape;
import java.util.List;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author antonepple
 */
public class PolygonWidget extends Widget {

    private Shape shape;

    public PolygonWidget(Scene scene, int[] xPoints, int[] yPoints) {
        super(scene);
        setPolygonPoints(xPoints, yPoints);
    }

    public void setPolygonPoints(int[] xPoints, int[] yPoints) {
        shape = new Polygon(xPoints, yPoints, xPoints.length);
    }

        @Override
    protected Rectangle calculateClientArea() {
        return shape.getBounds();
    }
    
    @Override
    protected void paintWidget() {
        Rectangle bounds = getBounds();
        int x = bounds.x + getBorder().getInsets().left;
        int y = bounds.y + getBorder().getInsets().top;
        int width = bounds.width - getBorder().getInsets().left - getBorder().getInsets().right;
        int height = bounds.height - getBorder().getInsets().top - getBorder().getInsets().bottom;

        Graphics2D g = getGraphics();
        Paint paint = g.getPaint();
        g.setPaint(getBackground());
        g.fill(shape);
        //g.fillOval(x,y, diameter, diameter);
        g.setColor(getForeground());
        g.draw(shape);
//        g.drawOval(x,y, diameter, diameter);
        g.setPaint(paint);
    }

    @Override
    protected void notifyStateChanged(ObjectState previousState, ObjectState state) {
        super.notifyStateChanged(previousState, state);
        setBorder(state.isSelected() ? BorderFactory.createResizeBorder(6, Color.GRAY, true) : BorderFactory.createEmptyBorder(6));
        if (state.isSelected()) {
            setBackground(Color.BLUE);
        } else {
            setBackground(Color.WHITE);
        }

    }
}
