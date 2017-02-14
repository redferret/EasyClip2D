
package EasyClip2D.sprite;

import EasyClip2D.gui.graphic.image.*;
import EasyClip2D.geom.*;
import EasyClip2D.core.ECEngine;
import EasyClip2D.collisions.*;
import EasyClip2D.util.Delay;
import EasyClip2D.util.Tools;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


/**
 * The Sprite object is the main object used in your games.
 * The Sprite object however is not used with Box2D. If
 * you want to use Box2D correctly then Sprite2D will be
 * better to use.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public abstract class Sprite extends SpriteObject {
    
    private Collisions collision_type;
    
    private SpriteBound collisionBound;
    
    private float rotationSpeed,  maxRotation,  minRotation, PCR;
   
    private boolean maxRotationApplied;
    
    /**
     * Default constructor
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Sprite() {
        
        this(0, 0, true);
        
    }
    /**
     * Constructs the sprite at the x and y position
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Sprite(float x, float y) {
        
        this(x, y, true);
        
    }
    
    /**
     * Constructs the sprite at the x and y position
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Sprite(float x, float y, boolean renderToViewPort) {
        
        super();
        
        pos = new SpritePoint(x, y);
        
        this.renderToViewPort = renderToViewPort;
        
        initSprite();

        spriteSetup();
        
        ECEngine.port.transformToViewPort(pos);
        
        if (renderToViewPort){
            
            SpritePoint rPos = ECEngine.port.getRadial();

            pos.translate(rPos.x, rPos.y);
        
        }
    }
    
    public final void initSprite(){
        
        spriteImage = new SpriteImage();
        
        center = new SpritePoint();
        
        rotationSpeed = minRotation = PCR = 0.f;
        
        velocity = new Vec2d();
        
        maxRotation = DEFAULT_MAX_ROTATION;
        
        checkBounds = true;
        
        spriteType = Tools.TYPE_DEFAULT;
        
        maxRotationApplied = animate = hasImage = isDead = renderOffScreen = false;
        
        renderOrder = -1;
        
        render = true;
        
        anchor = new SpritePoint();
        
    }
    /**
     * A mouse event is passed to check if the mouses position
     * is within bounds of this sprite.
     * @param e Mouse Event
     * @return if the mouses pos is within bounds of this sprite
     */
    public static boolean checkMouseBounds(SpriteObject s, MouseEvent e){
        
        SpriteRect rect = new SpriteRect();
        
        SpritePoint rPos = ECEngine.port.getRadial();
        
        rect.setRect(s.getPosition().x + s.anchor.x, s.getPosition().y + s.anchor.y, s.getWidth(), s.getHeight());
        
        return (rect.contains(e.getX() - rPos.x, e.getY() - rPos.y));
        
    }

    /**
     * Move tSprite in the direction it is facing.
     */
    public void move(float speed){
        
        setXVelocity(getSpriteImage().getGraphicsRender().getDirectionVector().getXi() * speed);
        
        setYVelocity(getSpriteImage().getGraphicsRender().getDirectionVector().getYj() * speed);
        
    }
    /**
     * Set the speed of the object by passing the speed of the X and Y or
     * the change in X and Y.
     */
    public void move(float xspeed, float yspeed){
        
        setXVelocity(xspeed);
        
        setYVelocity(yspeed);
        
    }
    
    /**
     * Rotate tSprite to an angle in degrees. Theta bounded at (0 < degrees < 360)
     */
    public void rotate(float degrees){
        
        getSpriteImage().setAngle(Tools.convertToRadians(degrees));
        
    }
    
    /**
     * Constant update method. 
     * Sets the rotation point to the center of this sprite.
     */
    public static void rotateCenter(Sprite sprite){
        
        sprite.getSpriteImage().getGraphicsRender().setRotatePoint(sprite.getCenter());
        
    }
    /**
     * Constant update method. 
     * Set the rotation point of this sprite.
     */
    public static void rotatePoint(Sprite sprite, SpritePoint point){
        
        sprite.getSpriteImage().getGraphicsRender().setRotatePoint(point);
        
    }
    
    /**
     * Constant update method. Scans between the minimum and maximum rotation
     * at a speed of radians/frame.
     */
    public static void scan(Sprite tSprite, float radians){
        
        if (tSprite.getSpriteImage().getAngleRadians() >= tSprite.getMaxRotation()){
            
            tSprite.setRotationSpeed(-radians);
            
        }else if (tSprite.getSpriteImage().getAngleRadians() <= tSprite.getMinRotation()){
            
            tSprite.setRotationSpeed(radians);
            
        }
        
    }
    
    @Deprecated
    public static void defineEngine(ECEngine ge){
        
    }
    
    /**
     * Copies the rotation of s1 to s2, the rotate point and angle
     * of s1 will be used for s2.
     * @param s1 a sprite
     * @param s2 the sprite to copy rotation
     */
    public static void copyRotation(Sprite s1, Sprite s2){
        s2.getSpriteImage().getGraphicsRender().setRotatePoint(s1.getSpriteImage().getGraphicsRender().getRotatePoint());
        s2.getSpriteImage().setAngle(s1.getSpriteImage().getAngleRadians());
    }
    
    /**
     * Constant update method. Animate a sprite by a specific speed and 
     * determine if the sprite loops.
     * @param sprite the sprite to animate
     * @param speed the speed of the animation
     * @param loop if the animation should be looped.
     */
    public static void animate(Sprite sprite, int speed, boolean loop){
        
        int curFrame = sprite.getCurrentFrame();
        int maxFrames = sprite.getSpriteImage().getMaxFrameCount();
        
        sprite.setCurrentFrame(curFrame);
        
        if (sprite.animationDelay.delay(speed) == 1){
            
            curFrame++;
            
            if (!loop && (curFrame > maxFrames)){
                
                killSprite(sprite);
                
                return;
                
            }else if (loop && (curFrame > maxFrames)){
                
                curFrame = 0;
                
            }
            
            sprite.setCurrentFrame(curFrame);
            
        }
        
    }
    
    /**
     * Constant update method. Clip the position of the sprite 
     * when the mouse's position falls out
     * of bounds with the boundaries specified by the <Code> bounds </code>
     * parameter. This method is not best used along with other Sprite
     * methods of movement.
     */
    public static void clipSprite(MouseEvent e, Sprite obj, SpriteRect bounds){
        
        if (!obj.isDead){
        
            if (e.getX() < bounds.getX()){

                bounds.setRect(bounds.getX() - bounds.width, bounds.getY(), bounds.width, bounds.height);

            }else if (e.getX() > bounds.getWidth()){

                bounds.setRect(bounds.getX() + bounds.width, bounds.getY(), bounds.width, bounds.height);

            }

            if (e.getY() < bounds.getY()){

                bounds.setRect(bounds.getX(), bounds.getY() - bounds.height, bounds.width, bounds.height);

            }else if (e.getY() > bounds.getHeight()){

                bounds.setRect(bounds.getX(), bounds.getY() + bounds.height, bounds.width, bounds.height);

            }

            obj.setLocationTo((float)bounds.getCenterX(), (float)bounds.getCenterY());
        
        }
        
    }
    
    /**
     * Initialize the animation delay to animate this sprite.
     */
    public void initAnimationDelay(){
        
        animationDelay = new Delay();
        
        animationDelay.allowReset(true);
        
    }
    
    public static void killSprite(Sprite tSprite){
        
        if (Tools.ec_engine != null){
            
            Tools.ec_engine.kill(tSprite);
            
        }
    }
    
    /**
     * Loads a single spriteImage into the sprite object, this method
     * takes a BufferedImage object.
     */
    public final void loadImage(BufferedImage img) {
        
        getSpriteImage().setImage(ECImage.toCompatibleImage(img));
        
        hasImage = true;
    }

    /**
     * Loads an animation for this sprite
     */
    public final void loadAnimation(BufferedImage img, int c, int r) {
        
        getSpriteImage().setImage(ECImage.toCompatibleImage(img));
        
        getSpriteImage().setSpriteBlocks(ECImage.splitImage(getSpriteImage().getImage(), c, r));
        
        animate = hasImage = true;
    }

    /**
     * Update method for this sprite object
     */
    public final void update() {

        float theta = getSpriteImage().getAngleRadians();

        theta += getRotationSpeed();

        if (theta >= getMaxRotation()) {
            
            if (!maxRotationApplied) {
                
                theta = getMinRotation();
                
            } else {
                
                theta = getMaxRotation();
                
            }
            
        } else if (theta <= getMinRotation()) {
            
            if (!maxRotationApplied()) {
                
                theta = getMaxRotation();
                
            } else {
                
                theta = getMinRotation();
                
            }
            
        }

        getSpriteImage().setAngle(theta);
        
        if (getSpriteType() != Tools.TYPE_GRID){
            
            this.pos.translate(velocity.xi, velocity.yj);
            
        }
        
        try {
            
            center.setLocation(getPosition().x + getWidth() / 2.0f, getPosition().y + getHeight() / 2.0f);
            
        } catch (Exception e) {
            
            throw new RuntimeException("Image dimensions invalid, override getter methods getWidth(), getHeight()");
            
        }
        
        SpritePoint pos;
        
        if (renderToViewPort)
            
            pos = Tools.getWindowPosition(getPosition());
        
        else
            
            pos = getPosition();
        
        spriteUpdate(pos.x, pos.y);

    }

    /**
     * Used by the ECEngine
     */
    public final static boolean checkCollision(Sprite collider, Sprite collidee) {
        
        if (!collider.equals(collidee)) {

            if (collider.collision_type == null){
                throw new IllegalStateException("Sprite contains no collision algorithm");
            }
            
            return collider.collision_type.checkCollision(collider, collidee);
            
        }
        
        return false;
    }
    
    /**
     * Used by the ECEngine
     */
    public final void draw(Graphics2D g2d) {

        if (animate){

            getSpriteImage().drawSprite(getCurrentFrame(), g2d, getPosition(), anchor, renderToViewPort);

        }else {

            getSpriteImage().drawImage(g2d, getPosition(), anchor, renderToViewPort);

        }

        additionalGraphics(g2d);
        
    }
    /**
     * Sets the ECEngine to check the bounds of this object
     */
    public boolean checkBounds(){
        return checkBounds;
    }
    /**
     * Sets whether this object will have collision detection or not.
     */
    public void checkBounds(boolean checkBounds){
        this.checkBounds = checkBounds;
    }
    /**
     * 
     * @return the current velocity of the rotation in degrees
     */
    public double getRotationSpeed() {
        return Tools.convertToDegrees(rotationSpeed);
    }
    /**
     *
     * @param degrees sets the rotations speed
     */
    public void setRotationSpeed(float degrees) {
        
        degrees = Tools.convertToRadians(degrees);
        
        this.rotationSpeed = degrees;
    }
    /**
     *
     * @return the minimum rotation of the object in radians
     */
    public float getMinRotation() {
        return minRotation;
    }
    /**
     *
     * @return the maximum rotation of the object in radians
     */
    public float getMaxRotation() {
        return maxRotation;
    }
    /**
     * sets the min rotation of the object in degrees
     */
    public void setMinRotation(float minTheta) {
        this.minRotation = Tools.convertToRadians(minTheta);
    }
    /**
     * set the max rotation of the object in degrees
     */
    public void setMaxRotation(float maxTheta) {
        this.maxRotation = Tools.convertToRadians(maxTheta);
    }
    /**
     * Enables the object to oversee the min and max rotation limits
     * or to disable the limited rotation.
     */
    public void applyMaxRotation(boolean atMaxThetaRotate) {
        this.maxRotationApplied = atMaxThetaRotate;
    }
    /**
     *
     * @return whether the sprite object can rotate past its max and min
     * rotation.
     */
    public boolean maxRotationApplied() {
        return maxRotationApplied;
    }

    /**
     * Set the sprite's position to the point given
     */
    public void setLocationTo(float x, float y){
        setLocationTo(new SpritePoint(x, y));
    }
    
    /**
     * Sets the sprites collision bounds
     */
    public void setCollisionBounds(SpriteBound bound){
        if (spriteImage == null){
            throw new NullPointerException("No Image or Sprite is loaded yet");
        }
        this.collisionBound = bound;
    }
    /**
     * Returns the collision bounds
     */
    public SpriteBound getCollisionBounds(){
        return this.collisionBound;
    }
    /**
     * Sets the PCR of this sprite.
     */
    public void setPotentialCollisionRadius(float radius){
        PCR = radius;
    }
    /**
     * Returns the PCR of this sprite
     */
    public float getPotentialCollisionRadius(){
        return PCR;
    }
    
    public void addForce(Vec2d force){
        
        velocity = Vec2d.addVectors(velocity, force);
        
    }
    
    /**
     * Overrides whether this sprite has an spriteImage or not.
     * Used only when graphics are not loaded into this sprite.
     * Must be called when constructing this sprite object.
     */
    public void basicGraphics(boolean b){
        
        hasImage = b;
    
    }
    
    public Vec2d getVelocity(){
        
        return velocity;
        
    }
    
    public void setVelocity(Vec2d vec){
        
        velocity.setXi(vec.xi);
        
        velocity.setYj(vec.yj);
        
    }
    
    /**
     * Set the X speed of change in X
     * @param xSpeed 
     */
    public void setXVelocity(float xSpeed) {
        
        velocity.setXi(xSpeed);
        
    }
    /**
     * Set the Y speed of change in Y
     */
    public void setYVelocity(float ySpeed) {
        
        velocity.setYj(ySpeed);
        
    }
    

    /**
     * Set the collision algorithm for this object.
     */
    public void setCollisionType(Collisions c){
        
        this.collision_type = c;
        
    }
    
    /**
     * Get the collision algorithm for this object.
     */
    public Collisions getCollisionType(){
        
        return this.collision_type;
        
    }

    /**
     * Freezes the sprite's movement by setting all velocities to 0
     * including rotational velocity.
     */
    public void freeze() {
        
        velocity.zero();
        
        setRotationSpeed(0.0f);
        
    }

}

