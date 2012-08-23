/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import de.eppleton.jbox2d.CircleShapeBuilder;
import de.eppleton.jbox2d.PolygonShapeBuilder;
import java.util.HashMap;
import org.box2d.proto.Box2D.PbJoint;
import org.box2d.proto.Box2D.PbWorld.Builder;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;
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
        
        Joint joint = createFlipper(world, new Vec2(2.25f, 3.73f), .3f, 0.1f, true);
        Body a = joint.m_bodyA;
        Body b = joint.m_bodyB;
        HashMap<Body,Integer> bodyMap = new HashMap<>();
        bodyMap.put(a, 0);
        bodyMap.put(b, 1);
        PbSerializer s = new PbSerializer();
        PbJoint.Builder serializeJoint = s.serializeJoint(joint, bodyMap, null);


        System.out.println("gravity {\n" +
"  x: 0.0\n" +
"  y: -10.0\n" +
"}\n" +
"allow_sleep: true\n" +
"warm_starting: true\n" +
"continuous_physics: true\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 3.1\n" +
"    y: 8.8\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 4.415\n" +
"  I: 4.262928\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 10.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: -1\n" +
"    }\n" +
"    shape {\n" +
"      type: POLYGON\n" +
"      radius: 0.01\n" +
"      points {\n" +
"        x: 2.9\n" +
"        y: 0.7\n" +
"      }\n" +
"      points {\n" +
"        x: -0.5999999\n" +
"        y: 2.9\n" +
"      }\n" +
"      points {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      normals {\n" +
"        x: 0.5321715\n" +
"        y: 0.8466365\n" +
"      }\n" +
"      normals {\n" +
"        x: -0.9792604\n" +
"        y: -0.20260556\n" +
"      }\n" +
"      normals {\n" +
"        x: 0.23464052\n" +
"        y: -0.9720822\n" +
"      }\n" +
"      centroid {\n" +
"        x: 0.76666677\n" +
"        y: 1.2\n" +
"      }\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 0.0\n" +
"    y: 8.0\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 6.4749994\n" +
"  I: 9.8923645\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 10.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: -1\n" +
"    }\n" +
"    shape {\n" +
"      type: POLYGON\n" +
"      radius: 0.01\n" +
"      points {\n" +
"        x: 7.2\n" +
"        y: -1.2\n" +
"      }\n" +
"      points {\n" +
"        x: 4.3\n" +
"        y: -1.9\n" +
"      }\n" +
"      points {\n" +
"        x: 5.4\n" +
"        y: -6.1\n" +
"      }\n" +
"      normals {\n" +
"        x: -0.23464055\n" +
"        y: 0.97208226\n" +
"      }\n" +
"      normals {\n" +
"        x: -0.9673723\n" +
"        y: -0.2533594\n" +
"      }\n" +
"      normals {\n" +
"        x: 0.9386698\n" +
"        y: -0.34481743\n" +
"      }\n" +
"      centroid {\n" +
"        x: 5.633333\n" +
"        y: -3.0666664\n" +
"      }\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -3.1\n" +
"    y: 8.8\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 4.415\n" +
"  I: 4.262928\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 10.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: -1\n" +
"    }\n" +
"    shape {\n" +
"      type: POLYGON\n" +
"      radius: 0.01\n" +
"      points {\n" +
"        x: 0.5999999\n" +
"        y: 2.9\n" +
"      }\n" +
"      points {\n" +
"        x: -2.9\n" +
"        y: 0.7\n" +
"      }\n" +
"      points {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      normals {\n" +
"        x: -0.5321715\n" +
"        y: 0.8466365\n" +
"      }\n" +
"      normals {\n" +
"        x: -0.23464052\n" +
"        y: -0.9720822\n" +
"      }\n" +
"      normals {\n" +
"        x: 0.9792604\n" +
"        y: -0.20260556\n" +
"      }\n" +
"      centroid {\n" +
"        x: -0.76666677\n" +
"        y: 1.2\n" +
"      }\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 0.0\n" +
"    y: 8.0\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 6.4749994\n" +
"  I: 9.8923645\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 10.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: -1\n" +
"    }\n" +
"    shape {\n" +
"      type: POLYGON\n" +
"      radius: 0.01\n" +
"      points {\n" +
"        x: -4.3\n" +
"        y: -1.9\n" +
"      }\n" +
"      points {\n" +
"        x: -7.2\n" +
"        y: -1.2\n" +
"      }\n" +
"      points {\n" +
"        x: -5.4\n" +
"        y: -6.1\n" +
"      }\n" +
"      normals {\n" +
"        x: 0.23464055\n" +
"        y: 0.97208226\n" +
"      }\n" +
"      normals {\n" +
"        x: -0.9386698\n" +
"        y: -0.34481743\n" +
"      }\n" +
"      normals {\n" +
"        x: 0.9673723\n" +
"        y: -0.2533594\n" +
"      }\n" +
"      centroid {\n" +
"        x: -5.633333\n" +
"        y: -3.0666664\n" +
"      }\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 3.1\n" +
"    y: 8.8\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 4.415\n" +
"  I: 4.262928\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 10.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: -1\n" +
"    }\n" +
"    shape {\n" +
"      type: POLYGON\n" +
"      radius: 0.01\n" +
"      points {\n" +
"        x: 2.9\n" +
"        y: 0.7\n" +
"      }\n" +
"      points {\n" +
"        x: -0.5999999\n" +
"        y: 2.9\n" +
"      }\n" +
"      points {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      normals {\n" +
"        x: 0.5321715\n" +
"        y: 0.8466365\n" +
"      }\n" +
"      normals {\n" +
"        x: -0.9792604\n" +
"        y: -0.20260556\n" +
"      }\n" +
"      normals {\n" +
"        x: 0.23464052\n" +
"        y: -0.9720822\n" +
"      }\n" +
"      centroid {\n" +
"        x: 0.76666677\n" +
"        y: 1.2\n" +
"      }\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 0.0\n" +
"    y: 8.0\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 6.4749994\n" +
"  I: 9.8923645\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 10.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: -1\n" +
"    }\n" +
"    shape {\n" +
"      type: POLYGON\n" +
"      radius: 0.01\n" +
"      points {\n" +
"        x: 7.2\n" +
"        y: -1.2\n" +
"      }\n" +
"      points {\n" +
"        x: 4.3\n" +
"        y: -1.9\n" +
"      }\n" +
"      points {\n" +
"        x: 5.4\n" +
"        y: -6.1\n" +
"      }\n" +
"      normals {\n" +
"        x: -0.23464055\n" +
"        y: 0.97208226\n" +
"      }\n" +
"      normals {\n" +
"        x: -0.9673723\n" +
"        y: -0.2533594\n" +
"      }\n" +
"      normals {\n" +
"        x: 0.9386698\n" +
"        y: -0.34481743\n" +
"      }\n" +
"      centroid {\n" +
"        x: 5.633333\n" +
"        y: -3.0666664\n" +
"      }\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -3.1\n" +
"    y: 8.8\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 4.415\n" +
"  I: 4.262928\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 10.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: -1\n" +
"    }\n" +
"    shape {\n" +
"      type: POLYGON\n" +
"      radius: 0.01\n" +
"      points {\n" +
"        x: 0.5999999\n" +
"        y: 2.9\n" +
"      }\n" +
"      points {\n" +
"        x: -2.9\n" +
"        y: 0.7\n" +
"      }\n" +
"      points {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      normals {\n" +
"        x: -0.5321715\n" +
"        y: 0.8466365\n" +
"      }\n" +
"      normals {\n" +
"        x: -0.23464052\n" +
"        y: -0.9720822\n" +
"      }\n" +
"      normals {\n" +
"        x: 0.9792604\n" +
"        y: -0.20260556\n" +
"      }\n" +
"      centroid {\n" +
"        x: -0.76666677\n" +
"        y: 1.2\n" +
"      }\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 0.0\n" +
"    y: 8.0\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 6.4749994\n" +
"  I: 9.8923645\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 10.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: -1\n" +
"    }\n" +
"    shape {\n" +
"      type: POLYGON\n" +
"      radius: 0.01\n" +
"      points {\n" +
"        x: -4.3\n" +
"        y: -1.9\n" +
"      }\n" +
"      points {\n" +
"        x: -7.2\n" +
"        y: -1.2\n" +
"      }\n" +
"      points {\n" +
"        x: -5.4\n" +
"        y: -6.1\n" +
"      }\n" +
"      normals {\n" +
"        x: 0.23464055\n" +
"        y: 0.97208226\n" +
"      }\n" +
"      normals {\n" +
"        x: -0.9386698\n" +
"        y: -0.34481743\n" +
"      }\n" +
"      normals {\n" +
"        x: 0.9673723\n" +
"        y: -0.2533594\n" +
"      }\n" +
"      centroid {\n" +
"        x: -5.633333\n" +
"        y: -3.0666664\n" +
"      }\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 3.1\n" +
"    y: 8.8\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 4.415\n" +
"  I: 4.262928\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 10.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: -1\n" +
"    }\n" +
"    shape {\n" +
"      type: POLYGON\n" +
"      radius: 0.01\n" +
"      points {\n" +
"        x: 2.9\n" +
"        y: 0.7\n" +
"      }\n" +
"      points {\n" +
"        x: -0.5999999\n" +
"        y: 2.9\n" +
"      }\n" +
"      points {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      normals {\n" +
"        x: 0.5321715\n" +
"        y: 0.8466365\n" +
"      }\n" +
"      normals {\n" +
"        x: -0.9792604\n" +
"        y: -0.20260556\n" +
"      }\n" +
"      normals {\n" +
"        x: 0.23464052\n" +
"        y: -0.9720822\n" +
"      }\n" +
"      centroid {\n" +
"        x: 0.76666677\n" +
"        y: 1.2\n" +
"      }\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 0.0\n" +
"    y: 8.0\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 6.4749994\n" +
"  I: 9.8923645\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 10.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: -1\n" +
"    }\n" +
"    shape {\n" +
"      type: POLYGON\n" +
"      radius: 0.01\n" +
"      points {\n" +
"        x: 7.2\n" +
"        y: -1.2\n" +
"      }\n" +
"      points {\n" +
"        x: 4.3\n" +
"        y: -1.9\n" +
"      }\n" +
"      points {\n" +
"        x: 5.4\n" +
"        y: -6.1\n" +
"      }\n" +
"      normals {\n" +
"        x: -0.23464055\n" +
"        y: 0.97208226\n" +
"      }\n" +
"      normals {\n" +
"        x: -0.9673723\n" +
"        y: -0.2533594\n" +
"      }\n" +
"      normals {\n" +
"        x: 0.9386698\n" +
"        y: -0.34481743\n" +
"      }\n" +
"      centroid {\n" +
"        x: 5.633333\n" +
"        y: -3.0666664\n" +
"      }\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -3.1\n" +
"    y: 8.8\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 4.415\n" +
"  I: 4.262928\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 10.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: -1\n" +
"    }\n" +
"    shape {\n" +
"      type: POLYGON\n" +
"      radius: 0.01\n" +
"      points {\n" +
"        x: 0.5999999\n" +
"        y: 2.9\n" +
"      }\n" +
"      points {\n" +
"        x: -2.9\n" +
"        y: 0.7\n" +
"      }\n" +
"      points {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      normals {\n" +
"        x: -0.5321715\n" +
"        y: 0.8466365\n" +
"      }\n" +
"      normals {\n" +
"        x: -0.23464052\n" +
"        y: -0.9720822\n" +
"      }\n" +
"      normals {\n" +
"        x: 0.9792604\n" +
"        y: -0.20260556\n" +
"      }\n" +
"      centroid {\n" +
"        x: -0.76666677\n" +
"        y: 1.2\n" +
"      }\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 0.0\n" +
"    y: 8.0\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 6.4749994\n" +
"  I: 9.8923645\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 10.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: -1\n" +
"    }\n" +
"    shape {\n" +
"      type: POLYGON\n" +
"      radius: 0.01\n" +
"      points {\n" +
"        x: -4.3\n" +
"        y: -1.9\n" +
"      }\n" +
"      points {\n" +
"        x: -7.2\n" +
"        y: -1.2\n" +
"      }\n" +
"      points {\n" +
"        x: -5.4\n" +
"        y: -6.1\n" +
"      }\n" +
"      normals {\n" +
"        x: 0.23464055\n" +
"        y: 0.97208226\n" +
"      }\n" +
"      normals {\n" +
"        x: -0.9386698\n" +
"        y: -0.34481743\n" +
"      }\n" +
"      normals {\n" +
"        x: 0.9673723\n" +
"        y: -0.2533594\n" +
"      }\n" +
"      centroid {\n" +
"        x: -5.633333\n" +
"        y: -3.0666664\n" +
"      }\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 0.0\n" +
"    y: 8.8\n" +
"  }\n" +
"  angle: -2.0943952\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 8.042478\n" +
"  I: 10.294372\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: -1\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 1.6\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 0.0\n" +
"    y: 8.8\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 10.0\n" +
"  I: 24.166668\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: -1\n" +
"    }\n" +
"    shape {\n" +
"      type: POLYGON\n" +
"      radius: 0.01\n" +
"      points {\n" +
"        x: -2.5\n" +
"        y: -1.0\n" +
"      }\n" +
"      points {\n" +
"        x: 2.5\n" +
"        y: -1.0\n" +
"      }\n" +
"      points {\n" +
"        x: 2.5\n" +
"        y: 1.0\n" +
"      }\n" +
"      points {\n" +
"        x: -2.5\n" +
"        y: 1.0\n" +
"      }\n" +
"      normals {\n" +
"        x: 0.0\n" +
"        y: -1.0\n" +
"      }\n" +
"      normals {\n" +
"        x: 1.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      normals {\n" +
"        x: 0.0\n" +
"        y: 1.0\n" +
"      }\n" +
"      normals {\n" +
"        x: -1.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      centroid {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 38.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 36.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 34.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 32.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 30.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 28.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 26.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 24.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 22.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 20.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 18.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 16.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 14.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 12.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 10.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 8.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 6.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 4.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 2.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: 0.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -2.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -4.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -6.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -8.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -10.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -12.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -14.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -16.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -18.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -20.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -22.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -24.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -26.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -28.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -30.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -32.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -34.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -36.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -38.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: DYNAMIC\n" +
"  position {\n" +
"    x: -40.0\n" +
"    y: 0.5\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.19634955\n" +
"  I: 0.0061359233\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 1.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: CIRCLE\n" +
"      center {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      radius: 0.25\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: STATIC\n" +
"  position {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.0\n" +
"  I: 0.0\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 0.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: POLYGON\n" +
"      radius: 0.01\n" +
"      points {\n" +
"        x: 50.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      points {\n" +
"        x: 50.0\n" +
"        y: 10.0\n" +
"      }\n" +
"      normals {\n" +
"        x: 1.0\n" +
"        y: -0.0\n" +
"      }\n" +
"      normals {\n" +
"        x: -1.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      centroid {\n" +
"        x: 50.0\n" +
"        y: 5.0\n" +
"      }\n" +
"    }\n" +
"  }\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 0.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: POLYGON\n" +
"      radius: 0.01\n" +
"      points {\n" +
"        x: -50.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      points {\n" +
"        x: -50.0\n" +
"        y: 10.0\n" +
"      }\n" +
"      normals {\n" +
"        x: 1.0\n" +
"        y: -0.0\n" +
"      }\n" +
"      normals {\n" +
"        x: -1.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      centroid {\n" +
"        x: -50.0\n" +
"        y: 5.0\n" +
"      }\n" +
"    }\n" +
"  }\n" +
"  fixtures {\n" +
"    restitution: 0.0\n" +
"    friction: 0.2\n" +
"    density: 0.0\n" +
"    sensor: false\n" +
"    filter {\n" +
"      category_bits: 1\n" +
"      mask_bits: 65535\n" +
"      group_index: 0\n" +
"    }\n" +
"    shape {\n" +
"      type: POLYGON\n" +
"      radius: 0.01\n" +
"      points {\n" +
"        x: -50.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      points {\n" +
"        x: 50.0\n" +
"        y: 0.0\n" +
"      }\n" +
"      normals {\n" +
"        x: 0.0\n" +
"        y: -1.0\n" +
"      }\n" +
"      normals {\n" +
"        x: -0.0\n" +
"        y: 1.0\n" +
"      }\n" +
"      centroid {\n" +
"        x: 0.0\n" +
"        y: 0.0\n" +
"      }\n" +
"    }\n" +
"  }\n" +
"}\n" +
"bodies {\n" +
"  type: STATIC\n" +
"  position {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angle: 0.0\n" +
"  linear_velocity {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  angular_velocity: 0.0\n" +
"  force {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  torque: 0.0\n" +
"  mass: 0.0\n" +
"  I: 0.0\n" +
"  linear_damping: 0.0\n" +
"  angular_damping: 0.0\n" +
"  bullet: false\n" +
"  allow_sleep: true\n" +
"  awake: true\n" +
"  active: true\n" +
"  fixed_rotation: false\n" +
"}\n" +
"joints {\n" +
"  type: REVOLUTE\n" +
"  body_a: 0\n" +
"  body_b: 13\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: 3.1\n" +
"    y: 0.0\n" +
"  }\n" +
"  ref_angle: 0.0\n" +
"  enable_limit: false\n" +
"  lower_limit: 0.0\n" +
"  upper_limit: 0.0\n" +
"  enable_motor: false\n" +
"  motor_speed: 0.0\n" +
"  max_motor_torque: 0.0\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 0\n" +
"  body_b: 12\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: -0.5999999\n" +
"    y: 2.8999996\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: 0.69282436\n" +
"    y: 0.3999934\n" +
"  }\n" +
"  length: 4.4654226\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 1\n" +
"  body_b: 12\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: 4.3\n" +
"    y: -1.9000001\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: 0.69282436\n" +
"    y: 0.3999934\n" +
"  }\n" +
"  length: 4.701064\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 1\n" +
"  body_b: 0\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: 4.3\n" +
"    y: -1.9000001\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  length: 2.9546578\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 1\n" +
"  body_b: 0\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: 7.2\n" +
"    y: -1.1999998\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: 2.9\n" +
"    y: 0.6999998\n" +
"  }\n" +
"  length: 2.954657\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: REVOLUTE\n" +
"  body_a: 2\n" +
"  body_b: 13\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: -3.1\n" +
"    y: 0.0\n" +
"  }\n" +
"  ref_angle: 0.0\n" +
"  enable_limit: false\n" +
"  lower_limit: 0.0\n" +
"  upper_limit: 0.0\n" +
"  enable_motor: false\n" +
"  motor_speed: 0.0\n" +
"  max_motor_torque: 0.0\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 2\n" +
"  body_b: 12\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: 0.5999999\n" +
"    y: 2.8999996\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: 0.69282436\n" +
"    y: 0.3999934\n" +
"  }\n" +
"  length: 4.4654226\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 3\n" +
"  body_b: 12\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: -4.3\n" +
"    y: -1.9000001\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: 0.69282436\n" +
"    y: 0.3999934\n" +
"  }\n" +
"  length: 4.701064\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 3\n" +
"  body_b: 2\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: -4.3\n" +
"    y: -1.9000001\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  length: 2.9546578\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 3\n" +
"  body_b: 2\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: -7.2\n" +
"    y: -1.1999998\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: -2.9\n" +
"    y: 0.6999998\n" +
"  }\n" +
"  length: 2.954657\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: REVOLUTE\n" +
"  body_a: 4\n" +
"  body_b: 13\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: 3.1\n" +
"    y: 0.0\n" +
"  }\n" +
"  ref_angle: 0.0\n" +
"  enable_limit: false\n" +
"  lower_limit: 0.0\n" +
"  upper_limit: 0.0\n" +
"  enable_motor: false\n" +
"  motor_speed: 0.0\n" +
"  max_motor_torque: 0.0\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 4\n" +
"  body_b: 12\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: -0.5999999\n" +
"    y: 2.8999996\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: -0.6928186\n" +
"    y: 0.40000343\n" +
"  }\n" +
"  length: 4.4654226\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 5\n" +
"  body_b: 12\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: 4.3\n" +
"    y: -1.9000001\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: -0.6928186\n" +
"    y: 0.40000343\n" +
"  }\n" +
"  length: 4.701064\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 5\n" +
"  body_b: 4\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: 4.3\n" +
"    y: -1.9000001\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  length: 2.9546578\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 5\n" +
"  body_b: 4\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: 7.2\n" +
"    y: -1.1999998\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: 2.9\n" +
"    y: 0.6999998\n" +
"  }\n" +
"  length: 2.954657\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: REVOLUTE\n" +
"  body_a: 6\n" +
"  body_b: 13\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: -3.1\n" +
"    y: 0.0\n" +
"  }\n" +
"  ref_angle: 0.0\n" +
"  enable_limit: false\n" +
"  lower_limit: 0.0\n" +
"  upper_limit: 0.0\n" +
"  enable_motor: false\n" +
"  motor_speed: 0.0\n" +
"  max_motor_torque: 0.0\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 6\n" +
"  body_b: 12\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: 0.5999999\n" +
"    y: 2.8999996\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: -0.6928186\n" +
"    y: 0.40000343\n" +
"  }\n" +
"  length: 4.4654226\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 7\n" +
"  body_b: 12\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: -4.3\n" +
"    y: -1.9000001\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: -0.6928186\n" +
"    y: 0.40000343\n" +
"  }\n" +
"  length: 4.701064\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 7\n" +
"  body_b: 6\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: -4.3\n" +
"    y: -1.9000001\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  length: 2.9546578\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 7\n" +
"  body_b: 6\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: -7.2\n" +
"    y: -1.1999998\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: -2.9\n" +
"    y: 0.6999998\n" +
"  }\n" +
"  length: 2.954657\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: REVOLUTE\n" +
"  body_a: 8\n" +
"  body_b: 13\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: 3.1\n" +
"    y: 0.0\n" +
"  }\n" +
"  ref_angle: 0.0\n" +
"  enable_limit: false\n" +
"  lower_limit: 0.0\n" +
"  upper_limit: 0.0\n" +
"  enable_motor: false\n" +
"  motor_speed: 0.0\n" +
"  max_motor_torque: 0.0\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 8\n" +
"  body_b: 12\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: -0.5999999\n" +
"    y: 2.8999996\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: 0.0\n" +
"    y: -0.8000002\n" +
"  }\n" +
"  length: 4.4654226\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 9\n" +
"  body_b: 12\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: 4.3\n" +
"    y: -1.9000001\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: 0.0\n" +
"    y: -0.8000002\n" +
"  }\n" +
"  length: 4.701064\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 9\n" +
"  body_b: 8\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: 4.3\n" +
"    y: -1.9000001\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  length: 2.9546578\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 9\n" +
"  body_b: 8\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: 7.2\n" +
"    y: -1.1999998\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: 2.9\n" +
"    y: 0.6999998\n" +
"  }\n" +
"  length: 2.954657\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: REVOLUTE\n" +
"  body_a: 10\n" +
"  body_b: 13\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: -3.1\n" +
"    y: 0.0\n" +
"  }\n" +
"  ref_angle: 0.0\n" +
"  enable_limit: false\n" +
"  lower_limit: 0.0\n" +
"  upper_limit: 0.0\n" +
"  enable_motor: false\n" +
"  motor_speed: 0.0\n" +
"  max_motor_torque: 0.0\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 10\n" +
"  body_b: 12\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: 0.5999999\n" +
"    y: 2.8999996\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: 0.0\n" +
"    y: -0.8000002\n" +
"  }\n" +
"  length: 4.4654226\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 11\n" +
"  body_b: 12\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: -4.3\n" +
"    y: -1.9000001\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: 0.0\n" +
"    y: -0.8000002\n" +
"  }\n" +
"  length: 4.701064\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 11\n" +
"  body_b: 10\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: -4.3\n" +
"    y: -1.9000001\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  length: 2.9546578\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: DISTANCE\n" +
"  body_a: 11\n" +
"  body_b: 10\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: -7.2\n" +
"    y: -1.1999998\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: -2.9\n" +
"    y: 0.6999998\n" +
"  }\n" +
"  length: 2.954657\n" +
"  frequency: 10.0\n" +
"  damping_ratio: 0.5\n" +
"}\n" +
"joints {\n" +
"  type: REVOLUTE\n" +
"  body_a: 12\n" +
"  body_b: 13\n" +
"  collideConnected: false\n" +
"  local_anchor_a {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  local_anchor_b {\n" +
"    x: 0.0\n" +
"    y: 0.0\n" +
"  }\n" +
"  ref_angle: 0.0\n" +
"  enable_limit: false\n" +
"  lower_limit: 0.0\n" +
"  upper_limit: 0.0\n" +
"  enable_motor: true\n" +
"  motor_speed: 2.0\n" +
"  max_motor_torque: 400.0\n" +
"}\n" +
"" + serializeJoint.build().toString());
        
    }
    
        private Joint createFlipper(World world, Vec2 position, float length, float height, boolean left) {
   
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
        return world.createJoint(revoluteJointDef);
        
    }
        
        
}
