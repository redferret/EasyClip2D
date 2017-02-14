
package EasyClip2D.gui.graphic.image;

import EasyClip2D.gui.graphic.SpriteRender;
import EasyClip2D.geom.SpritePoint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import EasyClip2D.Box2D.org.jbox2d.dynamics.Body;
import EasyClip2D.util.Tools;

/**
 * 
 * The SpriteRender is used for controlling and storing
 * sprite images.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class SpriteImage implements Serializable {

    private SpriteRender render;
    
    private transient BufferedImage[] aframes;
    
    private transient BufferedImage image;
    
    private int spriteFrame;
    
    private float angle;
    
    public SpriteImage() {
        
        render = new SpriteRender();
        
        spriteFrame = 0;
        
    }
    
    /**
     * More formally sets the array of sprite blocks to null.
     */
    public void unloadSpriteImages() {
        aframes = null;
    }
    /**
     * Returns the current sprite frame number
     */
    public int getSpriteFrameNumber() {
        return spriteFrame;
    }
    /**
     * Sets the sprite's frame 0 to n - 1
     */
    public void setSpriteFrameNumber(int spriteFrame) {
        this.spriteFrame = spriteFrame;
    }
    /**
     * Returns the current image loaded into the Bitmap object.
     */
    public BufferedImage getImage() {
        return image;
    }
    /**
     * This method is no longer used.
     * @deprecated 
     */
    public void setBitmapFrame(BufferedImage image) {
        
    }
    /**
     * Sets the Bitmap's image as a BufferedImage
     */
    public void setImage(BufferedImage image){
        this.image = image;
    }
    /**
     * Method should only be used by the Sprite object, however this
     * draws the current sprite by specifying the current frame and passing
     * a Graphics2D device along with the position of the sprite.
     */
    public void drawSprite(int currentFrame, Graphics2D g, SpritePoint p, 
            SpritePoint anchor, boolean renderToViewPort){
        
        render.drawImage(g, p, anchor, angle, aframes[currentFrame], renderToViewPort);
        
    }
    
    public void drawSprite(int currentFrame, Graphics2D g, Body body, 
            SpritePoint p, SpritePoint anchor, boolean renderToViewPort){
        
        render.drawImage(g, p, anchor, body.getAngle(), aframes[currentFrame], renderToViewPort);
        
    }
    /**
     * Returns the BufferedImage stored in this object.
     * @deprecated
     */
    public BufferedImage getBitmapImage(){
        return null;
    }
    /**
     * Gets the graphics renderer for this sprite image
     */
    public SpriteRender getGraphicsRender(){
        return render;
    }
    /**
     * Method should only be used by the Sprite object, performs
     * simular to drawSprite but only draws the image stored in this object.
     */
    public void drawImage(Graphics2D g, SpritePoint p, SpritePoint anchor, 
                   boolean renderToViewPort) {
        
        render.drawImage(g, p, anchor, angle, image, renderToViewPort);
    }
    
    public void drawImage(Graphics2D g, Body body, SpritePoint p, 
                SpritePoint anchor, boolean renderToViewPort) {
        
        render.drawImage(g, p, anchor, body.getAngle(), image, renderToViewPort);
    }

    /**
     * Returns all the sprite blocks stored
     */
    public void setSpriteBlocks(BufferedImage[] frames){
        aframes = frames;
    }
    
    /**
     * Returns a sprite block using an index called frameNumber
     */
    public BufferedImage getSpriteBlock(int frameNumber){
        if (aframes != null)
            return aframes[frameNumber];
        else
            return null;
    }

    /**
     * Sets the current angle of the image measured in radians.
     */
    public void setAngle(float radians) {
        this.angle = radians;
    }
    /**
     * Returns the angle in degrees.
     */
    public float getAngleDegrees() {
        return Tools.convertToDegrees(getAngleRadians());
    }
    
    /**
     * Returns the angle in degrees.
     */
    public float getAngleRadians() {
        return angle;
    }
    /**
     * Returns the number of sprite blocks
     */
    public int getMaxFrameCount(){
        return aframes.length - 1;
    }

}
