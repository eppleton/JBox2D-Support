/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.persistence;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import org.jbox2d.common.Vec2;

/**
 *
 * @author antonepple
 */
@XmlRootElement
public class World {

    public ArrayList<Body> bodyList = new ArrayList<Body>();
    public ArrayList<Joint> jointList = new ArrayList<Joint>();
    public Vec2 gravity = new Vec2(0, -7);
}
