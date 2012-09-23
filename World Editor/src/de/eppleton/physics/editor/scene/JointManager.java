/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene;

import de.eppleton.physics.editor.scene.widgets.DotWidget;
import de.eppleton.physics.editor.scene.widgets.FixedPointOnWidgetAnchor;
import java.awt.Color;
import java.awt.Point;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.JointType;
import org.netbeans.api.visual.model.ObjectScene;
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
        public void configureWidget(WorldEditorScene aThis, Widget widget, Joint nextJoint, float offsetX, float offsetY, int scale) {
            // Do Nothing
        }
    };
    private static JointProvider DISTANCEPROVIDER = new JointProvider() {
        @Override
        public void configureWidget(WorldEditorScene scene, Widget widget, Joint joint, float offsetX, float offsetY, int scale) {
            if (widget == null) {
                widget = new ConnectionWidget(scene);
                ((ConnectionWidget) widget).setLineColor(LINE_COLOR);

//                ArrayList<Point> points = new ArrayList<Point>();
                Widget bodyA = scene.findWidget(joint.m_bodyA);
                Widget bodyB = scene.findWidget(joint.m_bodyB);

                Vec2 anchorA = new Vec2();
                Vec2 anchorB = new Vec2();
                joint.getAnchorA(anchorA);
                joint.getAnchorB(anchorB);


                ((ConnectionWidget) widget).setSourceAnchor(new FixedPointOnWidgetAnchor(bodyA, new Point(
                        (int) ((anchorA.x + offsetX) * scale),
                        (int) (((anchorA.y * -1) + offsetY) * scale))));
                ((ConnectionWidget) widget).setTargetAnchor(new FixedPointOnWidgetAnchor(bodyB, new Point(
                        (int) ((anchorB.x + offsetX) * scale),
                        (int) (((anchorB.y * -1) + offsetY) * scale))));

                scene.addConnection(widget, joint);
                scene.validate();
            }



        }
    };
    private static JointProvider REVOLUTEJOINTPROVIDER = new JointProvider() {
        @Override
        public void configureWidget(WorldEditorScene scene, Widget widget, Joint joint, float offsetX, float offsetY, int scale) {
            if (widget == null) {
                Vec2 anchorA = new Vec2();
                joint.getAnchorA(anchorA);
                Point point = new Point(
                        (int) ((anchorA.x + offsetX) * scale),
                        (int) (((anchorA.y * -1) + offsetY) * scale));

                Widget bodyA = scene.findWidget(joint.m_bodyA);
                widget = new DotWidget(scene, bodyA.convertSceneToLocal(point));
                bodyA.addChild(widget);
                scene.addObject(joint, widget);
                scene.validate();
            }



        }
    };

    static JointProvider getJointProvider(Joint nextJoint) {
        JointProvider jointProvider = DUMMYPROVIDER;
        if (nextJoint.getType() == JointType.DISTANCE) {
            return DISTANCEPROVIDER;
        } else if (nextJoint.getType() == JointType.REVOLUTE) {
            return REVOLUTEJOINTPROVIDER;
        }
        return jointProvider;
    }
}
