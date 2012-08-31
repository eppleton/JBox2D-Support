/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene.widgets;

import java.awt.Point;
import java.awt.Rectangle;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author antonepple
 */
public class FixedPointOnWidgetAnchor extends Anchor {

    int px, py;

    public FixedPointOnWidgetAnchor(Widget bodyA, Point point) {
        super(bodyA);
        Rectangle bounds = bodyA.getBounds();
        px = point.x - ( bodyA.getLocation().x + bounds.x );
        py = point.y - ( bodyA.getLocation().y + bounds.y );
    }

    @Override
    public Result compute(Entry entry) {
        return new Result(getPointInSceneLocation(), Anchor.DIRECTION_ANY);
    }

    private Point getPointInSceneLocation() {
        Rectangle bounds = getRelatedWidget().getBounds();
        int x = getRelatedWidget().getLocation().x + bounds.x;
        int y = getRelatedWidget().getLocation().y + bounds.y;
        return new Point(x + px, y + py);
    }
    
    
}
