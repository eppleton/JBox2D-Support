/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene.widgets.actions;

import de.eppleton.physics.editor.scene.widgets.BodyWidget;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import org.netbeans.api.visual.action.ResizeProvider;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author antonepple
 */
public class SynchronizingResizeProvider implements ResizeProvider {

    private Rectangle oldBounds;
    private Point oldLocation;

    @Override
    public void resizingStarted(Widget widget) {
        oldBounds = widget.getBounds();
        oldLocation = widget.getLocation();
    }

    @Override
    public void resizingFinished(Widget widget) {
        // assert widget instanceof ReportElementsWidget;
        Rectangle bounds = widget.getBounds();
      
        if (widget instanceof BodyWidget) {
            List<Widget> children = widget.getChildren();
            for (Widget child : children) {
 
            }
        }
        oldBounds = bounds;
    }
}
