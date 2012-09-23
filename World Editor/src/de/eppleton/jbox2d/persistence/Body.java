/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.persistence;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

/**
 *
 * @author antonepple
 */
@XmlRootElement
public class Body {

    public ArrayList<Fixture> fixtureList = new ArrayList<Fixture>();
    public BodyType type = BodyType.DYNAMIC;
    public Object userData;
    public Vec2 position;
    public float angle;
    public Vec2 linearVelocity;
    public float angularVelocity;
    public float linearDamping;
    public float angularDamping;
    public boolean allowSleep;
    public boolean awake;
    public boolean fixedRotation;
    public boolean bullet;
    public boolean active;
    public float gravityScale;

   
}
