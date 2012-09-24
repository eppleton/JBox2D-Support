/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.persistence;

import javax.xml.bind.annotation.XmlRootElement;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.JointType;

/**
 *
 * @author antonepple
 */
@XmlRootElement
public class PrismaticJoint extends Joint {
    public Vec2 localAnchorA;
    public Vec2 localAnchorB;

    public PrismaticJoint() {
        this.type = JointType.PRISMATIC;
    }
    /**
     * The constrained angle between the bodies: body2_angle - body1_angle.
     */
    public float referenceAngle;
    /**
     * Enable/disable the joint limit.
     */
    public boolean enableLimit =true;
    /**
     * The lower translation limit, usually in meters.
     */
    public float lowerTranslation;
    /**
     * The upper translation limit, usually in meters.
     */
    public float upperTranslation;
    /**
     * Enable/disable the joint motor.
     */
    public boolean enableMotor;
    /**
     * The maximum motor torque, usually in N-m.
     */
    public float maxMotorForce = 0.1f;
    /**
     * The desired motor speed in radians per second.
     */
    public float motorSpeed  = 0.1f;
    
    Vec2 anchor;
    Vec2 axis;
}
