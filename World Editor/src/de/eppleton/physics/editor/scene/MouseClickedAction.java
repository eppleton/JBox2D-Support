/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene;

import java.awt.Component;
import org.netbeans.api.visual.widget.Widget;


/** When the scene is clicked, sets the the focus to scene's component. */ 
public class MouseClickedAction  extends org.netbeans.api.visual.action.WidgetAction.Adapter { 
    private final Component component; 
    
    public MouseClickedAction(Component aComponent) { 
        component = aComponent; 
    } 

    @Override 
    public State mouseClicked(Widget widget, WidgetMouseEvent event) { 
        System.out.println("setting focus to " + component); 
        component.requestFocus(); 
        return State.REJECTED; 
    } 
} 