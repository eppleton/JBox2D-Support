/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor;

import de.eppleton.physics.editor.Box2DDataObject.ViewSynchronizer;
import de.eppleton.physics.editor.palette.Box2DPaletteController;
import de.eppleton.physics.editor.scene.Callback;
import de.eppleton.physics.editor.scene.WorldScene;
import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import org.jbox2d.dynamics.World;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.netbeans.spi.palette.PaletteController;
import org.openide.awt.UndoRedo;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;

@MultiViewElement.Registration(
    displayName = "#LBL_Box2D_VISUAL",
iconBase = "de/eppleton/physics/editor/tar.png",
mimeType = "text/x-box2d",
persistenceType = TopComponent.PERSISTENCE_NEVER,
preferredID = "Box2DVisual",
position = 2000)
@Messages("LBL_Box2D_VISUAL=Visual")
public final class Box2DVisualElement extends JPanel implements MultiViewElement, PropertyChangeListener, ExplorerManager.Provider {

    private static Logger LOGGER = Logger.getLogger(Box2DVisualElement.class.getName());
    private transient Box2DDataObject obj;
    private transient JToolBar toolbar = new JToolBar();
    private transient MultiViewElementCallback callback;
    private transient final JScrollPane jScrollPane = new JScrollPane();
    private transient WorldScene scene;
    private transient final String name;
    private transient PaletteController paletteController;
    private transient ViewSynchronizer synchronizer;
    private transient VisualUpdater updater;
    private transient World world;
    private transient ExplorerManager em = new ExplorerManager();

    public Box2DVisualElement(Lookup lkp) {
        updater = new VisualUpdater(this);
        obj = lkp.lookup(Box2DDataObject.class);
        assert obj != null;
        name = obj.getPrimaryFile().getName();
        paletteController = Box2DPaletteController.createPalette();
        setLayout(new BorderLayout());
        add(jScrollPane, BorderLayout.CENTER);
        synchronizer = lkp.lookup(Box2DDataObject.ViewSynchronizer.class);
        synchronizer.addPropertyChangeListener(this);
        if (synchronizer.getWorld() != null) {
            update(synchronizer.getWorld());
        }
        initToolBar();
    }

    private void update(final World world) {
        if (world != null) {
            this.world = world;
            if (scene != null && scene.getWorld() == world) {
                return;
            }
            scene = new WorldScene(em, world, new CallbackImpl());
            jScrollPane.setViewportView(scene.createView());
        }
    }

    public void fireChange() {
        synchronizer.removePropertyChangelistener(Box2DVisualElement.this);
        synchronizer.setWorld(world);
        synchronizer.addPropertyChangeListener(Box2DVisualElement.this);
    }

    @Override
    public String getName() {
        return "Box2DVisualElement";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public JComponent getVisualRepresentation() {
        return this;
    }

    @Override
    public JComponent getToolbarRepresentation() {
        return toolbar;
    }

    @Override
    public Action[] getActions() {
        return new Action[0];
    }

    @Override
    public Lookup getLookup() {
        return new ProxyLookup( Lookups.fixed(paletteController), ExplorerUtils.createLookup(em, getActionMap()));
    }

    @Override
    public void componentShowing() {
        scene.updateBodies();
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
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == Box2DDataObject.ViewSynchronizer.WORLD_CHANGED) {
            LOGGER.info(">> Update Visual Editor");
            update((World) evt.getNewValue());
        }
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return em;
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

    private void initToolBar() {
        toolbar.removeAll();
        toolbar.add(scene.getZoomComboBox());
    }

    private static class VisualUpdater implements Runnable {

        private static final RequestProcessor RP = new RequestProcessor(VisualUpdater.class);
        private final RequestProcessor.Task UPDATE = RP.create(this);
        private final Box2DVisualElement editorElementImpl;

        public VisualUpdater(Box2DVisualElement bddo) {
            this.editorElementImpl = bddo;
        }

        public void worldChange() {
            UPDATE.schedule(1000);
        }

        public void run() {
            editorElementImpl.fireChange();
        }
    }

    private class CallbackImpl implements Callback {

        @Override
        public void changed() {
            updater.worldChange();
        }
    }
    
    
}
