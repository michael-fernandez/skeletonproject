/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Michael
 */
public class Environment {

    Geometry ground;  //this connects to the root node
   
    public Environment(Main main) {
        initFloor(main);
    }

    public void initFloor(Main main) {
        //create the ground we play on
        Box b = new Box(Vector3f.ZERO, 10f, 0.1f, 5f);
        ground = new Geometry("Box", b);
        Material matG = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        matG.setColor("Color", ColorRGBA.Green);
        ground.setMaterial(matG);

    }
}
