/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.persistence;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import org.jbox2d.dynamics.joints.JointType;

/**
 *
 * @author antonepple
 */
@XmlRootElement
@XmlSeeAlso(value = {PrismaticJoint.class, DistanceJoint.class,
    FrictionJoint.class, GearJoint.class, MouseJoint.class, PulleyJoint.class, 
    RevoluteJoint.class, WeldJoint.class})
public class Joint {

    /**
     * The joint type is set automatically for concrete joint types.
     */
    public JointType type;
    /**
     * Use this to attach application specific data to your joints.
     */
    public Object userData;
    /**
     * The first attached body.
     */
    public Body bodyA;
    /**
     * The second attached body.
     */
    public Body bodyB;
    /**
     * Set this flag to true if the attached bodies should collide.
     */
    public boolean collideConnected;
}
