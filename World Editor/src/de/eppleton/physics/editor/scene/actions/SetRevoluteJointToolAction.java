/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene.actions;

import de.eppleton.physics.editor.scene.WorldEditorScene;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
    category = "Box2D",
id = "de.eppleton.physics.editor.scene.actions.SetRevoluteJointToolAction")
@ActionRegistration(
    iconBase = "de/eppleton/physics/editor/scene/actions/noatunloopsong.png",
displayName = "#CTL_SetRevoluteJointToolAction")
@ActionReference(path = "Box2DSceneActions", position = 0)
@Messages("CTL_SetRevoluteJointToolAction=Revolute Joint")
public final class SetRevoluteJointToolAction implements ActionListener {
    
    private final WorldEditorScene context;
    
    public SetRevoluteJointToolAction(WorldEditorScene context) {
        this.context = context;
    }
    
    @Override
    public void actionPerformed(ActionEvent ev) {
        context.setActiveTool(WorldEditorScene.REVOLUTE_JOINT_TOOL);
    }
}
