/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;

/**
 *
 * @author antonepple
 */
public class BodyShape {
    private Body body;
    private Shape shape;

    public BodyShape(Body body, Shape shape) {
        this.body = body;
        this.shape = shape;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
    
    
}
