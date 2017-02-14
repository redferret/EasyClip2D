
package EasyClip2D.sprite;

import EasyClip2D.gui.graphic.image.SpriteImage;
import EasyClip2D.geom.SpritePoint;
import EasyClip2D.geom.Vec2d;
import EasyClip2D.util.Delay;
import java.awt.image.BufferedImage;

/**
 * The SpriteObject defines the Sprite and Sprite2D objects
 * that are used in Easy Clip2D.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public abstract class SpriteObject implements SpriteInterface {
    
    public static final float DEFAULT_MAX_ROTATION = (float)(Math.PI * 2);
    
    protected SpriteImage spriteImage;
    
    protected Delay animationDelay;
    
    protected int spriteFrame, renderOrder;
    
    protected boolean isDead, renderOffScreen, render, delete;
    
    protected boolean animate, checkBounds, hasImage, renderToViewPort;
    
    protected int spriteType;
    
    protected Vec2d velocity;
    
    protected SpritePoint pos, center, bounds;
    
    protected SpriteObject next, prev;
    
    protected SpritePoint anchor;
    
    private static final SpritePoint EMPTY_BOUNDS = new SpritePoint();
    
    /**
     * @return the bitmap object.
     */
    public final SpriteImage getSpriteImage() {
        return spriteImage;
    }
    /**
     * If the sprite is animated, such that sprite blocks are being used
     * this method will return true.
     */
    public final boolean isAnimated(){
        return animate;
    }
    /**
     * Sets whether this sprite is animated or not.
     */
    public final void setAnimate(boolean animate){
        this.animate = animate;
    }
    
    public final boolean toBeDeleted(){
        
        return delete;
        
    }
    
    public final void toBeDeleted(boolean b){
        
        delete = b;
        
    }
    
    /**
     * If the sprite is animated and has a sprite spriteImage loaded
     * this method will return the current frame number of this
     * sprite.
     */
    public final int getCurrentFrame(){
        return spriteFrame;
    }
    /**
     * Sets the spriteImage frame for this sprite.
     */
    public final void setCurrentFrame(int spriteFrame){
        this.spriteFrame = spriteFrame;
    }
    /**
     * flags if this sprite is dead or not.
     */
    public final void isDead(boolean isDead) {
        this.isDead = isDead;
    }
    /**
     * @return if the sprite is dead.
     */
    public final boolean isDead() {
        return this.isDead;
    }
    
    public final void setRenderOrder(int order){
        renderOrder = (order - 1);
    }
    
    public final boolean renderOffScreen(){
        
        return renderOffScreen;
        
    }
    
    /**
     * Tells the engine to render the sprite if the sprite
     * is not on the screen.
     * @param b 
     */
    public final void renderOffScreen(boolean b){
        
        renderOffScreen = b;
        
    }
    
    public final int renderOrder(){
        return renderOrder;
    }
    
    /**
     * Set the type of this sprite.
     */
    public final void setSpriteType(int type){
        spriteType = type;
    }
    
    /**
     * Gets the sprite type
     */
    public final int getSpriteType(){
        return spriteType;
    }
    
    /**
     * Returns the center point of this sprite.
     * No copy is returned.
     */
    public final SpritePoint getCenter(){
        
        return center;
        
    }
    
    /**
     * Set the sprite's position to the point given.
     * This is a safe method to use. You don't need to create
     * a copy of the sprite point being passed into the method.
     */
    public final void setLocationTo(SpritePoint point){
        
        SpritePoint.copyTo(point, pos);
        
    }
    
    /**
     * Returns the position of the sprite. Keep in mind that this method is
     * not safe in the sense that it does not return a copy of the 
     * sprite point representing the position of this sprite.
     * Use the copy methods in the SpritePoint class to create
     * safe operations.
     * 
     * @return the current sprite position
     */
    public SpritePoint getPosition() {
        
        return pos;
        
    }
    
    /**
     * The width of the sprite, given by the spriteImage's dimensions
     */
    public float getWidth(){
        
        if (hasImage){
        
            if (animate){
                return spriteImage.getSpriteBlock(0).getWidth();
            }

            return spriteImage.getImage().getWidth();
            
        }else
            return 0;
    }
    /**
     * The height of the sprite, given by the spriteImage's dimensions
     */
    public float getHeight(){
        
        if (hasImage){
        
            if (animate){
                return spriteImage.getSpriteBlock(0).getHeight();
            }

            return spriteImage.getImage().getHeight();
            
        }else
            return 0;
    }
    
    public float getPotentialCollisionRadius(){
        
        return 0;
        
    }
    /**
     * Adds a sprite to the sprite list in front of this sprite.
     * It is recommended to not call this method. There will be a
     * chance that the sprite added is not rendered, or
     * even updated correctly.
     * @param sprite Sprite being added
     */
    public final synchronized void addNext(SpriteObject sprite){
        
        if (next == null)
            
            next = sprite;
        
        else {
            
            if (sprite.prev == null)
        
                sprite.prev = this;
            
        }
    }
    
    /**
     * Adds a sprite to the sprite list behind this sprite.
     * It is recommended to not call this method. There will be a
     * chance that the sprite added is not rendered, or
     * even updated correctly.
     * @param sprite Sprite being added
     */
    public final void addPrev(SpriteObject sprite){
        
        prev = sprite;
        
    }
    
    /**
     * Returns the sprite in front of this sprite in the sprite list
     */
    public final synchronized SpriteObject getNext(){
        
        return next;
        
    }
    
    /**
     * Returns the sprite behind this sprite in the sprite list
     */
    public final synchronized SpriteObject getPrev(){
        
        return prev;
        
    }
    
    /**
     * Returns if there is a sprite in front of this sprite
     */
    public final synchronized boolean hasNext(){
        
        return next != null;
        
    }
    
    /**
     * Returns if there is a sprite behind this sprite
     */
    public final synchronized boolean hasPrev(){
        
        return prev != null;
        
    }
    
    /**
     * The delete function is sensitive but can be used
     * to also kill a sprite directly instead of calling 
     * the kill sprite method in the game engine which
     * only flags the sprite to be deleted later, usually
     * within only a few frames.
     */
    public final synchronized void delete(){
        
        if (next != null){
            
            next.prev = prev;
        }
        
        if (prev != null){
            
            prev.next = next;
            
        }
        
        next = null;
        
        prev = null;
        
    }
    
    /**
     * Sets the anchor point based off another point.
     */
    public final void setAnchorPointTo(SpritePoint p){
        
        anchor = p;
        
    }
    
    /**
     * Sets the anchor point for the sprite's image when
     * it is rendered over the sprite's body. By default
     * the images position is based off the top left hand
     * corner of the screen. You can anchor the image by
     * telling the engine to render the image x number
     * of pixels to the left or right denoted by a negative
     * sign, which directs the image to the left if negative.
     */
    public final void setImageAnchor(float x, float y){
        
        anchor.setLocation(x, y);
        
    }
    
    /**
     * The bounds is used for rendering sprites properly onto the screen.
     * If the sprite falls out of sight from the user, then the sprite
     * will not render it's graphics. If there are no boundaries for the
     * sprite then you may have a sprite pop off the screen at the wrong time.
     * @param w - width
     * @param h - height
     */
    public final void setBounds(float w, float h){
        
        if (w < 0 || h < 0){
            
            throw new IllegalStateException("Bounds must be positive");
            
        }
        
        if (bounds == null){
            
            bounds = new SpritePoint(w, h);
            
            return;
            
        }
        
        bounds.setLocation(w, h);
        
    }
    
    public final SpritePoint getBounds(){
        
        if (bounds == null){
            
            return EMPTY_BOUNDS;
            
        }
        
        return bounds;
        
    }
    
    /**
     * Automatically sets the bounds of this sprite to the image provided.
     * @param image 
     */
    public void setBoundsToImage(BufferedImage image){
        
        if (bounds == null){
            
            bounds = new SpritePoint();
            
        }
        
        bounds.setLocation(image.getWidth(), image.getHeight());
        
    }
    
    /**
     * Tells the engine if this sprite is to be rendered or not
     * @param render 
     */
    public void render(boolean render){
        
        this.render = render;
        
    }
    
    public boolean renderSprite(){
        
        return render;
        
    }
    
}
