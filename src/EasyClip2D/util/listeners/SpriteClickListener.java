
package EasyClip2D.util.listeners;

import EasyClip2D.sprite.Sprite;
import EasyClip2D.sprite.SpriteObject;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Add's and checks sprites whether they have been clicked on
 * by a mouse or not. This can be used to create menu buttons.
 * Place the method <code>getClicked(MouseEvent e)</code> in your
 * <code>MousePressed(MouseEvent e)</code> method.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class SpriteClickListener {

    private ArrayList<SpriteObject> clickable;
    
    
    public SpriteClickListener() {
        clickable = new ArrayList<SpriteObject>();
    }
    /**
     * Add a sprite object as a clickable sprite
     */
    public void addClickable(SpriteObject s){
        clickable.add(s);
    }
    /**
     * Remove a sprite object from this list
     */
    public void removeClickable(SpriteObject s){
        clickable.remove(s);
    }
    /**
     * Clear the list of clickable sprites
     */
    public void clear(){
        clickable.clear();
    }
    
    /**
     * Obtain a sprite that has been clicked on.
     * Note that the order of sprites added will be the 
     * order of sprites checked. If two sprites are overlapping
     * only one sprite will be returned.
     */
    public SpriteObject getClicked(MouseEvent e){
        
        for (int i = 0; i < clickable.size(); i++){
            
            if (Sprite.checkMouseBounds(clickable.get(i), e)){
                return clickable.get(i);
            }
            
        }
        
        return null;
        
    }
    
    public void processClicked(MouseEvent e){
        
        try{
            
            for (int i = 0; i < clickable.size(); i++){
            
                if (Sprite.checkMouseBounds(clickable.get(i), e)){
                    
                    ((ClickableEvent)clickable.get(i)).clickEvent();
                    
                }

            }
            
        }catch (Exception ev){
            ev.printStackTrace();
        }
        
    }
    
    public SpriteObject get(int i){
        
        return clickable.get(i);
        
    }
    
    public int size(){
        return clickable.size();
    }
    
}
