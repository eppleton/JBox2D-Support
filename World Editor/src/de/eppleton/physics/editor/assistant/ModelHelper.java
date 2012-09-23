/*
 * Model.java
 *
 * Created on September 6, 2006, 11:30 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package de.eppleton.physics.editor.assistant;

/**
 *
 * @author Geertjan Wielenga
 */
public class ModelHelper {
    
    public static AssistantModel model = new AssistantModel();
    
    /** Creates a new instance of Model */
    public ModelHelper() {
    }
    
    /** Returns a model */
    public static AssistantModel returnAssistantModel() {
        return model;
    }
    
}
