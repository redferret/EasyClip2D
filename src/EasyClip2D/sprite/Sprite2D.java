
package EasyClip2D.sprite;

import EasyClip2D.Box2D.org.jbox2d.common.Vec2;
import EasyClip2D.gui.graphic.image.SpriteImage;
import EasyClip2D.core.ECEngine;
import EasyClip2D.gui.graphic.image.ECImage;
import EasyClip2D.util.Delay;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import EasyClip2D.Box2D.org.jbox2d.dynamics.Body;
import EasyClip2D.geom.SpritePoint;
import EasyClip2D.gui.util.Units;
import EasyClip2D.util.Sprite2DDef;
import EasyClip2D.util.Tools;


/**
 * The Sprite2D object is used when Box2D is being used
 * within your games. Sprite2D objects are only used
 * with Box2D but Sprite objects can still also be used
 * in your games as well. The structure of both are
 * similar.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public abstract class Sprite2D extends SpriteObject {

    protected Body body;
    
    protected Sprite2DDef def_list;

    private Units sp;
    
    /**
     * Constructs the Sprite2D with a definition collisionList
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Sprite2D(Sprite2DDef list) {
        
        this(list, true);
        
    }
    
    /**
     * Constructs the Sprite2D with a definition collisionList
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Sprite2D(Sprite2DDef list, boolean renderToViewPort) {
        
        super();
    
        def_list = list;
        
        this.renderToViewPort = renderToViewPort;
        
        if (renderToViewPort && spriteType == Tools.TYPE_GRID)
            
            ECEngine.port.transformToViewPort(pos);
        
        initSprite();
        
        sp = new Units(list.getPos().x, list.getPos().y);
        
        list.getBodyDef().position = sp.getScaledPosition();
 
        spriteSetup();
        
    }
    
    private void initSprite(){
     
        spriteImage = new SpriteImage();

        checkBounds = renderToViewPort = true;

        animate = hasImage = isDead = renderOffScreen = false;
        
        renderOrder = -1;
        
        render = true;
        
        spriteType = Tools.TYPE_STATIC;
        
        anchor = new SpritePoint();
        
    }

    
    @Deprecated
    public static void defineEngine(ECEngine ge){
        
    }
    
    public void rotate(float degrees){
        
        getBody().getTransform().set(getBody().getPosition(), Tools.convertToRadians(degrees));
        
    }
    
    /**
     * Copies the rotation of s1 to s2, the rotate point and angle
     * of s1 will be used for s2.
     */
    public static void rotateCenter(Sprite2D sprite){
        
        sprite.getSpriteImage().getGraphicsRender().
                    getRotatePoint().setLocation(sprite.getPosition().x, 
                    sprite.getPosition().y);

    }
    
    /**
     * Returns the center point of this sprite as a sprite point.
     * @return the center of this sprite.
     */
    public Vec2 getWorldCenter(){
        
        return getBody().getWorldCenter();
        
    }
    
    /**
     * Constant update method. Animate a sprite by a specific speed and 
     * determine if the sprite loops.
     */
    public static void animate(Sprite2D sprite, int speed, boolean loop){
        
        int curFrame = sprite.getCurrentFrame();
        int maxFrames = sprite.getSpriteImage().getMaxFrameCount();
        
        sprite.setCurrentFrame(curFrame);
        
        if (sprite.animationDelay.delay(speed) == 1){
            
            curFrame++;
            
            if (!loop && (curFrame > maxFrames)){
                
                sprite.delete();
                
                return;
                
            }else if (loop && (curFrame > maxFrames)){
                
                curFrame = 0;
                
            }
            
            sprite.setCurrentFrame(curFrame);
            
        }
        
    }
    
    /**
     * Initialize the animation delay to animate this sprite.
     */
    public void initAnimationDelay(){
        animationDelay = new Delay();
    }
    /**
     * Loads a single spriteImage into the sprite object, this method
     */
    public final void loadImage(BufferedImage img) {

        getSpriteImage().setImage(ECImage.toCompatibleImage(img));
        
        hasImage = true;
    }

    /**
     * Loads an spriteImage and splits it into an array of BufferedImages
     */
    public final void loadAnimation(BufferedImage img, int c, int r) {
        
        getSpriteImage().setImage(ECImage.toCompatibleImage(img));
        
        getSpriteImage().setSpriteBlocks(ECImage.splitImage(getSpriteImage().getImage(), c, r));
        
        animate = hasImage = true;
    }

    public final void update() {

        try{
            
            SpritePoint pos = Tools.getWindowPosition(getPosition());
            
            spriteUpdate(pos.x, pos.y);
        
        }finally{}
        
    }
    
    /**
     * used by the ECEngine
     */
    public final void draw(Graphics2D g2d) {
        
        if (animate){
            
            getSpriteImage().drawSprite(getCurrentFrame(), g2d, getBody(), 
                    getPosition(), anchor, renderToViewPort);

        }else {

            getSpriteImage().drawImage(g2d, getBody(), getPosition(), 
                    anchor, renderToViewPort);

        }

        try {

            additionalGraphics(g2d);

        } finally {

            return;

        }
        
    }
    
    /**
     * Sets the body for this sprite
     */
    public void setBody(Body body){
        
        this.body = body;
        
        this.body.setUserData(this);
        
    }
    
    /**
     * Returns the body for this sprite, usually
     * the body is already created by easy clip 
     * and this method is best to obtain the correct instance
     * of it.
     */
    public Body getBody(){
        return body;
    }
    
    public Sprite2DDef getDefList(){
        
        return def_list;
        
    }

    /**
     * Returns the position of the sprite's world center.
     */
    public SpritePoint getPosition() {

        sp.setScaledLocation(body.getPosition());
        
        return sp.getPosition();
        
    }
    
    /**
     * When two sprites are no longer colliding this method 
     * is called.
     */
    public abstract void collisionEnded(Sprite2D fixB);

    
    
}

