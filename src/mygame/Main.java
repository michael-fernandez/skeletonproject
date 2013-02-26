package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;



public class Main extends SimpleApplication {

    BulletAppState buildAppState; //look up AppStates (not entirely sure what they are
    //These materials should not be here. Move to their local classes
    Material matG, matB, matR, matW;
    KinectInterface kinect;
    Environment environment;
    Mocap moCap;
    Geometry[] bones; //will make up the person
    Cylinder c = new Cylinder(10, 10, 0.04f, 1f, true);
    Node skeleton = new Node(); //attach skeleton of person to it's own node

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        moCap = new Mocap();
        kinect = new KinectInterface(this);
        environment=new Environment(this);

        //initialize game
        initMaterials();
        initLight();
        //initFloor();
        initCoord();
        initPhysics();
        rootNode.attachChild(environment.ground);

        //set camera
        cam.setLocation(new Vector3f(5f, 3f, 5f));
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        flyCam.setMoveSpeed(10);
    }

    @Override
    public void simpleUpdate(float tpf) {
        kinect.getData();
        //TODO: add update code
    }

    public void initMaterials() {
        //set matrials and colors for geometries
        matG = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matG.setColor("Color", ColorRGBA.Green);
        matB = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matB.setColor("Color", ColorRGBA.Blue);
        matR = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matR.setColor("Color", ColorRGBA.Red);
        matW = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matW.setColor("Color", ColorRGBA.White);
    }

    public void initLight() {
        //set directed light for diffuse lighting
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1f));
        rootNode.addLight(sun);
    }

    public void initCoord() {
        //set coordinate system so we can keep track of directions
        Arrow x = new Arrow(new Vector3f(1, 0, 0));
        Geometry Xarrow = new Geometry("Arrow", x);
        Xarrow.setMaterial(matR);
        Arrow y = new Arrow(new Vector3f(0, 1, 0));
        Geometry Yarrow = new Geometry("Arrow", y);
        Yarrow.setMaterial(matG);
        Arrow z = new Arrow(new Vector3f(0, 0, 1));
        Geometry Zarrow = new Geometry("Arrow", z);
        Zarrow.setMaterial(matB);
        rootNode.attachChild(Xarrow);
        rootNode.attachChild(Yarrow);
        rootNode.attachChild(Zarrow);
    }

    // Transform the Cylinder c such that it connects p1,p2
    private void setConnectiveTransform(float[] p1, float[] p2, Geometry c) {
        //Find Direction
        Vector3f u = new Vector3f(p2[0] - p1[0], p2[1] - p1[1], p2[2] - p1[2]);
        float length = u.length();
        u = u.normalize();
        //Set Rotation
        Vector3f v = u.cross(Vector3f.UNIT_Z);
        Vector3f w = u.cross(v);
        Matrix3f m = new Matrix3f(u.x, v.x, w.x, u.y, v.y, w.y, u.z, v.z, w.z);
        c.setLocalRotation(m);
        //Set Scaling
        c.setLocalScale(1f, 1f, length);
        //Set Translation
        float[] center = {(p1[0] + p2[0]) / 2, (p1[1] + p2[1]) / 2, (p1[2] + p2[2]) / 2};
        c.setLocalTranslation(center[0], center[1], center[2]);
    }

    //Please move this
    public void initPhysics() {
        //pick joints you want
        if (kinect.joint != null) {
            float[][] jointArray = {{(float) kinect.joint[10][1], (float) kinect.joint[10][2], (float) kinect.joint[10][3]}, {(float) kinect.joint[11][1], (float) kinect.joint[11][2], (float) kinect.joint[11][3]}};
            bones = new Geometry[jointArray.length];
            //start loop to connect all joints
            for (int i = 0; i < bones.length; i++) {
                //set geometry, connect and transform cylinder, set material
                bones[i] = new Geometry("Cylinder", c);
                setConnectiveTransform(jointArray[0], jointArray[1], bones[i]);
                bones[i].setMaterial(matW);
                //attach physics to bones
                RigidBodyControl phy = new RigidBodyControl(0f); //0f = 0 mass
                bones[i].addControl(phy);
                phy.setKinematic(true);
                //attach physics to world
                buildAppState.getPhysicsSpace().add(phy);
                //attach to node so we can play
                skeleton.attachChild(bones[i]);
            }
            rootNode.attachChild(skeleton);
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
