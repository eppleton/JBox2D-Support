/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene.widgets;

import de.eppleton.jbox2d.WorldUtilities;
import de.eppleton.physics.editor.scene.WorldEditorScene;
import java.awt.Color;
import org.jbox2d.dynamics.Body;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.Widget.Dependency;

/**
 *
 * @author antonepple
 */
public class BodyWidget extends Widget implements Dependency {

    Body body;
    WorldEditorScene scene;

    public BodyWidget(WorldEditorScene scene, Body body) {
        super(scene);
        this.scene = scene;
        this.body = body;
        scene.getMainLayer().addChild(this);
        scene.addObject(body, this);
        addActions(scene, body);
    }

    final void addActions(final WorldEditorScene scene, final Body body) {
        getActions().addAction(scene.createSelectAction());
        getActions().addAction(ActionFactory.createResizeAction(null, scene.getResizeProvider()));
        getActions().addAction(scene.getMoveAction());
        addDependency(this);
    }

    @Override
    protected void notifyStateChanged(ObjectState previousState, ObjectState state) {
        super.notifyStateChanged(previousState, state);
        //setBorder(state.isSelected() ? BorderFactory.createResizeBorder(6, Color.GRAY, true) : BorderFactory.createEmptyBorder(6));
        setBorder(state.isSelected() ? BorderFactory.createResizeBorder(4) : BorderFactory.createEmptyBorder(4));
        if (state.isSelected()) {
            setBackground(Color.BLUE);
        } else {
            setBackground(Color.WHITE);
        }

    }
    int x, y, width, height;

    @Override
    public void revalidateDependency() {

        if (getLocation() != null) {
            int newX = getLocation().x;
            int newY = getLocation().y;
            if ((newX != x || newY != y)) {
                body.getPosition().x = WorldUtilities.sceneToWorld(newX, scene.getScale(), scene.getOffsetX(), false);
                body.getPosition().y = WorldUtilities.sceneToWorld(newY, scene.getScale(), scene.getOffsetY(), true);
                x = newX;
                y = newY;
//                                scene.fireChange();
            }
        }
        /*
         Rectangle bounds = containerWidget.getBounds();
         if (bounds != null) {
         int newHeight = bounds.height;
         int newWidth = bounds.width;
         if (newHeight != height || newWidth != width) {
         Fixture fixture = body.getFixtureList();
         while (fixture != null) {
         Shape shape = fixture.getShape();
         if (shape.getType() == ShapeType.CIRCLE) {
         float radius = shape.getRadius();
         int oldSmallerSide = width < height ? width : height;
         int newSmallerSide = newWidth < newHeight ? newWidth : newHeight;
         float ratio = (float) newSmallerSide / (float) oldSmallerSide;
         //System.out.println("olds " + oldSmallerSide + " newS " + newSmallerSide + " ratio " + ratio);
         if (!Float.isInfinite(ratio)) {
         shape.setRadius(radius * ratio);
         Widget widget = scene.findWidget(shape);
         //DEFAULT_CIRCLE_PROVIDER.configureWidget(scene, widget, body, shape, offset_x, offset_y, scale);
         //                                            ((CircleWidget)widget).setRadius((int) (shape.m_radius * scale));
         //                                            System.out.println("### setting the circles radius ");
         }
         }
         fixture = fixture.getNext();
         }
         width = newWidth;
         height = newHeight;
         scene.fireChange();
         }

         }*/
    }
}
