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
public class GearJoint extends Joint{

    public GearJoint() {
        type = JointType.GEAR;
    }
    
    
    
    /**
	 * The first revolute/prismatic joint attached to the gear joint.
	 */
	public Joint joint1;

	/**
	 * The second revolute/prismatic joint attached to the gear joint.
	 */
	public Joint joint2;

	/**
	 * Gear ratio.
	 * @see GearJoint
	 */
	public float ratio;
}
