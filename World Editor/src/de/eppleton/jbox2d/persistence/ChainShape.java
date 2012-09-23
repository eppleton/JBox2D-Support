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
public class ChainShape extends Shape {

    public ChainShape() {
        this.type = ShapeType.CHAIN;
    }
    public Vec2[] m_vertices;
    public int m_count;
    public final Vec2 m_prevVertex = new Vec2(), m_nextVertex = new Vec2();
    public boolean m_hasPrevVertex = false, m_hasNextVertex = false;
}
