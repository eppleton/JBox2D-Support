/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene;

import org.jbox2d.dynamics.joints.Joint;
import org.netbeans.api.visual.widget.ConnectionWidget;

/**
 *
 * @author antonepple
 */
interface JointProvider {

    public void configureWidget(WorldScene aThis, ConnectionWidget widget, Joint nextJoint, float offsetX, float offsetY, int scale);
    
}
