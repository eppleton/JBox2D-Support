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
public class MouseJoint extends Joint{
    public Vec2 target;

    public MouseJoint() {
        type = JointType.MOUSE;
    }

    
    
    /**
     * The maximum constraint force that can be exerted to move the candidate
     * body. Usually you will express as some multiple of the weight (multiplier
     * * mass * gravity).
     */
    public float maxForce;
    /**
     * The response speed.
     */
    public float frequencyHz;
    /**
     * The damping ratio. 0 = no damping, 1 = critical damping.
     */
    public float dampingRatio;
}
