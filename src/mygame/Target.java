package mygame;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.HullCollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;

public class Target {

    Cylinder target;
    Geometry Geo_target;
    Node targetNode;
    GhostControl ghost;
    CollisionShape shape;
    RigidBodyControl rigidBody;

    public Target(Main main) {
        targetNode = new Node();
        target = new Cylinder(10, 20, 1f, .1f, true);
        Geo_target = new Geometry("Cylinder", target);
        Material mat_tex = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat_tex.setColor("Color", ColorRGBA.Blue);
        Geo_target.setMaterial(mat_tex);
        
        shape = new HullCollisionShape(target);
        ghost = new GhostControl(shape);
        rigidBody = new RigidBodyControl(shape,0f);
        
        targetNode.addControl(rigidBody);
        targetNode.addControl(ghost);
        targetNode.attachChild(Geo_target);
        rigidBody.setKinematic(true);
        targetNode.move(0, 1f, -2f);

        main.getRootNode().attachChild(targetNode);
        main.bulletAppState.getPhysicsSpace().add(rigidBody);
        main.bulletAppState.getPhysicsSpace().add(ghost);
    }
}
