/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor;

import de.eppleton.jbox2d.persistence.PersistenceUtil;
import de.eppleton.physics.editor.Box2DDataObject.ViewSynchronizer;
import de.eppleton.physics.editor.assistant.AssistantModel;
import de.eppleton.physics.editor.assistant.AssistantView;
import de.eppleton.physics.editor.assistant.ModelHelper;
import de.eppleton.physics.editor.palette.Box2DPaletteController;
import de.eppleton.physics.editor.scene.WorldEditorScene;
import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.jbox2d.dynamics.World;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.netbeans.spi.palette.PaletteController;
import org.openide.awt.UndoRedo;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.text.DataEditorSupport;
import org.openide.text.NbDocument;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;

@MultiViewElement.Registration(
    displayName = "#LBL_Box2D_VISUAL",
iconBase = "de/eppleton/jbox2d/persistence/box2d_logo.png",
mimeType = "text/box2d+xml",
persistenceType = TopComponent.PERSISTENCE_NEVER,
preferredID = "Box2DVisual",
position = 2000)
@NbBundle.Messages("LBL_Box2D_VISUAL=Visual")
public class Box2DWorldEditor extends javax.swing.JPanel implements MultiViewElement {

    private static Logger LOGGER = Logger.getLogger(Box2DWorldEditor.class.getName());
    private transient de.eppleton.physics.editor.Box2DDataObject obj;
    private transient JToolBar toolbar = new JToolBar();
    private transient MultiViewElementCallback callback;
    private transient final JScrollPane jScrollPane = new JScrollPane();
    private transient WorldEditorScene scene;
    private transient final String name;
    private transient PaletteController paletteController;
    private transient ExplorerManager em = new ExplorerManager();
    private final AssistantModel localmodel;
    private World world;
    private TextDocumentListener textDocumentListener;
    private StyledDocument document;
    private ViewSynchronizer synchronizer;
    private DataEditorSupport des;

    /**
     * Creates new form Box2DWorldEditor
     */
    public Box2DWorldEditor(Lookup lkp) {
        obj = lkp.lookup(de.eppleton.physics.editor.Box2DDataObject.class);
        assert obj != null;
        name = obj.getPrimaryFile().getName();
        paletteController = Box2DPaletteController.createPalette();
        setLayout(new BorderLayout());
        add(jScrollPane, BorderLayout.CENTER);
        localmodel = ModelHelper.returnAssistantModel();
        localmodel.setContext("started");
        add(new AssistantView(localmodel), BorderLayout.NORTH);
        synchronizer = lkp.lookup(ViewSynchronizer.class);
        try {
            JAXBContext context = JAXBContext.newInstance(de.eppleton.jbox2d.persistence.World.class);
            Unmarshaller um = context.createUnmarshaller();
            de.eppleton.jbox2d.persistence.World jaxbWorld = (de.eppleton.jbox2d.persistence.World) um.unmarshal(obj.getPrimaryFile().getInputStream());
            world = PersistenceUtil.getWorldFromJAXBWorld(jaxbWorld);
            if (world != null) {
                synchronizer.setWorld(world);

            }
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        } catch (JAXBException ex) {
            Exceptions.printStackTrace(ex);
        }
        scene = new WorldEditorScene(em,world);
        scene.addPropertyChangeListener(new WorldPropertyListener());
        jScrollPane.setViewportView(scene.createView());
        des = lkp.lookup(DataEditorSupport.class);
        document = des.getDocument();
        textDocumentListener = new TextDocumentListener();
        try {
            des.openDocument().addDocumentListener(textDocumentListener);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public String getName() {
        return "Box2DVisualElement";
    }

    @Override
    public JComponent getVisualRepresentation() {
        return this;
    }

    @Override
    public JComponent getToolbarRepresentation() {
        toolbar.removeAll();
        toolbar.add(scene.getZoomComboBox());
        Lookup actionLookup = Lookups.forPath("Box2DSceneActions");
        Collection<? extends Action> actions = actionLookup.lookupAll(Action.class);
        for (Action action : actions) {
            toolbar.add(action);
        }
        return toolbar;
    }

    public Action[] getActions() {
        Action[] retValue;
        // the multiviewObserver was passed to the element in setMultiViewCallback() method.
        if (callback != null) {
            retValue = callback.createDefaultActions();


        } else {
            // fallback..
            retValue = new Action[0];
        }
        return retValue;
    }

    @Override
    public Lookup getLookup() {
        return new ProxyLookup(Lookups.fixed(paletteController, scene), ExplorerUtils.createLookup(em, getActionMap()));
    }

    @Override
    public void componentOpened() {
    }

    @Override
    public void componentClosed() {
    }

    @Override
    public void componentHidden() {
    }

    @Override
    public void componentActivated() {
    }

    @Override
    public void componentDeactivated() {
    }

    @Override
    public UndoRedo getUndoRedo() {
        return UndoRedo.NONE;
    }

    @Override
    public void setMultiViewCallback(MultiViewElementCallback callback) {
        this.callback = callback;
        callback.updateTitle(name);
    }

    @Override
    public CloseOperationState canCloseElement() {
        return CloseOperationState.STATE_OK;
    }

    @Override
    public void componentShowing() {
    }

    private void refresh() {
        StringReader reader = null;
        try {
            JAXBContext context = JAXBContext.newInstance(de.eppleton.jbox2d.persistence.World.class);
            Unmarshaller um = context.createUnmarshaller();
            reader = new StringReader(document.getText(0, document.getLength()));
            de.eppleton.jbox2d.persistence.World jaxbWorld = (de.eppleton.jbox2d.persistence.World) um.unmarshal(reader);
            world = PersistenceUtil.getWorldFromJAXBWorld(jaxbWorld);
            if (world != null) {
                synchronizer.setWorld(world);
            }
           scene.setWorld(world);
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        } catch (JAXBException ex) {
            Exceptions.printStackTrace(ex);
        } 

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
      private class WorldPropertyListener implements PropertyChangeListener{

        @Override
        public void propertyChange(PropertyChangeEvent pce) {
              try {
                  document = des.openDocument();
              } catch (IOException ex) {
                  Exceptions.printStackTrace(ex);
              }
              
            if (document == null)
               throw new IllegalStateException("Document not exists");
            try {
                DocChanger ch = new DocChanger(document);
                document.removeDocumentListener(textDocumentListener);
                NbDocument.runAtomic(document, ch);
            } finally {
                document.addDocumentListener(textDocumentListener);
            }
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
