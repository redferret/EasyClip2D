
package EasyClip2D.gui.util;

import EasyClip2D.sprite.SpriteObject;
import EasyClip2D.util.SpriteList;

/**
 * The render list is used to set up some kind of
 * way to tell easy clip how to render your sprites
 * in a particular order.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class RenderList {
 
    private SpriteList def_list;
    
    private RenderOrder renderOrder;
    
    public RenderList() {
        
        renderOrder = null;
        
        def_list = new SpriteList();
        
    }
    
    /**
     * Adds a sprite to this list
     */
    public void add(SpriteObject s){
  
        if (s.renderOrder() != -1 || s.renderOrder() > 0){

            if (renderOrder != null)
                
                renderOrder.addSprite(s);

            else
                
                throw new IllegalStateException("Render List Doesn't Exist.");
            
        }else{
            
            def_list.addSprite(s);
            
        }
          
    }
    
    /**
     * Removes all active sprites
     */
    public void removeAll(){
        
        def_list.clear();
        
        if (renderOrder != null)
            
            renderOrder.clear();
        
    }
    
    /**
     * Returns the list of sprites in the order
     * they are to be rendered.
     */
    public RenderOrder getRenderOrder(){
        
        return renderOrder;
        
    }
    
    /**
     * Returns the list of sprites in the
     * default render order. These are sprites
     * that are not within an ordered list.
     */
    public SpriteObject getDefaultOrder(){
        
        return def_list.getList();
        
    }
    
    /**
     * Registers an ordered list, must be called before any
     * sprites can be placed in a particular render order.
     */
    public void registerRenderOrder(int renderLength){
        
        renderOrder = new RenderOrder(renderLength);
        
    }
    
}
