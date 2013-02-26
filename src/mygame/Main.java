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
    

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        moCap = new Mocap();
        kinect = new KinectInterface(this);
        environment=new Environment(this);
        KinectSkeleton kinectskeleton=new KinectSkeleton(this);

        //Basic Lighting and Coordinates
        //initMaterials();
        initLight();
        initCoord();
        initPhysics();
        
        //Attach objects to the rootnode here:
        rootNode.attachChild(environment.ground);
        rootNode.attachChild(kinectskeleton.skeleton);

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

    
    //Please move this
    public void initPhysics() {

    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
