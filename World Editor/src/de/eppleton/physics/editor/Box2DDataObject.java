/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor;

import com.google.protobuf.TextFormat;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.box2d.proto.Box2D;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.serialization.pb.PbDeserializer;
import org.jbox2d.serialization.pb.PbSerializer;
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
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.text.DataEditorSupport;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;
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
    
    private DocumentListenerImpl documentListenerImpl;
    private Document document;
    private final Updater updater;
    
    public Box2DDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        registerEditor("text/x-box2d", true);
        updater = new Updater(this);
    }
    
    @Override
    protected Node createNodeDelegate() {
        super.createNodeDelegate();
        return new DataNode(this, Children.create(new Box2DChildfactory(this), true));
    }
    
    @Override
    protected int associateLookup() {
        return 1;
    }
    
    public void setDocument(Document d) {
        
        if (document == null) {
            documentListenerImpl = new DocumentListenerImpl();
            d.addDocumentListener(documentListenerImpl);
        }
        document = d;
        updateFromDocument();
        
    }
    
    public void updateDocument() {
        document.removeDocumentListener(documentListenerImpl);
        try {
            document.remove(document.getStartPosition().getOffset(), document.getLength());
            document.insertString(0, serializeWorld(getLookup().lookup(World.class)), null);
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
        document.addDocumentListener(documentListenerImpl);
        
    }
    
    private String serializeWorld(World world) {
        PbSerializer s = new PbSerializer();
        Box2D.PbWorld.Builder serializeWorld = s.serializeWorld(world);
        return serializeWorld.build().toString();
    }
    
    private World parseWorld(String worldDescription) {
        World deserializedWorld = null;
        try {
            final Box2D.PbWorld.Builder builder = Box2D.PbWorld.newBuilder();
            TextFormat.merge(worldDescription, builder);
            Box2D.PbWorld pbWorld = builder.build();
            PbDeserializer d = new PbDeserializer();
            deserializedWorld = d.deserializeWorld(pbWorld);
        } catch (Exception ex) {
            //Exceptions.printStackTrace(ex);
        }
        return deserializedWorld;
    }
    
    private void updateFromDocument() {
        try {
            World world = parseWorld(document.getText(document.getStartPosition().getOffset(), document.getEndPosition().getOffset()));
            if (world != null) {
                getCookieSet().assign(World.class, world);
            }
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
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
        return new MultiViewEditorElement(lkp) {
            @Override
            public void componentActivated() {
                super.componentActivated();
                DataEditorSupport cookie = getLookup().lookup(DataEditorSupport.class);
                Document d = cookie.getOpenedPanes()[0].getDocument();
                getLookup().lookup(Box2DDataObject.class).setDocument(d);
            }
        };
    }
    
    private static class Box2DChildfactory extends ChildFactory<Body> implements LookupListener {
        
        private final Result<World> lookupResult;
        Box2DDataObject b2D;
        
        public Box2DChildfactory(Box2DDataObject aThis) {
            this.b2D = aThis;
            lookupResult = b2D.getLookup().lookupResult(World.class);
            lookupResult.addLookupListener(this);
        }
        
        @Override
        protected boolean createKeys(List<Body> toPopulate) {
            World world = b2D.getLookup().lookup(World.class);
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
            try {
                return new BeanNode(key);
            } catch (IntrospectionException ex) {
                Exceptions.printStackTrace(ex);
            }
            return null;
        }
        
        @Override
        public void resultChanged(LookupEvent ev) {
            refresh(true);
        }
    }
    
    private class DocumentListenerImpl implements DocumentListener {
        
        public DocumentListenerImpl() {
        }
        
        @Override
        public void insertUpdate(DocumentEvent de) {
            updater.documentChange();
        }
        
        @Override
        public void removeUpdate(DocumentEvent de) {
            updater.documentChange();
        }
        
        @Override
        public void changedUpdate(DocumentEvent de) {
            updater.documentChange();
        }
    }
    
    private static class Updater implements Runnable {
        
        private static final RequestProcessor RP = new RequestProcessor(Updater.class);
        private final RequestProcessor.Task UPDATE = RP.create(this);
        private final Box2DDataObject bddo;
        
        public Updater(Box2DDataObject bddo) {
            this.bddo = bddo;
        }
        
        public void documentChange() {
            UPDATE.schedule(1000);
        }
        
        public void run() {
            bddo.updateFromDocument();
        }
    }
}
