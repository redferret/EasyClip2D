
package EasyClip2D.collisions;

import EasyClip2D.geom.SpritePoint;
import java.awt.Graphics2D;
import java.io.Serializable;

/**
 * Implementation of this object can define a collision boundary
 * for a Sprite Object. 
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public abstract class SpriteBound implements Serializable {

    /**
     * The center point for your bound.
     */
    protected SpritePoint centre;
    
    /**
     * The previous angle of rotation.
     */
    protected float prevRotation;
    
    /**
     * Method for translation of this Boundary
     * @param newCentre The new location, or location of a sprite's center
     * or position.
     */
    public abstract void move(SpritePoint newCentre);
    /**
     * Draw method for this boundary
     * @param g2d the current graphics device
     */
    public abstract void draw(Graphics2D g2d);
    /**
     * Rotate bound from an origin by mesure of radians
     * @param origin of rotation.
     * @param radians of rotation.
     */
    public abstract void rotateBound(SpritePoint origin, float radians);
    
    /**
     * Intersection algorithm for this boundary to another boundary of
     * type SpriteBound
     * @param bound the test bound for intersection
     * @return if bound intersects with this bound.
     */
    public abstract boolean intersects(SpriteBound bound);
    
    /**
     * Returns the previous angle of rotation.
     */
    public final float getPrevRotation(){
 
        return prevRotation;
        
    }
    
    public final void setPrevRotation(float radians){
        
        prevRotation = radians;
        
    }
    
    public final SpritePoint getCentre(){
        return centre;
    }
    
    public final void setCentre(SpritePoint point){
        
        centre = new SpritePoint(point);
        
    }
    
}
