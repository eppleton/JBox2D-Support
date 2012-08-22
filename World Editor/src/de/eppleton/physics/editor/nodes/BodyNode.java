/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.nodes;

import java.lang.reflect.InvocationTargetException;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.nodes.Sheet.Set;
import org.openide.util.Exceptions;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author antonepple
 */
public class BodyNode extends AbstractNode {

    public BodyNode(Body body) {
        super(Children.LEAF, Lookups.fixed(body));
        updateName();
    }

    private void updateName() {
        Shape shape = getLookup().lookup(Body.class).getFixtureList().getShape();
        String name = getLookup().lookup(Body.class).getType().name();
        if (shape != null) {
            name += " " + shape.getType().name();
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
        Set shapeProperties = null;
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
        properties.put(new PropertySupport.ReadWrite<Float>("angle", Float.class, "angle", "angle") {
            @Override
            public Float getValue() throws IllegalAccessException, InvocationTargetException {
                return getLookup().lookup(Body.class).m_sweep.a;
            }

            @Override
            public void setValue(Float val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                getLookup().lookup(Body.class).m_sweep.a = val;
            }
        });
        properties.put(
                new PropertySupport.ReadWrite<BodyType>("type", BodyType.class, "type", "type") {
                    @Override
                    public BodyType getValue() throws IllegalAccessException, InvocationTargetException {
                        return getLookup().lookup(Body.class).getType();
                    }

                    @Override
                    public void setValue(BodyType val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        getLookup().lookup(Body.class).setType(val);
                        updateName();
                        fireNameChange("", getName());
                    }
                });
//                    properties.put(new PropertySupport.Reflection(getLookup().lookup(Body.class), BodyType.class, "type"));



//        properties.put(new PropertySupport.ReadWrite<Float>("friction", Float.class, "friction", "friction") {
//            @Override
//            public Float getValue() throws IllegalAccessException, InvocationTargetException {
//                return getLookup().lookup(Body.class).getFixtureList().getFriction();
//            }
//
//            @Override
//            public void setValue(Float val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//                getLookup().lookup(Body.class).getFixtureList().setFriction(val);
//            }
//        });

        try {
            properties.put(new PropertySupport.Reflection(getLookup().lookup(Body.class), boolean.class, "isActive", "setActive"));
            properties.put(new PropertySupport.Reflection(getLookup().lookup(Body.class), float.class, "AngularDamping"));

            properties.put(new PropertySupport.Reflection(getLookup().lookup(Body.class), float.class, "AngularDamping"));
            properties.put(new PropertySupport.Reflection(getLookup().lookup(Body.class), float.class, "AngularVelocity"));
            properties.put(new PropertySupport.Reflection(getLookup().lookup(Body.class), boolean.class, "isAwake", "setAwake"));
            properties.put(new PropertySupport.Reflection(getLookup().lookup(Body.class), boolean.class, "isBullet", "setBullet"));
            properties.put(new PropertySupport.Reflection(getLookup().lookup(Body.class), boolean.class, "isFixedRotation", "setFixedRotation"));
            properties.put(new PropertySupport.Reflection(getLookup().lookup(Body.class), boolean.class, "isSleepingAllowed", "setSleepingAllowed"));
            properties.put(new PropertySupport.Reflection(getLookup().lookup(Body.class).getFixtureList(), float.class, "friction"));

            properties.put(new PropertySupport.Reflection(getLookup().lookup(Body.class), float.class, "LinearDamping"));
            properties.put(new PropertySupport.Reflection(getLookup().lookup(Body.class).getFixtureList(), float.class, "restitution"));
            properties.put(new PropertySupport.Reflection(getLookup().lookup(Body.class).getFixtureList(), boolean.class, "isSensor", "setSensor"));
            Shape shape = getLookup().lookup(Body.class).getFixtureList().getShape();
            if (shape != null) {
                shapeProperties = sheet.createPropertiesSet();
                if (shape instanceof CircleShape) {
                    shapeProperties.setName("Circle");
                    shapeProperties.setDisplayName("Circle");
                    shapeProperties.setShortDescription("Circle specific Properties");
                    final CircleShape circleShape = (CircleShape) shape;
                    shapeProperties.put(
                            new PropertySupport.ReadWrite<Float>("radius", Float.class, "radius", "radius") {
                                @Override
                                public Float getValue() throws IllegalAccessException, InvocationTargetException {
                                    return circleShape.m_radius;
                                }

                                @Override
                                public void setValue(Float val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                                    circleShape.m_radius = val;
                                }
                            });
                } else if (shape instanceof PolygonShape) {
                    shapeProperties.setName("Polygon");
                    shapeProperties.setDisplayName("Polygon");
                    shapeProperties.setShortDescription("Polygon specific Properties");
                    final PolygonShape polygonShape = (PolygonShape) shape;
                    shapeProperties.put(
                            new PropertySupport.ReadWrite<Vec2[]>("points", Vec2[].class, "points", "points") {
                                @Override
                                public Vec2[] getValue() throws IllegalAccessException, InvocationTargetException {
                                    return polygonShape.getVertices();
                                }

                                @Override
                                public void setValue(Vec2[] val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                                    polygonShape.set(val, val.length);
                                }
                            });
                }
            }


        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }
        sheet.put(properties);
        sheet.put(shapeProperties);

        return sheet;
    }
}
