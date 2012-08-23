/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import de.eppleton.jbox2d.CircleShapeBuilder;
import de.eppleton.jbox2d.PolygonShapeBuilder;
import org.box2d.proto.Box2D.PbBody;
import org.box2d.proto.Box2D.PbWorld.Builder;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.jbox2d.serialization.pb.PbSerializer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
        World world = new World(gravity);
//        BodyDef groundBodyDef = new BodyDef();
//        groundBodyDef.position.set(0, -10);
//        Body groundBody = world.createBody(groundBodyDef);
//        PolygonShape groundBox = new PolygonShape();
//        groundBox.setAsBox(50, 10);
//        groundBody.createFixture(groundBox, 0);
//
//        new CircleShapeBuilder(world).position(5, 5).radius(1).density(1).bullet(true).build();

        // Dynamic Body
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyType.DYNAMIC;
//        bodyDef.position.set(0, 4);
//        Body body = world.createBody(bodyDef);
//        PolygonShape dynamicBox = new PolygonShape();
//        dynamicBox.setAsBox(1, 1);
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape = dynamicBox;
//        fixtureDef.density = 1;
//        fixtureDef.friction = 0.3f;
//        body.createFixture(fixtureDef);
        
        Body body = createFlipper(world, new Vec2(2.25f, 3.73f), .3f, 0.1f, true);
        PbSerializer s = new PbSerializer();
        Builder serializeWorld = s.serializeWorld(world);

        System.out.println("serializeWorld.toString() " + serializeWorld.build().toString());
        
    }
    
        private Body createFlipper(World world, Vec2 position, float length, float height, boolean left) {
   
        float halfLength = (length / 2);
        float halfHeight = height / 2;
        float centerX = -halfLength + halfHeight;
        float centerY = 0f;
  
        float curveX = (float) (Math.cos(0.7853982) * halfHeight);
        float curveY = (float) (Math.sin(0.7853982) * halfHeight);
        Body flipperBody = null;
        if (left) {
            flipperBody = new PolygonShapeBuilder(world).position(position).
                    type(BodyType.DYNAMIC).vertices(-halfLength, 0.0f,
                    centerX - curveX, centerY + curveY,
                    centerX, halfHeight,
                    halfLength - (halfHeight / 2), halfHeight / 2,
                    halfLength, 0,
                    halfLength - (halfHeight / 2), -halfHeight / 2,
                    centerX - curveX, centerY - curveY,
                    centerX, -halfHeight).userData("flipper").density(1).friction(0).
                    restitution(.5f).build();
        } else {
            centerX = halfLength - halfHeight;

            flipperBody = new PolygonShapeBuilder(world).position(position).
                    type(BodyType.DYNAMIC).vertices(halfLength, 0.0f,
                    centerX + curveX, centerY + curveY,
                    centerX, halfHeight,
                    -halfLength + (halfHeight / 2), halfHeight / 2,
                    -halfLength, 0,
                    -halfLength + (halfHeight / 2), -halfHeight / 2,
                    centerX, -halfHeight,
                    centerX + curveX, centerY - curveY).userData("flipper").
                    density(1).friction(0).restitution(.5f).build();
        }

//        Body flipperBody = new BoxBuilder(this).type(BodyType.DYNAMIC).position(position).
//                halfWidth(length / 2).halfHeight(height / 2).density(2).friction(0).
//                userData(info).restitution(.5f).
//                build();
        Vec2 jointPosition = position.add(new Vec2(((length / 2) - halfHeight) * (left ? -1 : 1), 0));
        Body circleBody = new CircleShapeBuilder(world).type(BodyType.STATIC).
                position(jointPosition).build();

        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.initialize(flipperBody, circleBody, jointPosition);
        revoluteJointDef.upperAngle = left ? .3f : .6f;
        revoluteJointDef.lowerAngle = left ? -.6f : -.3f;
        revoluteJointDef.enableLimit = true;
        revoluteJointDef.maxMotorTorque = 10.0f;
        revoluteJointDef.motorSpeed = 0.0f;
        revoluteJointDef.enableMotor = true;
        world.createJoint(revoluteJointDef);
        return flipperBody;
    }
        
        
}
