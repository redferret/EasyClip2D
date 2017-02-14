
package EasyClip2D.sprite;

import java.awt.Graphics2D;

/**
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public interface SpriteInterface {

    /**
     * The Sprite's update method.
     */
    public void update();
    
    /**
     * @return The type of this sprite.
     */
    public int getSpriteType();
    
    /**
     * The Sprite's draw method used by the GraphicsPanel.
     */
    public void draw(Graphics2D g2d);
    
    /**
     * @return true is the sprite is dead and is ready to be removed from
     * the sprite manager and other game engine controllers.
     */
    public boolean isDead();
    
    /**
     * The method is only called one time after the sprite is constructed.
     * Additional construction code can go within this method.
     * Helps to organize blocks of code.
     */
    public abstract void spriteSetup();
    /**
     * This method is called after the sprite is updated.
     * Additional code or logic can be written here, such as
     * the behavior of the sprite. The method also
     * passes the current x and y positions of the sprite.
     */
    public abstract void spriteUpdate(float x, float y);
    
    /**
     * If a collision is detected between this object and another, then
     * this method is called, the object that collided with this object
     * is passed into the parameter as cSprite. Sprite2D also 
     * implements this method and a ContactListener must
     * be setup in order to have collisions be detected by Box2D.
     */
    public abstract void collisionDetected(SpriteObject collidee);
    
    /**
     * Returns the nearest sprite to this one. If collision bounds
     * are setup for this sprite then the PCR will determine how close
     * a sprite will be once the nearest is obtained. This
     * method will also return multiple sprites that are within this
     * sprite's PCR for each frame the collision thread goes through.
     */
    public abstract void nearestSprite(SpriteObject nearest);
    
    /**
     * This method is called after the sprite has been rendered to
     * the GraphicsPanel. The graphics is passed into the method
     * for additional paint procedures. The AffineTransform has been
     * reinitialized. This method is more conventional to use
     * as a debug for your sprites.
     */
    public abstract void additionalGraphics(Graphics2D g2d);
}
