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
public class RevoluteJoint extends Joint{

    public RevoluteJoint() {
        type = JointType.REVOLUTE;
    }
    
        
    
	/**
	 *  The local anchor point relative to body1's origin.
	 */
	public Vec2 localAnchorA;

	/**
	 *  The local anchor point relative to body2's origin.
	 */
	public Vec2 localAnchorB;

	/**
	 *  The body2 angle minus body1 angle in the reference state (radians).
	 */
	public float referenceAngle;

	/**
	 *  A flag to enable joint limits.
	 */
	public boolean enableLimit;

	/**
	 *  The lower angle for the joint limit (radians).
	 */
	public float lowerAngle;

	/**
	 *  The upper angle for the joint limit (radians).
	 */
	public float upperAngle;

	/**
	 *  A flag to enable the joint motor.
	 */
	public boolean enableMotor;

	/**
	 *  The desired motor speed. Usually in radians per second.
	 */
	public float motorSpeed;

	/**
	 *  The maximum motor torque used to achieve the desired motor speed.
	 *  Usually in N-m.
	 */
	public float maxMotorTorque;
	
}
