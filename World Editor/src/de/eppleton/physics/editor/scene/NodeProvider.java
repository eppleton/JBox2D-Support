/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author eppleton
 */
public interface NodeProvider< S extends Shape, N extends Widget> {

    public Widget configureNode(WorldScene scene, N n, Body body, S shape, float offset_x, float offset_Y, int scale);//, Transform [] transforms);

    public boolean providesNodeFor(Body body, S shape);

}
