/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.persistence;

import javax.xml.bind.annotation.XmlRootElement;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;

/**
 *
 * @author antonepple
 */
@XmlRootElement
public class CircleShape extends Shape {

    public CircleShape() {
        this.type = ShapeType.CIRCLE;
    }
   
}
