/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import org.jbox2d.common.Vec2;
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

    private static Color TRANSPARENT_GREEN = new Color(100, 255, 100, 100);
    private static JointProvider DUMMYPROVIDER = new JointProvider() {
        @Override
        public void configureWidget(WorldScene aThis, ConnectionWidget widget, Joint nextJoint, float offsetX, float offsetY, int scale) {
            // Do Nothing
        }
    };
    private static JointProvider DISTANCEPROVIDER = new JointProvider() {
        @Override
        public void configureWidget(WorldScene scene, ConnectionWidget widget, Joint nextJoint, float offsetX, float offsetY, int scale) {
            if (widget == null) {
                widget = new ConnectionWidget(scene);
                widget.setForeground(TRANSPARENT_GREEN);

//                ArrayList<Point> points = new ArrayList<Point>();
                Widget bodyA = scene.findWidget(nextJoint.m_bodyA);
                Widget bodyB = scene.findWidget(nextJoint.m_bodyB);
                Vec2 anchorA = new Vec2();
                Vec2 anchorB = new Vec2();
                nextJoint.getAnchorA(anchorA);
                nextJoint.getAnchorB(anchorB);

                widget.setSourceAnchor(AnchorFactory.createCenterAnchor(bodyA));
                widget.setTargetAnchor(AnchorFactory.createCenterAnchor(bodyB));

//                        new Point(
//                        (int) ((anchorA.x + offsetX) * scale),
//                        (int) (((anchorA.y * -1) + offsetY) * scale));
//                points.add(
//                        new Point(
//                        (int) ((anchorB.x + offsetX) * scale),
//                        (int) (((anchorB.y * -1) + offsetY) * scale)));

                //widget.setControlPoints(points, false);
                
                scene.addWidgetToScene(widget, nextJoint, offsetX, offsetY, scale);
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
