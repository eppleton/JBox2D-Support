/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene;

import java.util.Set;
import org.netbeans.api.visual.model.ObjectSceneEvent;
import org.netbeans.api.visual.model.ObjectSceneListener;
import org.netbeans.api.visual.model.ObjectState;

/**
 * Abstract impl eith empty methods to make it easier to implement required methods
 * @author antonepple
 */
public abstract class AbstractObjectSceneListener implements ObjectSceneListener {

    public void objectAdded(ObjectSceneEvent objectSceneEvent, Object object) {
    }

    public void objectRemoved(ObjectSceneEvent objectSceneEvent, Object object) {
    }

    @Override
    public void selectionChanged(ObjectSceneEvent event, Set<Object> previousSelection, Set<Object> newSelection) {        
    }

    public void objectStateChanged(ObjectSceneEvent objectSceneEvent, Object object, ObjectState objectState, ObjectState objectState0) {
    }

    public void highlightingChanged(ObjectSceneEvent objectSceneEvent, Set<Object> oldHighlighte, Set<Object> newHighlighted) {
    }

    public void hoverChanged(ObjectSceneEvent objectSceneEvent, Object object, Object object0) {
    }

    public void focusChanged(ObjectSceneEvent objectSceneEvent, Object object, Object object0) {
    }
}
