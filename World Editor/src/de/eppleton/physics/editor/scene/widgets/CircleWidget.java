/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene.widgets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

public class CircleWidget extends Widget {

    private int diameter;

    public CircleWidget(Scene scene, int radius) {
        super(scene);
        setRadius(radius);
    }

    @Override
    protected Rectangle calculateClientArea() {
        return new Rectangle(0, 0, diameter + 1, diameter + 1);
    }

    @Override
    protected void paintWidget() {
        Rectangle bounds = getBounds();
        int x = bounds.x + getBorder().getInsets().left;
        int y = bounds.y + getBorder().getInsets().top;
        int width = bounds.width - getBorder().getInsets().left - getBorder().getInsets().right;
        int height = bounds.height - getBorder().getInsets().top - getBorder().getInsets().bottom;
        diameter = height > width? width : height;
        Graphics2D g = getGraphics();
        Paint paint = g.getPaint();
        g.setPaint(getBackground());
        g.fillOval(x,y, diameter, diameter);
        g.setColor(getForeground());
        g.drawOval(x,y, diameter, diameter);
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

    public void setRadius(int r) {
        this.diameter = r*2;
    }

    public int getRadius() {
        return diameter/2;
    }
}