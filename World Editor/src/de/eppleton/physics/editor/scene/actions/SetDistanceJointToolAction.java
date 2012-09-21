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
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
    category = "Box2D",
id = "de.eppleton.physics.editor.scene.actions.SetDistanceJointToolAction")
@ActionRegistration(
    iconBase = "de/eppleton/physics/editor/scene/actions/2downarrow.png",
displayName = "#CTL_SetDistanceJointToolAction")
@ActionReferences({
    @ActionReference(path = "Box2DSceneActions", position = -100)
})
@Messages("CTL_SetDistanceJointToolAction=Distance Joint")
public final class SetDistanceJointToolAction implements ActionListener {

    private final WorldEditorScene context;

    public SetDistanceJointToolAction(WorldEditorScene context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        context.setActiveTool(WorldEditorScene.DISTANCE_JOINT_TOOL);
    }
}
