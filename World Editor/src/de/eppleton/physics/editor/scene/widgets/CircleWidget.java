/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene.widgets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

public class CircleWidget extends Widget {

    private int r;

    public CircleWidget(Scene scene, int radius) {
        super(scene);
        r = radius;
    }

    protected Rectangle calculateClientArea() {
        return new Rectangle(-r, -r, 2 * r + 1, 2 * r + 1);
    }

    protected void paintWidget() {
        Graphics2D g = getGraphics();
        Paint paint = g.getPaint();
        g.setPaint(getBackground());
        g.fillOval(-r, -r, 2 * r, 2 * r);

        g.setColor(getForeground());
        g.drawOval(-r, -r, 2 * r, 2 * r);
        g.setPaint(paint);
    }

    @Override
    protected void notifyStateChanged(ObjectState previousState, ObjectState state) {
        System.out.println("notifyStateChanged " + state);
        super.notifyStateChanged(previousState, state);
        if (state.isSelected()) {

            setBackground(Color.BLUE);
        } else {

            setBackground(Color.WHITE);
        }
       
    }
}