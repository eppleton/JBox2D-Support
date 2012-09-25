/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor;

import de.eppleton.jbox2d.persistence.PersistenceUtil;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.jbox2d.dynamics.World;
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
import org.openide.util.RequestProcessor;
import org.openide.windows.TopComponent;
import org.xml.sax.InputSource;

@Messages({
    "LBL_Box2D_LOADER=Box2D XML Files"
})
@MIMEResolver.ExtensionRegistration(
    displayName = "#LBL_Box2D_LOADER",
mimeType = "text/x-box2d+xml",
extension = {"b2x"})
@DataObject.Registration(
    mimeType = "text/x-box2d+xml",
iconBase = "de/eppleton/jbox2d/persistence/box2d_logo.png",
displayName = "#LBL_Box2D_LOADER",
position = 300)
@ActionReferences({
    @ActionReference(
        path = "Loaders/text/x-box2d+xml/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
    position = 100,
    separatorAfter = 200),
    @ActionReference(
        path = "Loaders/text/x-box2d+xml/Actions",
    id =
    @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
    position = 300),
    @ActionReference(
        path = "Loaders/text/x-box2d+xml/Actions",
    id =
    @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
    position = 400,
    separatorAfter = 500),
    @ActionReference(
        path = "Loaders/text/x-box2d+xml/Actions",
    id =
    @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
    position = 600),
    @ActionReference(
        path = "Loaders/text/x-box2d+xml/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
    position = 700,
    separatorAfter = 800),
    @ActionReference(
        path = "Loaders/text/x-box2d+xml/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
    position = 900,
    separatorAfter = 1000),
    @ActionReference(
        path = "Loaders/text/x-box2d+xml/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
    position = 1100,
    separatorAfter = 1200),
    @ActionReference(
        path = "Loaders/text/x-box2d+xml/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
    position = 1300),
    @ActionReference(
        path = "Loaders/text/x-box2d+xml/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
    position = 1400)
})
public class Box2DDataObject extends MultiDataObject implements PropertyChangeListener {

    org.jbox2d.dynamics.World world;
    private final Updater updater;
    private TextDocumentListener textDocumentListener;
    private StyledDocument document;
    private DataEditorSupport des;

    public Box2DDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        registerEditor("text/x-box2d+xml", true);
        InputSource inputSource = DataObjectAdapters.inputSource(this);
        CheckXMLCookie checkCookie = new CheckXMLSupport(inputSource);
        getCookieSet().add(checkCookie);
        ValidateXMLCookie validateXMLCookie = new ValidateXMLSupport(inputSource);
        getCookieSet().add(validateXMLCookie);
        try {
            JAXBContext context = JAXBContext.newInstance(de.eppleton.jbox2d.persistence.World.class);
            Unmarshaller um = context.createUnmarshaller();
            de.eppleton.jbox2d.persistence.World jaxbWorld = (de.eppleton.jbox2d.persistence.World) um.unmarshal(pf.getInputStream());
            world = PersistenceUtil.getWorldFromJAXBWorld(jaxbWorld);
        } catch (JAXBException ex) {
            Exceptions.printStackTrace(ex);
        }
        des = getLookup().lookup(DataEditorSupport.class);
        document = des.getDocument();
        textDocumentListener = new TextDocumentListener();
        try {
            des.openDocument().addDocumentListener(textDocumentListener);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        updater = new Updater(this);
    }

    @Override
    protected int associateLookup() {
        return 1;
    }

    private void refresh() {
        
        StringReader reader = null;
        try {
            document = des.openDocument();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        try {
            JAXBContext context = JAXBContext.newInstance(de.eppleton.jbox2d.persistence.World.class);
            Unmarshaller um = context.createUnmarshaller();
            reader = new StringReader(document.getText(0, document.getLength()));
            de.eppleton.jbox2d.persistence.World jaxbWorld = (de.eppleton.jbox2d.persistence.World) um.unmarshal(reader);
            world = PersistenceUtil.getWorldFromJAXBWorld(jaxbWorld);
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        } catch (JAXBException ex) {
             // we ignore this, it's expected to happen
        }

    }

    @MultiViewElement.Registration(
        displayName = "#LBL_Box2D_EDITOR",
    iconBase = "de/eppleton/jbox2d/persistence/box2d_logo.png",
    mimeType = "text/x-box2d+xml",
    persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
    preferredID = "Box2D",
    position = 1000)
    @Messages("LBL_Box2D_EDITOR=Source")
    public static MultiViewEditorElement createEditor(Lookup lkp) {
        return new MultiViewEditorElement(lkp);
    }

    World getWorld() {
        return world;
    }

    private class TextDocumentListener implements DocumentListener {

        public void insertUpdate(DocumentEvent e) {
            refresh();
        }

        public void removeUpdate(DocumentEvent e) {
            refresh();
        }

        public void changedUpdate(DocumentEvent e) {
            refresh();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        updater.documentChange();
    }

    private static class Updater implements Runnable {

        private static final RequestProcessor RP = new RequestProcessor(Updater.class);
        private final RequestProcessor.Task UPDATE = RP.create(this);
        private final Box2DDataObject box2dDataObject;

        private Updater(Box2DDataObject aThis) {
            this.box2dDataObject = aThis;
        }

        public void documentChange() {
            UPDATE.schedule(500);
        }

        public void run() {
            box2dDataObject.changed();
        }
    }

    private void changed() {
        try {
            document = des.openDocument();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        if (document == null) {
            throw new IllegalStateException("Document not exists");
        }
        try {
            DocChanger ch = new DocChanger(document);    
            document.removeDocumentListener(textDocumentListener);
            NbDocument.runAtomic(document, ch);
        } finally {
            document.addDocumentListener(textDocumentListener);
        }
    }

    private class DocChanger implements Runnable {

        StyledDocument doc;

        public DocChanger(StyledDocument doc) {
            this.doc = doc;
        }

        public void run() {
            try {
                de.eppleton.jbox2d.persistence.World jaxbWorldFromWorld = PersistenceUtil.getJAXBWorldFromWorld(world);
                StringWriter stringWriter = new StringWriter();
                JAXBContext context = JAXBContext.newInstance(de.eppleton.jbox2d.persistence.World.class);
                Marshaller m = context.createMarshaller();
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                m.setProperty("jaxb.schemaLocation", "http://www.eppleton.de/schemas/box2d http://www.eppleton.de/schemas/Box2d.xsd");
                m.marshal(jaxbWorldFromWorld, stringWriter);
                doc.remove(doc.getStartPosition().getOffset(), doc.getLength());
                doc.insertString(doc.getStartPosition().getOffset(), stringWriter.toString(), null);
            } catch (JAXBException ex) {
                Exceptions.printStackTrace(ex);
            } catch (BadLocationException bex) {
                Exceptions.printStackTrace(bex);
            }
        }
    }



}
