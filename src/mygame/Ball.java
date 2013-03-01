/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author David Osinski
 */
public class Ball implements PhysicsCollisionListener {
    Main main;
    CollisionShape shape;
    RigidBodyControl rigidBody;
    Sphere ball_shape;
    Geometry ball_tex;
    GhostControl ghost;
    Node ballNode;
    Vector3f start_location;
    
    public Ball(Main main, Vector3f start) {
        
        this.main = main;
        start_location = start;
        start_location.setZ(start_location.z-0.5f);
        ball_shape = new Sphere(20, 20, .1f);
        ball_tex = new Geometry("A Texture Ball", ball_shape);
        ball_tex.move(start_location);
        
        Material mat_tex = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        //Texture tex = main.getAssetManager().loadTexture("Interface/Logo/Monkey.jpg");
        // mat_tex.setTexture("ColorMap", tex);
        ball_tex.setMaterial(mat_tex);
        //rootNode.attachChild(cube_tex);
        
        shape = new SphereCollisionShape(ball_shape.radius);
        rigidBody = new RigidBodyControl(shape,1f);
        rigidBody.setLinearVelocity(new Vector3f(0,0,-10f));
        ball_tex.addControl(rigidBody);
        ghost = new GhostControl(new SphereCollisionShape(ball_shape.radius));
        
        ballNode = new Node();
        ballNode.attachChild(ball_tex);
        ballNode.addControl(ghost);
        main.getRootNode().attachChild(ballNode);
        main.bulletAppState.getPhysicsSpace().addCollisionListener(this);
        main.bulletAppState.getPhysicsSpace().add(rigidBody);
        main.bulletAppState.getPhysicsSpace().add(ghost);
    }

    public void collision(PhysicsCollisionEvent event) {
        if ((event.getNodeB()==main.target.targetNode || event.getNodeA()==main.target.targetNode)
                &&(event.getNodeB()==ball_tex || event.getNodeA()==ball_tex)){
            System.out.println(ball_tex.getWorldTranslation().y);

            main.score.total_score++;
            main.score.update();
            start_location.setZ(start_location.z-0.2f);
            rigidBody.setPhysicsLocation(start_location);
            rigidBody.setLinearVelocity(new Vector3f(0,0,0));
        }
    }
    

}
