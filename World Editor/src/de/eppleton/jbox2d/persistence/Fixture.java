/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.persistence;

import javax.xml.bind.annotation.XmlRootElement;
import org.jbox2d.dynamics.Filter;

@XmlRootElement
public class Fixture {

    /**
     * The shape, this must be set. The shape will be cloned, so you can create
     * the shape on the stack.
     */
    public Shape shape = null;
    /**
     * Use this to store application specific fixture data.
     */
    public Object userData;
    /**
     * The friction coefficient, usually in the range [0,1].
     */
    public float friction;
    /**
     * The restitution (elasticity) usually in the range [0,1].
     */
    public float restitution;
    /**
     * The density, usually in kg/m^2
     */
    public float density;
    /**
     * A sensor shape collects contact information but never generates a
     * collision response.
     */
    public boolean sensor;
    /**
     * Contact filtering data;
     */
    public Filter filter;

 
}
