/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.palette.items;

import java.util.HashMap;
import javax.swing.text.JTextComponent;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;
import org.openide.text.ActiveEditorDrop;

/**
 *
 * @author antonepple
 */
public abstract class B2DActiveEditorDrop implements ActiveEditorDrop{ 
    public enum DropType{
        WORLD,JOINT,BODY,SHAPE,FIXTURE
    }
    
    @Override
    public boolean handleTransfer(JTextComponent targetComponent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public abstract void addBodies(World world,HashMap<Integer, Joint> jointMap,HashMap<Integer, Body> bodyMap);

}
