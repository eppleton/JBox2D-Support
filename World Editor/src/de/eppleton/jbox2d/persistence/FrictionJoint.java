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
public class FrictionJoint extends Joint {

    public FrictionJoint() {
        type = JointType.FRICTION;
    }

    
    
    /**
     * The maximum friction force in N.
     */
    public float maxForce;
    /**
     * The maximum friction torque in N-m.
     */
    public float maxTorque;
}
