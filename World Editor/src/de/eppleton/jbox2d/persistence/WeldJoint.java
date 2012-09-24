/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.persistence;

import javax.xml.bind.annotation.XmlRootElement;
import org.jbox2d.common.Vec2;

/**
 *
 * @author antonepple
 */
@XmlRootElement
public class WeldJoint extends Joint{
    /**
	 * The body2 angle minus body1 angle in the reference state (radians).
	 */
	public float referenceAngle;
	
	/**
	 * The mass-spring-damper frequency in Hertz. Rotation only.
	 * Disable softness with a value of 0.
	 */
	public float frequencyHz;
	
	/**
	 * The damping ratio. 0 = no damping, 1 = critical damping.
	 */
	public float dampingRatio;
   
        public Vec2 anchor;
}
