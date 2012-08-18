/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.palette.items;

import javax.swing.text.JTextComponent;
import org.openide.text.ActiveEditorDrop;

/**
 *
 * @author antonepple
 */
public class TestItem implements B2DActiveEditorDrop {

    public TestItem() {
    }

    @Override
    public boolean handleTransfer(JTextComponent targetComponent) {
        return false;
    }
}
