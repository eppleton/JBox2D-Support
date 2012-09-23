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
public class PulleyJoint extends Joint{

    public PulleyJoint() {
        this.type = JointType.PULLEY;
    }
    
    
    /**
   * The first ground anchor in world coordinates. This point never moves.
   */
  public Vec2 groundAnchorA;

  /**
   * The second ground anchor in world coordinates. This point never moves.
   */
  public Vec2 groundAnchorB;

  /**
   * The local anchor point relative to bodyA's origin.
   */
  public Vec2 localAnchorA;

  /**
   * The local anchor point relative to bodyB's origin.
   */
  public Vec2 localAnchorB;

  /**
   * The a reference length for the segment attached to bodyA.
   */
  public float lengthA;

  /**
   * The a reference length for the segment attached to bodyB.
   */
  public float lengthB;

  /**
   * The pulley ratio, used to simulate a block-and-tackle.
   */
  public float ratio;
}
