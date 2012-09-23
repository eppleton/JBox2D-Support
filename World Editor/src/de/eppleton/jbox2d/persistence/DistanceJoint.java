/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.persistence;

import javax.xml.bind.annotation.XmlRootElement;
import org.jbox2d.dynamics.joints.JointType;

@XmlRootElement
public class DistanceJoint extends Joint {

    public DistanceJoint() {
        type = JointType.FRICTION;
    }
  
    /**
     * The equilibrium length between the anchor points.
     */
    public float length;
    /**
     * The mass-spring-damper frequency in Hertz.
     */
    public float frequencyHz;
    /**
     * The damping ratio. 0 = no damping, 1 = critical damping.
     */
    public float dampingRatio;
}
