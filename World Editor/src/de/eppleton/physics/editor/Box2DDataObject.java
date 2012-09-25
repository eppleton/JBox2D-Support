/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor;

import de.eppleton.jbox2d.persistence.PersistenceUtil;
import de.eppleton.jbox2d.persistence.World;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.netbeans.api.xml.cookies.CheckXMLCookie;
import org.netbeans.api.xml.cookies.ValidateXMLCookie;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.netbeans.spi.xml.cookies.CheckXMLSupport;
import org.netbeans.spi.xml.cookies.DataObjectAdapters;
import org.netbeans.spi.xml.cookies.ValidateXMLSupport;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.text.DataEditorSupport;
import org.openide.text.NbDocument;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.xml.sax.InputSource;

@Messages({
    "LBL_Box2D_LOADER=Box2D XML Files"
})
@MIMEResolver.ExtensionRegistration(
    displayName = "#LBL_Box2D_LOADER",
mimeType = "text/box2d+xml",
extension = {"b2x"})
@DataObject.Registration(
    mimeType = "text/box2d+xml",
iconBase = "de/eppleton/jbox2d/persistence/box2d_logo.png",
displayName = "#LBL_Box2D_LOADER",
position = 300)
@ActionReferences({
    @ActionReference(
        path = "Loaders/text/box2d+xml/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
    position = 100,
    separatorAfter = 200),
    @ActionReference(
        path = "Loaders/text/box2d+xml/Actions",
    id =
    @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
    position = 300),
    @ActionReference(
        path = "Loaders/text/box2d+xml/Actions",
    id =
    @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
    position = 400,
    separatorAfter = 500),
    @ActionReference(
        path = "Loaders/text/box2d+xml/Actions",
    id =
    @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
    position = 600),
    @ActionReference(
        path = "Loaders/text/box2d+xml/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
    position = 700,
    separatorAfter = 800),
    @ActionReference(
        path = "Loaders/text/box2d+xml/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
    position = 900,
    separatorAfter = 1000),
    @ActionReference(
        path = "Loaders/text/box2d+xml/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
    position = 1100,
    separatorAfter = 1200),
    @ActionReference(
        path = "Loaders/text/box2d+xml/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
    position = 1300),
    @ActionReference(
        path = "Loaders/text/box2d+xml/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
    position = 1400)
})
public class Box2DDataObject extends MultiDataObject {

    de.eppleton.physics.editor.Box2DDataObject.ViewSynchronizer synchronizer;
    org.jbox2d.dynamics.World world;

    public Box2DDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        registerEditor("text/box2d+xml", true);
        
        InputSource inputSource = DataObjectAdapters.inputSource(this);
        CheckXMLCookie checkCookie = new CheckXMLSupport(inputSource);
        getCookieSet().add(checkCookie);
        ValidateXMLCookie validateXMLCookie = new ValidateXMLSupport(inputSource);
        getCookieSet().add(validateXMLCookie);
       
        synchronizer = new ViewSynchronizer();
        getCookieSet().assign(de.eppleton.physics.editor.Box2DDataObject.ViewSynchronizer.class, synchronizer);
    }

    @Override
    protected int associateLookup() {
        return 1;
    }

    @MultiViewElement.Registration(
        displayName = "#LBL_Box2D_EDITOR",
    iconBase = "de/eppleton/jbox2d/persistence/box2d_logo.png",
    mimeType = "text/box2d+xml",
    persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
    preferredID = "Box2D",
    position = 1000)
    @Messages("LBL_Box2D_EDITOR=Source")
    public static MultiViewEditorElement createEditor(Lookup lkp) {
        return new MultiViewEditorElement(lkp);
    }

    public static class ViewSynchronizer {

        private static Logger LOGGER = Logger.getLogger(de.eppleton.physics.editor.Box2DDataObject.ViewSynchronizer.class.getName());
        private org.jbox2d.dynamics.World oldWorld;
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

        public void updateFromVisualEditor() {
        }

        public void setWorld(org.jbox2d.dynamics.World newWorld) {
            // assert newWorld != null; // this clashes with template system
            if (newWorld == null) {
                return;
            }
            LOGGER.info("Updating World");
            oldWorld = newWorld;
            PropertyChangeListener[] propertyChangeListeners = p.getPropertyChangeListeners();
            for (PropertyChangeListener propertyChangeListener : propertyChangeListeners) {
                LOGGER.info("Currently registered Listener " + propertyChangeListener);
            }
            p.firePropertyChange(WORLD_CHANGED, null, newWorld);
        }

        public org.jbox2d.dynamics.World getWorld() {
            return oldWorld;
        }
    }

    

   
}
