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
id = "de.eppleton.physics.editor.scene.SetCreateShapeToolAction")
@ActionRegistration(
    iconBase = "de/eppleton/physics/editor/scene/kthememgr.png",
displayName = "#CTL_SetCreateShapeToolAction")
@ActionReference(path = "Box2DSceneActions", position = 0)
@Messages("CTL_SetCreateShapeToolAction=Create Shape")
public final class SetCreateShapeToolAction implements ActionListener {

    private final WorldEditorScene context;

    public SetCreateShapeToolAction(WorldEditorScene context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        context.setActiveTool(WorldEditorScene.CREATE_SHAPE_TOOL);
    }
}
