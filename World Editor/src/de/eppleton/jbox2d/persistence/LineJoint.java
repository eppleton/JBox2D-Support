/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.persistence;

import javax.xml.bind.annotation.XmlRootElement;
import org.jbox2d.dynamics.joints.JointType;

/**
 *
 * @author antonepple
 */
@XmlRootElement
public class LineJoint extends Joint{

       
    /**
	 * Enable/disable the joint limit.
	 */
	public boolean enableLimit;
	
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
	public float maxMotorForce;
	
	/**
	 * The desired motor speed in radians per second.
	 */
	public float motorSpeed;
}
