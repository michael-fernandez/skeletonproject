package mygame;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author Michael
 */
//This is where the skeleton should be created
public class KinectSkeleton {

    Node skeleton = new Node(); // this is the skeleton, which connects to the root node.
    Geometry[] bones = new Geometry[2];

    // This should initalize the skeleton. Movements should be handled in update Movements
    public KinectSkeleton(Main main) {
        Material matW = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        matW.setColor("Color", ColorRGBA.White);
        if (main.kinect.joint != null) {
            float[][] jointArray = {{(float) main.kinect.joint[9][1] / 1000.0f, (float) main.kinect.joint[9][2] / 1000.0f, (float) main.kinect.joint[9][3] / 1000.0f}, {(float) main.kinect.joint[10][1] / 1000.0f, (float) main.kinect.joint[10][2] / 1000.0f, (float) main.kinect.joint[10][3] / 1000.0f}};
//            float[][] jointArray = {{(float) main.kinect.joint[10][1], (float) main.kinect.joint[10][2], (float) main.kinect.joint[10][3]}, //right wrist
//                {(float) main.kinect.joint[9][1], (float) main.kinect.joint[9][2], (float) main.kinect.joint[9][3]}, //right elbow
//                {(float) main.kinect.joint[8][1], (float) main.kinect.joint[8][2], (float) main.kinect.joint[8][3]}, //right shoulder
//                {(float) main.kinect.joint[2][1], (float) main.kinect.joint[2][2], (float) main.kinect.joint[2][3]}, //shoulder center
//                {(float) main.kinect.joint[4][1], (float) main.kinect.joint[4][2], (float) main.kinect.joint[4][3]}, //left shoulder
//                {(float) main.kinect.joint[5][1], (float) main.kinect.joint[5][2], (float) main.kinect.joint[5][3]}, //left elbow
//                {(float) main.kinect.joint[6][1], (float) main.kinect.joint[6][2], (float) main.kinect.joint[6][3]}, //left wrist
//                {(float) main.kinect.joint[0][1], (float) main.kinect.joint[0][2], (float) main.kinect.joint[0][3]}, //hip center
//                {(float) main.kinect.joint[13][1], (float) main.kinect.joint[13][2], (float) main.kinect.joint[13][3]}, //left knee
//                {(float) main.kinect.joint[14][1], (float) main.kinect.joint[14][2], (float) main.kinect.joint[14][3]}, //left ankle
//                {(float) main.kinect.joint[17][1], (float) main.kinect.joint[17][2], (float) main.kinect.joint[17][3]}, //right knee
//                {(float) main.kinect.joint[18][1], (float) main.kinect.joint[18][2], (float) main.kinect.joint[18][3]}}; //right ankle
            bones = new Geometry[jointArray.length];
            //start loop to connect all joints
            for (int i = 0; i < bones.length - 1; i++) {
                Cylinder c = new Cylinder(10, 10, 0.4f, 5f, true);
                //set geometry, connect and transform cylinder, set material
                bones[i] = new Geometry("Cylinder", c);
                setConnectiveTransform(jointArray[0], jointArray[1], bones[i]);
                bones[i].setMaterial(matW);
                //attach physics to bones
                RigidBodyControl phy = new RigidBodyControl(1f); //0f = 0 mass
                bones[i].addControl(phy);
                phy.setKinematic(true);
                //attach physics to world
                main.bulletAppState.getPhysicsSpace().add(phy);
                //attach to node so we can play
                skeleton.attachChild(bones[i]);
            }
        } else {
            System.out.println("NULL!");
        }
    }

    public void updateMovements(Main main) {
        if (main.kinect.joint != null) {
            float[][] jointArray = {{(float) main.kinect.joint[9][1] / 1000.0f, (float) main.kinect.joint[9][2] / 1000.0f, (float) main.kinect.joint[9][3] / 1000.0f}, {(float) main.kinect.joint[10][1] / 1000.0f, (float) main.kinect.joint[10][2] / 1000.0f, (float) main.kinect.joint[10][3] / 1000.0f}};
            for (int i = 0; i < bones.length - 1; i++) {
                setConnectiveTransform(jointArray[i], jointArray[i + 1], bones[i]);
            }
        } else {
            System.out.println("NULL!");
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
