package mygame;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Cylinder;

public class Target {

    Cylinder target;
    Geometry Geo_target;

    public Target(Main main) {


        target = new Cylinder(10, 20, 1f, .1f, true);
        Geo_target = new Geometry("Cylinder", target);
        Material mat_tex = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat_tex.setColor("Color", ColorRGBA.Blue);
        Geo_target.move(0, 1f, -2f);
        Geo_target.setMaterial(mat_tex);
        System.out.println("this happened");
        main.getRootNode().attachChild(Geo_target);
        System.out.println("this finished");

    }
}
