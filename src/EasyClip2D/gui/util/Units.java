
package EasyClip2D.gui.util;

import EasyClip2D.Box2D.org.jbox2d.common.Vec2;
import EasyClip2D.geom.SpritePoint;

/**
 * The Units class is used to specify a scaling
 * value for Box2D. Since everything in Box2D uses meters, 
 * it's important to convert pixel measurements into meters.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class Units {

    private SpritePoint location;
    
    public static float scale_value = 1.0f;
    
    public Units() {
        
        this(0, 0);
        
    }
    
    public Units(float x, float y) {
        
        location = new SpritePoint(x, y);
        
    }
    
    /**
     * Set's the scaling value for Box2D.
     */
    public static void setUnitScale(float scale){
        
        scale_value = scale;
        
    }
    
    /**
     * This method is used by the game engine, it
     * should not be modified.
     */
    public synchronized void setLocation(float x, float y){
        
        location.setLocation(x, y);
        
    }
    
    /**
     * This method is used by the game engine, it
     * should not be modified.
     */
    public synchronized void setScaledLocation(Vec2 pos){
        
        location.setLocation(pos.x * scale_value, pos.y * scale_value);
        
    }
    
    /**
     * This method is used by the game engine, it
     * should not be modified.
     */
    public synchronized SpritePoint getPosition(){
        
        return location;
        
    }
    
    /**
     * This method is used by the game engine, it
     * should not be modified.
     */
    public synchronized Vec2 getScaledPosition(){
        
        return new Vec2(location.x * (1f/scale_value), location.y * (1f/scale_value));
        
    }
    
}
