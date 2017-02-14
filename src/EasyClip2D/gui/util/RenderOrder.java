
package EasyClip2D.gui.util;

import EasyClip2D.sprite.SpriteObject;
import java.util.ArrayList;

/**
 * This class is used to organize your sprites into 
 * a specified order defined by you.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class RenderOrder {

    private ArrayList<SpriteObject>[] sprites;
    
    public RenderOrder(int layerCount) {
        
        init(layerCount);
        
    }
    
    private void init(int length){
        
        sprites = new ArrayList[length];
        
        for (int i = 0; i < length; i++){
            
            sprites[i] = new ArrayList<SpriteObject>();
            
        }
        
    }
    
    public void addSprite(SpriteObject s){
        
        sprites[s.renderOrder()].add(s);
        
    }
    
    public ArrayList<SpriteObject>[] getLists(){
        
        return sprites;
        
    }
    
    public void remove(int index){
        
        sprites[index] = null;
        
    }
    
    public void clear(){
        
        int length = sprites.length;
        
        init(length);
        
    }
    
    public int length(){
        
        return sprites.length;
        
    }
    
}
