/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyClip2D.util.listeners;

import EasyClip2D.sprite.util.HoveredSprite;
import EasyClip2D.sprite.Sprite;
import EasyClip2D.sprite.SpriteObject;
import EasyClip2D.util.Tools;
import java.awt.event.MouseEvent;

/**
 *
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class HoveredListener {

    public boolean enabled;
    
    private static HoveredSprite hoveredSprite;
    
    public HoveredListener(){
        
        enabled = false;
        
    }
    
    public void enable(boolean b){
        enabled = b;
    }
    
    public void highlight(MouseEvent e){
        
        if (Tools.ec_engine == null && enabled){
            return;
        }
        
        // Get the sprite that is being hovered
        SpriteObject temp = Tools.ec_engine.getClickableSprites().getClicked(e);
        
        if (temp != null){
               
            // Check to see if the sprite that was hovered is no longer
            // being hovered.
            if (hoveredSprite != temp && hoveredSprite != null){
                hoveredSprite.hovered(false);
            }
            
            hoveredSprite = (HoveredSprite)temp;

            hoveredSprite.hovered(true);

        } else{

            if (hoveredSprite != null)
                
                hoveredSprite.hovered(false);
            
        }
        
    }
    
}
