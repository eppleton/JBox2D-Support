/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.palette.items;

import com.google.protobuf.TextFormat;
import com.google.protobuf.TextFormat.ParseException;
import de.eppleton.jbox2d.CircleShapeBuilder;
import java.io.IOException;
import java.util.Properties;
import org.box2d.proto.Box2D;
import org.box2d.proto.Box2D.PbBody;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.serialization.pb.PbDeserializer;
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
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

@Messages({
    "LBL_B2dPaletteItem_LOADER=Files of B2dPaletteItem"
})
@MIMEResolver.ExtensionRegistration(
    displayName = "#LBL_B2dPaletteItem_LOADER",
mimeType = "text/x-box2d-palette-item",
extension = {"b2i"})
@DataObject.Registration(
    mimeType = "text/x-box2d-palette-item",
iconBase = "de/eppleton/physics/editor/palette/items/wizard.png",
displayName = "#LBL_B2dPaletteItem_LOADER",
position = 300)
@ActionReferences({
    @ActionReference(
        path = "Loaders/text/x-box2d-palette-item/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
    position = 100,
    separatorAfter = 200),
    @ActionReference(
        path = "Loaders/text/x-box2d-palette-item/Actions",
    id =
    @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
    position = 300),
    @ActionReference(
        path = "Loaders/text/x-box2d-palette-item/Actions",
    id =
    @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
    position = 400,
    separatorAfter = 500),
    @ActionReference(
        path = "Loaders/text/x-box2d-palette-item/Actions",
    id =
    @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
    position = 600),
    @ActionReference(
        path = "Loaders/text/x-box2d-palette-item/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
    position = 700,
    separatorAfter = 800),
    @ActionReference(
        path = "Loaders/text/x-box2d-palette-item/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
    position = 900,
    separatorAfter = 1000),
    @ActionReference(
        path = "Loaders/text/x-box2d-palette-item/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
    position = 1100,
    separatorAfter = 1200),
    @ActionReference(
        path = "Loaders/text/x-box2d-palette-item/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
    position = 1300),
    @ActionReference(
        path = "Loaders/text/x-box2d-palette-item/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
    position = 1400)
})
public class B2dPaletteItemDataObject extends MultiDataObject {

    public B2dPaletteItemDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        registerEditor("text/x-box2d-palette-item", false);

        Properties properties = new Properties();
        properties.load(pf.getInputStream());
        getCookieSet().assign(Properties.class, properties);
        final String body = properties.getProperty("body");
        B2DActiveEditorDrop b2DActiveEditorDrop = new B2DActiveEditorDrop() {
            @Override
            public Body createBody(World world) {
                try {
                    final Box2D.PbBody.Builder builder = Box2D.PbBody.newBuilder();
                    TextFormat.merge(body, builder);
                    PbBody build = builder.build();
                    PbDeserializer d = new PbDeserializer();
                    return d.deserializeBody(world, build);
                } catch (ParseException ex) {
                    Exceptions.printStackTrace(ex);
                }
                return null;

            }
        };
        getCookieSet().assign(B2DActiveEditorDrop.class, b2DActiveEditorDrop);
    }

    @Override
    protected Node createNodeDelegate() {
        DataNode nodeDelegate = (DataNode) super.createNodeDelegate();
        initNode(getLookup().lookup(Properties.class), nodeDelegate);
        return nodeDelegate;
    }

    @Override
    protected int associateLookup() {
        return 1;
    }
    public static String DISPLAY_NAME = "displayname";
    public static String ICON_BASE = "iconBase";

    private void initNode(Properties lookup, DataNode node) {
        if (lookup == null) {
            return;
        }
        node.setDisplayName(lookup.getProperty(DISPLAY_NAME, node.getDisplayName()));
        if (lookup.getProperty(ICON_BASE) != null) {
            node.setIconBaseWithExtension(lookup.getProperty(ICON_BASE));
        }
    }
}
