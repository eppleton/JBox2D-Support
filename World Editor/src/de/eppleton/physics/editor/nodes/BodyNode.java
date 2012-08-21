/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.nodes;

import java.lang.reflect.InvocationTargetException;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.nodes.Sheet.Set;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author antonepple
 */
public class BodyNode extends AbstractNode {

    public BodyNode(Body body) {
        super(Children.LEAF, Lookups.fixed(body));
        Shape shape = body.getFixtureList().getShape();
        String name = body.getType().name();
        if (shape != null) {
            name += " "+shape.getType().name();
        } else {
            name += " Body";
        }
        setName(name);
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = super.createSheet();
        Set properties = sheet.get(Sheet.PROPERTIES);
        if (properties == null) {
            properties = sheet.createPropertiesSet();
        }
        properties.put(new PropertySupport.ReadWrite<Float>("x", Float.class, "x", "X coordinate of position") {
            @Override
            public Float getValue() throws IllegalAccessException, InvocationTargetException {
                return getLookup().lookup(Body.class).getPosition().x;
            }

            @Override
            public void setValue(Float val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                getLookup().lookup(Body.class).getPosition().x = val;
            }
        });
        properties.put(new PropertySupport.ReadWrite<Float>("y", Float.class, "y", "y coordinate of position") {
            @Override
            public Float getValue() throws IllegalAccessException, InvocationTargetException {
                return getLookup().lookup(Body.class).getPosition().y;
            }

            @Override
            public void setValue(Float val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                getLookup().lookup(Body.class).getPosition().y = val;
            }
        });
        sheet.put(properties);

        return sheet;
    }
}
