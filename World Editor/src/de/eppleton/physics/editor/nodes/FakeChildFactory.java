/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.nodes;

import java.util.Collections;
import java.util.List;
import org.jbox2d.dynamics.Body;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 *
 * @author antonepple
 */
public class FakeChildFactory extends ChildFactory<Body> {

    List<Body> keys = Collections.EMPTY_LIST;

    public void setKeys(List<Body> currentKeys) {
        keys = currentKeys;
        refresh(true);
    }

    @Override
    protected boolean createKeys(List<Body> toPopulate) {
        toPopulate.addAll(keys);
        return true;
    }

    @Override
    protected Node createNodeForKey(Body key) {
        System.out.println("### creating Node for "+key);
        return new BodyNode(key);
    }
}
