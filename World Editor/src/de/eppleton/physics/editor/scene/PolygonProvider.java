/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene;

import org.jbox2d.collision.shapes.PolygonShape;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author eppleton
 */
public interface PolygonProvider<n extends Widget> extends NodeProvider<PolygonShape, n> {
    
}
