/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.protobuf.TextFormat;
import de.eppleton.jbox2d.CircleShapeBuilder;
import java.io.IOException;
import org.box2d.proto.Box2D.PbWorld.Builder;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.serialization.SerializationResult;
import org.jbox2d.serialization.pb.PbSerializer;
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
public class NewEmptyJUnitTest {

    public NewEmptyJUnitTest() {
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
        Vec2 gravity = new Vec2(0, -10);
        boolean doSleep = true;
        World world = new World(gravity, doSleep);
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(0, -10);
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(50, 10);
        groundBody.createFixture(groundBox, 0);

        new CircleShapeBuilder(world).position(5, 5).radius(1).density(1).bullet(true).build();
        
        // Dynamic Body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(0, 4);
        Body body = world.createBody(bodyDef);
        PolygonShape dynamicBox = new PolygonShape();
        dynamicBox.setAsBox(1, 1);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicBox;
        fixtureDef.density = 1;
        fixtureDef.friction = 0.3f;
        body.createFixture(fixtureDef);
        PbSerializer s = new PbSerializer();
        Builder serializeWorld = s.serializeWorld(world);
        
        System.out.println("serializeWorld.toString() "+serializeWorld.build().toString());
    }
}
