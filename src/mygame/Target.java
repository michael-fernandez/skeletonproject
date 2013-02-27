package mygame;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Cylinder;

public class Target {

    private Cylinder target;
    protected Geometry Geo_target;

    public Target(Main main) {


        target = new Cylinder(10, 20, 2f, .05f, true);
        Geo_target = new Geometry("Cylinder", target);
        
        Material mat_tex = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat_tex.setColor("Color", ColorRGBA.Blue);
        
        Geo_target.setMaterial(mat_tex);

        main.getRootNode().attachChild(Geo_target);
        //Geo_target.setLocalTranslation(5, 0, 5);
        //Geo_target.setLocalRotation();
    }
}
