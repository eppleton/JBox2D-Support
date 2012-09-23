/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.persistence;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import org.jbox2d.collision.shapes.ShapeType;

@XmlRootElement
@XmlSeeAlso(value={ChainShape.class,CircleShape.class,PolygonShape.class, EdgeShape.class})
public abstract class Shape {

    public  ShapeType type;
    public float radius;
}
