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
    category = "Build",
id = "de.eppleton.physics.editor.scene.SetSelectToolAction")
@ActionRegistration(
    iconBase = "de/eppleton/physics/editor/scene/edit.png",
displayName = "#CTL_SetSelectToolAction")
@ActionReference(path = "Box2DSceneActions", position = 0)
@Messages("CTL_SetSelectToolAction=Create Shape")
public final class SetSelectToolAction implements ActionListener {

    private final WorldEditorScene context;

    public SetSelectToolAction(WorldEditorScene context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        context.setActiveTool(WorldEditorScene.SELECT_TOOL);
    }
}
