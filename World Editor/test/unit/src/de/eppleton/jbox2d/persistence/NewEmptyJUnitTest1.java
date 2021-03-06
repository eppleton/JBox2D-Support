/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d.persistence;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import org.jbox2d.builders.BoxBuilder;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openide.util.Exceptions;

/**
 *
 * @author antonepple
 */
public class NewEmptyJUnitTest1 {

    private static final String WORLD_XML = "/Users/antonepple/bookstore-jaxb.xml";

    public NewEmptyJUnitTest1() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

    @Test
    public void hello() {
        try {
            // create JAXB context and instantiate marshaller
           org.jbox2d.dynamics.World world = new org.jbox2d.dynamics.World(new Vec2(0,-7));

            new BoxBuilder(world).position(5, 5).type(BodyType.DYNAMIC).halfHeight(1).halfWidth(1).build();
            World jaxbWorldFromWorld = PersistenceUtil.getJAXBWorldFromWorld(world);
            final File baseDir = new File("/Users/antonepple/");
            JAXBContext context = JAXBContext.newInstance(World.class);

            /*context.generateSchema(new SchemaOutputResolver() {
                @Override
                public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
                    return new StreamResult(new File(baseDir, suggestedFileName));
                }
            });*/

            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.setProperty("jaxb.schemaLocation", "http://www.eppleton.de/schemas/box2d http://www.eppleton.de/schemas/Box2d.xsd");

            m.marshal(jaxbWorldFromWorld, System.out);
            Writer w = null;
            try {
                w = new FileWriter(WORLD_XML);
                m.marshal(jaxbWorldFromWorld, w);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            } finally {
                try {
                    w.close();
                } catch (Exception e) {
                }
            }

            // get variables from our xml file, created before
            System.out.println();
            System.out.println("Output from our XML File: ");
            Unmarshaller um = context.createUnmarshaller();
            World world2 = (World) um.unmarshal(new FileReader(WORLD_XML));
            System.out.println("gravity "
                    + world2.gravity.y);

        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (JAXBException ex) {
            Exceptions.printStackTrace(ex);
        }

    }
}
