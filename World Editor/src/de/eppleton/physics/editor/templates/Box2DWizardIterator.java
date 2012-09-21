/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.templates;

import java.awt.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.netbeans.api.templates.TemplateRegistration;
import org.netbeans.spi.project.ui.templates.support.Templates;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.Panel;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.util.NbBundle.Messages;

// TODO define position attribute
@TemplateRegistration(folder = "JBox2D", displayName = "#Box2DWizardIterator_displayName", iconBase = "de/eppleton/physics/editor/templates/tar.png", content = {"Box2D.box2d", "Box2DProperties.bpr"}, description = "box2D.html", scriptEngine = "freemarker")
@Messages("Box2DWizardIterator_displayName=JBox2D World")
public final class Box2DWizardIterator implements WizardDescriptor.InstantiatingIterator<WizardDescriptor> {

    private int index;
    private WizardDescriptor wizard;
    private List<WizardDescriptor.Panel<WizardDescriptor>> panels;

    private List<WizardDescriptor.Panel<WizardDescriptor>> getPanels() {
        Project project = Templates.getProject(wizard);
        Sources sources = (Sources) project.getLookup().lookup(Sources.class);
        SourceGroup[] groups = sources.getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA);
        if (panels == null) {
            panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
            Panel<WizardDescriptor> selectFilePanel = Templates.buildSimpleTargetChooser(project, groups).bottomPanel(new Box2DWizardPanel1()).create();
            panels.add(selectFilePanel);

            String[] steps = createSteps();
            for (int i = 0; i < panels.size(); i++) {
                Component c = panels.get(i).getComponent();
                if (steps[i] == null) {
                    // Default step name to component name of panel. Mainly
                    // useful for getting the name of the target chooser to
                    // appear in the list of steps.
                    steps[i] = c.getName();
                }
                if (c instanceof JComponent) { // assume Swing components
                    JComponent jc = (JComponent) c;
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                    jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
                }
            }
        }
        return panels;
    }

    @Override
    public Set<?> instantiate() throws IOException {
        HashSet<DataObject> files = new HashSet<DataObject>();
        Set<String> keySet = wizard.getProperties().keySet();
       
        HashMap box2DMap = new HashMap();
        box2DMap.put("x", wizard.getProperty("gravity_x"));
        box2DMap.put("y", wizard.getProperty("gravity_y"));
        box2DMap.put("allow_sleep", wizard.getProperty("allow_sleep"));
        FileObject foundFolder = Templates.getTargetFolder(wizard);
        DataFolder targetFolder = DataFolder.findFolder(foundFolder);
        String targetName = Templates.getTargetName(wizard);

        DataObject template = DataObject.find(FileUtil.getConfigFile("Templates/JBox2D/Box2D.box2d"));
        DataObject createFromTemplate = template.createFromTemplate(targetFolder, targetName, box2DMap);
        
        // TODO return set of FileObject (or DataObject) you have created
        files.add(createFromTemplate);
        return files;
    }

    @Override
    public void initialize(WizardDescriptor wizard) {
        this.wizard = wizard;
    }

    @Override
    public void uninitialize(WizardDescriptor wizard) {
        panels = null;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> current() {
        return getPanels().get(index);
    }

    @Override
    public String name() {
        return index + 1 + ". from " + getPanels().size();
    }

    @Override
    public boolean hasNext() {
        return index < getPanels().size() - 1;
    }

    @Override
    public boolean hasPrevious() {
        return index > 0;
    }

    @Override
    public void nextPanel() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        index++;
    }

    @Override
    public void previousPanel() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        index--;
    }

    // If nothing unusual changes in the middle of the wizard, simply:
    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }
    // If something changes dynamically (besides moving between panels), e.g.
    // the number of panels changes in response to user input, then use
    // ChangeSupport to implement add/removeChangeListener and call fireChange
    // when needed

    // You could safely ignore this method. Is is here to keep steps which were
    // there before this wizard was instantiated. It should be better handled
    // by NetBeans Wizard API itself rather than needed to be implemented by a
    // client code.
    private String[] createSteps() {
        String[] beforeSteps = (String[]) wizard.getProperty("WizardPanel_contentData");
        assert beforeSteps != null : "This wizard may only be used embedded in the template wizard";
        String[] res = new String[(beforeSteps.length - 1) + panels.size()];
        for (int i = 0; i < res.length; i++) {
            if (i < (beforeSteps.length - 1)) {
                res[i] = beforeSteps[i];
            } else {
                res[i] = panels.get(i - beforeSteps.length + 1).getComponent().getName();
            }
        }
        return res;
    }
}
