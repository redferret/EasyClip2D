
package EasyClip2D.gui.graphic;

import EasyClip2D.core.ECEngine;
import EasyClip2D.geom.Vec2d;
import EasyClip2D.geom.SpritePoint;
import EasyClip2D.util.Tools;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.awt.RenderingHints;
/**
 * The Frame Object contains all the Paint events, a Graphics2D object
 * is passed into a Sprite, then the sprite determins what kind
 * of draw method it needs to perform and then sends it to the Bitmap
 * object which then performs a draw method here in the Frame object.
 * Special methods can be used in this class such as enabling rotation
 * of an image or sprite block and drawing a pivot point and obtaining
 * the Px2 and Py2 slopes.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class SpriteRender implements Serializable {

    private SpritePoint rotatePoint;
    
    private static boolean smoothGraphics = true;
    
    private boolean rotate;
    
    private Vec2d sv;

    private static final SpritePoint NO_ANCHOR = new SpritePoint();
    
    public SpriteRender() {
        
        rotatePoint = new SpritePoint();
        
        rotate = true;
        
        sv = new Vec2d();
        
    }
    
    /**
     * Sets the rendering to render high quality images
     */
    public static void useHighQuality(boolean b){
        
        smoothGraphics = b;
        
    }
    
    /**
     * Switches between high or low quality
     */
    public static void switchQuality(){
        
        smoothGraphics = !smoothGraphics;
        
    }
    
    /**
     * Set a point for which this object will rotate on.
     * @param p 
     */
    public void setRotatePoint(SpritePoint p){
        rotatePoint = p;
    }

    public SpritePoint getRotatePoint() {
        return rotatePoint;
    }

    /**
     * Returns the direction the sprite is currently facing
     */
    public Vec2d getDirectionVector(){
        return sv;
    }
    
    /**
     * Enable the object to rotate the image or sprite block
     * @param rotate 
     */
    public void rotateImage(boolean rotate){
        this.rotate = rotate;
    }
    
    /**
     * Renders an Image onto the screen
     * @param g2d the current graphics device
     * @param pos the position of the image
     * @param anchor the image's anchor point
     * @param theta the angle to render the image at
     * @param img the image being rendered
     * @param renderToViewPort Renders the image based off the position of the view port
     */
    public void drawImage(Graphics2D g2d, SpritePoint pos, SpritePoint anchor, 
            double theta, BufferedImage img, boolean renderToViewPort) {

        if (smoothGraphics)
            
            smoothGraphics(g2d);
            
        SpritePoint rPos = ECEngine.port.getRadial();
        
        if (renderToViewPort){
            
            Tools.setGraphicsToViewPort(g2d);
            
        }
        
        if (img != null){

            if (rotate){

                g2d.rotate(theta, rotatePoint.x + rPos.x, rotatePoint.y + rPos.y);
                
                sv.setXi((float)-Math.sin(theta));
                
                sv.setYj((float)Math.cos(theta));

            }

            if (anchor != null){
                
                drawFinalImage(g2d, img, pos, rPos, anchor);
                
            }else {
                
                drawFinalImage(g2d, img, pos, rPos, NO_ANCHOR);
                
            }
        }
        
        g2d.setTransform(new AffineTransform());

    }
    
    private void drawFinalImage(Graphics2D g2d, BufferedImage img, SpritePoint pos, SpritePoint rPos, SpritePoint anchor){
        
        g2d.drawImage(img, (int)(pos.x + anchor.x + rPos.x), (int)(pos.y + anchor.y + rPos.y), (int)(img.getWidth()), 
                            (int)(img.getHeight()), Tools.ec_engine.getWindow().getPanelFrame());
        
    }
    
    private void smoothGraphics(Graphics2D g2d){

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, 
                RenderingHints.VALUE_RENDER_QUALITY);
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        
    }

}
