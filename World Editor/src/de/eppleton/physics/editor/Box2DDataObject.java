/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor;

import de.eppleton.jbox2d.WorldUtilities;
import de.eppleton.physics.editor.nodes.BodyNode;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

@Messages({
    "LBL_Box2D_LOADER=Files of Box2D"
})
@MIMEResolver.ExtensionRegistration(
    displayName = "#LBL_Box2D_LOADER",
mimeType = "text/x-box2d",
extension = {"box2d", "b2d"})
@DataObject.Registration(
    mimeType = "text/x-box2d",
iconBase = "de/eppleton/physics/editor/tar.png",
displayName = "#LBL_Box2D_LOADER",
position = 300)
@ActionReferences({
    @ActionReference(
        path = "Loaders/text/x-box2d/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
    position = 100,
    separatorAfter = 200),
    @ActionReference(
        path = "Loaders/text/x-box2d/Actions",
    id =
    @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
    position = 300),
    @ActionReference(
        path = "Loaders/text/x-box2d/Actions",
    id =
    @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
    position = 400,
    separatorAfter = 500),
    @ActionReference(
        path = "Loaders/text/x-box2d/Actions",
    id =
    @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
    position = 600),
    @ActionReference(
        path = "Loaders/text/x-box2d/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
    position = 700,
    separatorAfter = 800),
    @ActionReference(
        path = "Loaders/text/x-box2d/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
    position = 900,
    separatorAfter = 1000),
    @ActionReference(
        path = "Loaders/text/x-box2d/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
    position = 1100,
    separatorAfter = 1200),
    @ActionReference(
        path = "Loaders/text/x-box2d/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
    position = 1300),
    @ActionReference(
        path = "Loaders/text/x-box2d/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
    position = 1400)
})
public class Box2DDataObject extends MultiDataObject {

    ViewSynchronizer synchronizer;

    public Box2DDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        registerEditor("text/x-box2d", true);
        synchronizer = new ViewSynchronizer();
        getCookieSet().assign(ViewSynchronizer.class, synchronizer);
        // start by parsing the world directly from the file
        World parsedWorld = WorldUtilities.parseWorld(pf.asText());
        synchronizer.setWorld(parsedWorld);
    }

    //TODO better move nodes to Visual Editor...
    @Override
    protected Node createNodeDelegate() {
        super.createNodeDelegate();
        return new DataNode(this, Children.create(new Box2DChildfactory(this), true));
    }

    @Override
    protected int associateLookup() {
        return 1;
    }

    @MultiViewElement.Registration(
        displayName = "#LBL_Box2D_EDITOR",
    iconBase = "de/eppleton/physics/editor/tar.png",
    mimeType = "text/x-box2d",
    persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
    preferredID = "Box2D",
    position = 1000)
    @Messages("LBL_Box2D_EDITOR=Source")
    public static MultiViewEditorElement createEditor(Lookup lkp) {
        return new Box2DEditor(lkp);
    }

    private static class Box2DChildfactory extends ChildFactory<Body> implements PropertyChangeListener {

        Box2DDataObject b2D;
        private ViewSynchronizer synchronizer;

        public Box2DChildfactory(Box2DDataObject aThis) {
            this.b2D = aThis;
            synchronizer = b2D.getLookup().lookup(ViewSynchronizer.class);
            synchronizer.addPropertyChangeListener(this);
        }

        @Override
        protected boolean createKeys(List<Body> toPopulate) {
            World world = synchronizer.getWorld();
            if (world == null) {
                return true;
            }

            Body nextBody = world.getBodyList();
            while (nextBody != null) {
                toPopulate.add(nextBody);
                nextBody = nextBody.getNext();
            }
            return true;
        }

        @Override
        protected Node createNodeForKey(Body key) {
            return new BodyNode(key);
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            refresh(true);
        }
    }

    public static class ViewSynchronizer {

        private static Logger LOGGER = Logger.getLogger(ViewSynchronizer.class.getName());
        private World oldWorld;
        PropertyChangeSupport p = new PropertyChangeSupport(this);
        public static String WORLD_CHANGED = "world changed";

        public void addPropertyChangeListener(PropertyChangeListener l) {
            LOGGER.info("added Listener " + l);
            p.addPropertyChangeListener(WORLD_CHANGED, l);
        }

        public void removePropertyChangelistener(PropertyChangeListener l) {
            LOGGER.info("removed Listener " + l);
            p.removePropertyChangeListener(WORLD_CHANGED, l);
        }

        public void setWorld(World newWorld) {
            assert newWorld != null;
            LOGGER.info("Updating World");
            oldWorld = newWorld;
            PropertyChangeListener[] propertyChangeListeners = p.getPropertyChangeListeners();
            for (PropertyChangeListener propertyChangeListener : propertyChangeListeners) {
                LOGGER.info("Currently registered Listener " + propertyChangeListener);
            }
            p.firePropertyChange(WORLD_CHANGED, null, newWorld);
        }

        public World getWorld() {
            return oldWorld;
        }
    }
}
