/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.palette.items;

import de.eppleton.jbox2d.CircleShapeBuilder;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

/**
 *
 * @author antonepple
 */
public class TestItem extends B2DActiveEditorDrop {

    public TestItem() {
    }

    @Override
    public Body[] createBodies(World world) {
        return new Body[]{new CircleShapeBuilder(world).radius(2.0F).build()};
    } 
}
