/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor;

import de.eppleton.jbox2d.PatchedTestbedController;
import de.eppleton.jbox2d.WorldUtilities;
import de.eppleton.physics.editor.Box2DDataObject.ViewSynchronizer;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import org.jbox2d.dynamics.World;
import org.jbox2d.testbed.framework.TestList;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.TestbedTest;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.openide.awt.UndoRedo;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

/**
 *
 * @author antonepple
 */
@MultiViewElement.Registration(
    displayName = "#LBL_Box2D_Simulator",
iconBase = "de/eppleton/physics/editor/tar.png",
mimeType = "text/x-box2d",
persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
preferredID = "Box2DSimulator",
position = 3000)
@NbBundle.Messages("LBL_Box2D_Simulator=Simulator")
public class Box2DSimulatorElement extends javax.swing.JPanel implements MultiViewElement, PropertyChangeListener {

    private static Logger LOGGER = Logger.getLogger(Box2DSimulatorElement.class.getName());
    private Box2DDataObject obj;
    private JToolBar toolbar = new JToolBar();
    private transient MultiViewElementCallback callback;
    private TestbedModel model;
    private PatchedTestbedController controller;
    private boolean started = false;
    private World world;
    private ViewSynchronizer synchronizer;

    /**
     * Creates new form Box2DSimulatorElement
     */
    public Box2DSimulatorElement(final Lookup lkp) {
        obj = lkp.lookup(Box2DDataObject.class);
        assert obj != null;
        synchronizer = lkp.lookup(Box2DDataObject.ViewSynchronizer.class);
        synchronizer.addPropertyChangeListener(this);
        
    }
    
    private void update(World newWorld) {
        this.world = newWorld;
        removeAll();
        final String name = obj.getName();
        model = new TestbedModel();
        model.addCategory("Bla");
        
        model.addTest(new TestbedTestImpl(newWorld, name));
        TestList.populateModel(model);
        TestPanelJ2D panel = new TestPanelJ2D(model);
        model.setDebugDraw(panel.getDebugDraw());
        controller = new PatchedTestbedController(model, panel);
        //TestbedSidePanel side = new TestbedSidePanel(model, controller);
        setLayout(new BorderLayout());
        initToolBar();
        add((Component) panel, BorderLayout.CENTER);
        //add(toolbar, BorderLayout.NORTH);
        // add(new JScrollPane(side), "East");
        revalidate();
        
    }
    
    public void initToolBar() {
        toolbar.removeAll();
        toolbar.add(new AbstractAction("", ImageUtilities.loadImageIcon("de/eppleton/physics/editor/resources/player_stop.png", true)) {
            @Override
            public void actionPerformed(ActionEvent e) {
                started = false;
                controller.stop();
                update(WorldUtilities.copy(synchronizer.getWorld()));
            }
        });
        toolbar.add(new AbstractAction("", ImageUtilities.loadImageIcon("de/eppleton/physics/editor/resources/player_pause.png", true)) {
            @Override
            public void actionPerformed(ActionEvent e) {
                started = false;
                controller.stop();
            }
        });
        toolbar.add(new AbstractAction("", ImageUtilities.loadImageIcon("de/eppleton/physics/editor/resources/player_play.png", true)) {
            @Override
            public void actionPerformed(ActionEvent e) {
                started = true;
                play();
            }
        });
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
        return obj.getLookup();
    }
    
    @Override
    public void componentOpened() {
    }
    
    @Override
    public void componentClosed() {
    }
    
    @Override
    public void componentShowing() {
    }
    
    @Override
    public void componentHidden() {
        if (controller != null) {
            controller.stop();
        }
    }
    
    @Override
    public void componentActivated() {
        if (synchronizer.getWorld() != null) {
            update(WorldUtilities.copy(synchronizer.getWorld()));
        }
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
    }
    
    @Override
    public CloseOperationState canCloseElement() {
        return CloseOperationState.STATE_OK;
    }
    
    private void play() {
        if (controller != null && !controller.isAnimating()) {
            controller.playTest(0);
            controller.start();
        }
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == ViewSynchronizer.WORLD_CHANGED) {
            World world = (World) evt.getNewValue();
            if (world != null) {
                update(WorldUtilities.copy(world));
            } else {
                LOGGER.warning("new World is null");
            }
        }
    }
    
    private class TestbedTestImpl extends TestbedTest {
        
        private final String name;
        private World world;
        
        public TestbedTestImpl(World world, String name) {
            super();
            this.name = name;
            this.world = world;
        }
        
        @Override
        public String getTestName() {
            return name;
        }

        // this is a hack to set our World as the tests world...
        @Override
        public void init(TestbedModel argModel) {
            super.init(argModel);
            m_world = world;
            init(world, false);
        }
        
        @Override
        public void initTest(boolean bln) {
//            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
