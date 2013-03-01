/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;

/**
 *
 * @author Owner
 */
public class Score {
    
    BitmapText scoreText;
    BitmapFont font;
    long total_score = 0;
    
    public Score(Main main, Node GUINode, BitmapFont font) {
        this.font = font;
        GUINode.detachAllChildren();
        scoreText = new BitmapText(font, true);
        scoreText.setText("Score: "+total_score);
        scoreText.setLocalTranslation(300, scoreText.getLineHeight(), 0); 
        GUINode.attachChild(scoreText);
    }
    
    public void update(){
        scoreText.setText("Score: "+total_score);
    }
}
