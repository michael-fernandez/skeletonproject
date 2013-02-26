
package mygame;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;

/**
 *
 * @author Michael
 */
//This is where the skeleton should be created
public class KinectSkeleton {

    Node skeleton = new Node(); // this is the skeleton, which connects to the root node. 
    //I moved this local as no one else will need this. Geometry[] bones; //will make up the person

    
    // you may wnat to move most of this out of the constructor.
    public KinectSkeleton(Main main) {
        Material matW = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        matW.setColor("Color", ColorRGBA.White);
        if (main.kinect.joint != null) {
            float[][] jointArray = {{(float) main.kinect.joint[10][1], (float) main.kinect.joint[10][2], (float) main.kinect.joint[10][3]}, {(float) main.kinect.joint[11][1], (float) main.kinect.joint[11][2], (float) main.kinect.joint[11][3]}};
            Geometry[] bones = new Geometry[jointArray.length];
            //start loop to connect all joints
            for (int i = 0; i < bones.length; i++) {
                Cylinder c = new Cylinder(10, 10, 0.04f, 1f, true);
                //set geometry, connect and transform cylinder, set material
                bones[i] = new Geometry("Cylinder", c);
                setConnectiveTransform(jointArray[0], jointArray[1], bones[i]);
                bones[i].setMaterial(matW);
                //attach physics to bones
                RigidBodyControl phy = new RigidBodyControl(0f); //0f = 0 mass
                bones[i].addControl(phy);
                phy.setKinematic(true);
                //attach physics to world
                main.buildAppState.getPhysicsSpace().add(phy);
                //attach to node so we can play
                skeleton.attachChild(bones[i]);
            }

        }
    }

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
}
