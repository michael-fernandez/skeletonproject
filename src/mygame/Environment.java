/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.PlaneCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

/**
 *
 * @author Michael
 */



public class Environment {

    Geometry ground;  //this connects to the root node
    Texture text;
    CollisionShape shape;
    RigidBodyControl rigidBody;
    
    public Environment(Main main) {
        initFloor(main);
    }

    public final void initFloor(Main main) {
        //create the ground we play on
        Box b = new Box(Vector3f.ZERO, 10f, 0.1f, 10f);
    
        ground = new Geometry("Box", b);
        Material matG = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        
        ground.getMesh().scaleTextureCoordinates(new Vector2f(35,35));
        text = main.getAssetManager().loadTexture("grass2.png");
        text.setWrap(Texture.WrapMode.Repeat);
        matG.setTexture("ColorMap", text);
        ground.setMaterial(matG);
        
        shape = new BoxCollisionShape();
        rigidBody = new RigidBodyControl(0f);
        ground.addControl(rigidBody);
        
        main.getRootNode().attachChild(ground);
    }
}
