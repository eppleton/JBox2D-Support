/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene.widgets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.ImageUtilities;

/**
 *
 * @author antonepple
 */
public class DotWidget extends Widget {

    private int diameter = 5;

    public DotWidget(Scene scene, Widget widget,
            WidgetAction.WidgetMouseEvent event) {
        super(scene);
        setPreferredLocation(widget.convertLocalToScene(event.getPoint()));
    }

    @Override
    protected Rectangle calculateClientArea() {
        int r = diameter / 2;
        return new Rectangle(-r, -r, diameter + 1, diameter + 1);
    }

    @Override
    protected void paintWidget() {
        super.paintWidget();
        Rectangle bounds = getBounds();
        int x = bounds.x + getBorder().getInsets().left;
        int y = bounds.y + getBorder().getInsets().top;
        Graphics2D g = getGraphics();
        Paint paint = g.getPaint();
        Color fg = getForeground();
        Color c = Color.BLACK;
        g.setPaint(c);
        g.fillOval(x, y, diameter, diameter);
        g.setPaint(paint);
    }
}
