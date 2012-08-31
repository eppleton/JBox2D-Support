/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene;

import de.eppleton.physics.editor.scene.widgets.FixedPointOnWidgetAnchor;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.JointType;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author antonepple
 */
class JointManager {

    private static Color LINE_COLOR = new Color(0.5f, 0.8f, 0.8f);
    private static JointProvider DUMMYPROVIDER = new JointProvider() {
        @Override
        public void configureWidget(WorldScene aThis, ConnectionWidget widget, Joint nextJoint, float offsetX, float offsetY, int scale) {
            // Do Nothing
        }
    };
    private static JointProvider DISTANCEPROVIDER = new JointProvider() {
        @Override
        public void configureWidget(WorldScene scene, ConnectionWidget widget, Joint joint, float offsetX, float offsetY, int scale) {
            if (widget == null) {
                widget = new ConnectionWidget(scene);
                widget.setLineColor(LINE_COLOR);
         
              
//                ArrayList<Point> points = new ArrayList<Point>();
                Widget bodyA = scene.findWidget(joint.m_bodyA);
                Widget bodyB = scene.findWidget(joint.m_bodyB);
                Vec2 anchorA = new Vec2();
                Vec2 anchorB = new Vec2();
                joint.getAnchorA(anchorA);
                joint.getAnchorB(anchorB);
      

                widget.setSourceAnchor(new FixedPointOnWidgetAnchor(bodyA, new Point(
                        (int) ((anchorA.x + offsetX) * scale),
                        (int) (((anchorA.y * -1) + offsetY) * scale))));
                widget.setTargetAnchor(new FixedPointOnWidgetAnchor(bodyB, new Point(
                        (int) ((anchorB.x + offsetX) * scale),
                        (int) (((anchorB.y * -1) + offsetY) * scale))));

                scene.addWidgetToScene(widget, joint, offsetX, offsetY, scale);
            }



        }
    };

    static JointProvider getJointProvider(Joint nextJoint) {
        JointProvider jointProvider = DUMMYPROVIDER;
        if (nextJoint.getType() == JointType.DISTANCE) {
            return DISTANCEPROVIDER;
        }
        return jointProvider;
    }
}
