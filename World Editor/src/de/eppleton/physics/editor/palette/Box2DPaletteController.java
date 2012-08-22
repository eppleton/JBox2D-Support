/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.palette;

import de.eppleton.physics.editor.palette.items.B2DActiveEditorDrop;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.beans.BeanInfo;
import java.io.IOException;
import javax.swing.Action;
import org.netbeans.spi.palette.DragAndDropHandler;
import org.netbeans.spi.palette.PaletteActions;
import org.netbeans.spi.palette.PaletteController;
import org.netbeans.spi.palette.PaletteFactory;
import org.openide.nodes.Node;
import org.openide.text.ActiveEditorDrop;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.datatransfer.ExTransferable;

/**
 *
 * @author antonepple
 */
public class Box2DPaletteController {

    private static Box2DPaletteController palette = null;

    public static PaletteController createPalette() {
        try {
            if (null == palette) {

                return PaletteFactory.createPalette(
                        //Folder:      
                        "Box2DPalette",
                        //Palette Actions:
                        new PaletteActions() {
                    @Override
                    public Action[] getImportActions() {
                        return null;
                    }

                    @Override
                    public Action[] getCustomPaletteActions() {
                        return null;
                    }

                    @Override
                    public Action[] getCustomCategoryActions(Lookup lkp) {
                        return null;
                    }

                    @Override
                    public Action[] getCustomItemActions(Lookup lkp) {
                        return null;
                    }

                    @Override
                    public Action getPreferredAction(Lookup lkp) {
                        return null;
                    }
                },
                        //Palette Filter:  
                        null,
                        //Drag and Drop Handler:  
                        new DragAndDropHandler(true) {
                    @Override
                    public void customize(ExTransferable exTransferable, Lookup lookup) {
                        Node node = lookup.lookup(Node.class);
                        final B2DActiveEditorDrop activeDrop = node.getLookup().lookup(B2DActiveEditorDrop.class);
                        if (activeDrop != null) {
                            exTransferable.put(new ExTransferable.Single(ActiveEditorDrop.FLAVOR) {
                                protected Object getData() throws IOException, UnsupportedFlavorException {
                                    return activeDrop;
                                }
                            });
                        }
                    }
                });
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }
}
