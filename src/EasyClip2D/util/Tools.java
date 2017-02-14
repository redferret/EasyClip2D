
package EasyClip2D.util;

import EasyClip2D.Box2D.org.jbox2d.common.Vec2;
import EasyClip2D.Box2D.org.jbox2d.dynamics.World;
import EasyClip2D.collisions.SpriteCollisionList;
import EasyClip2D.gui.graphic.SpriteRender;
import EasyClip2D.core.ECEngine;
import EasyClip2D.core.ECEngine.Environment;
import EasyClip2D.geom.SpritePoint;
import EasyClip2D.gui.util.Units;
import EasyClip2D.io.Key;
import EasyClip2D.io.KeyCombo;
import EasyClip2D.io.KeyController;
import EasyClip2D.io.MouseButton;
import EasyClip2D.io.MouseController;
import EasyClip2D.io.MouseMovement;
import EasyClip2D.io.MouseWheel;
import EasyClip2D.util.listeners.Box2DCollisionListener;
import EasyClip2D.util.listeners.SpriteClickListener;
import EasyClip2D.sprite.Sprite;
import EasyClip2D.sprite.util.SpriteGrid;
import EasyClip2D.sprite.SpriteObject;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

/**
 * The Tools class is used to simplify coding and make life
 * easier for the programmer when programming games.
 * Most of these tasks can be hand-written but this is
 * only recommended if you fully know how Easy Clip works.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class Tools {
    
    /**
     * This is a pre-registered pointer to your game engine
     */
    public static ECEngine ec_engine;
    
    /**
     * When adding sprites with collisions you must tell what kind of colliding
     * sprite it is.
     */
    public static final int TYPE_COLLIDER = 0x10b, TYPE_COLLIDEE = 0x11b, 
            TYPE_COLLIDE_ALL = 0x12b, NONE = 0x00b;
    
    public static final int TYPE_DEFAULT = 0x0, TYPE_BACKGROUND = 0x1, 
            TYPE_STATIC = 0x2, TYPE_GRID = 0x3;
    
    public static final int TOP_SCREEN = 1, LEFT_SCREEN = 2, BOTTOM_SCREEN = 3, 
            RIGHT_SCREEN = 4, NO_SCREEN = 0;
    
    
    
    /**
     * When you are using the mouse for input, you must call this
     * method to setup the engine's mouse controller.
     */
    public static void initMouseController(){
        
        ec_engine.registerMouseButtonController(new MouseController());
        
    }
    
    /**
     * Adds a mouse button to the controller
     */
    public static void addMouseButton(MouseButton mb){
        
        if (ec_engine.getMouseButtonController() == null){
            
            throw new NullPointerException("Mouse Controller is null");
            
        }
        
        ec_engine.getMouseButtonController().addMouseButton(mb);
        
    }
    
    /**
     * Adds mouse movement to the controller
     */
    public static void addMouseMovement(MouseMovement mm){
        
        if (ec_engine.getMouseButtonController() == null){
            
            throw new NullPointerException("Mouse Controller is null");
            
        }
        
        ec_engine.getMouseButtonController().addMouseMovement(mm);
        
    }
    
    /**
     * Adds a mouse wheel listener to your game engine.
     */
    public static void addMouseWheel(MouseWheel mw){
        
        if (ec_engine.getMouseButtonController() == null){
            
            throw new NullPointerException("Mouse Controller is null");
            
        }
        
        ec_engine.getMouseButtonController().addMouseWheel(mw);
        
    }
    
    /**
     * If you are using the keyboard for input, call this method
     * to setup the controller for the keyboard.
     */
    public static void initKeyBoardController(){
        
        ec_engine.registerKeyController(new KeyController());
        
    }
    
    /**
     * Adds a key to the keyboard controller object.
     */
    public static void addKey(Key k){
        
        if (ec_engine.getKeyController() == null){
            
            throw new NullPointerException("Keyboard Controller is null");
            
        }
        
        ec_engine.getKeyController().addKey(k);
        
    }
    
    /**
     * Adds a combo to the keyboard controller.
     */
    public static void addCombo(KeyCombo combo){
        
        if (ec_engine.getKeyController() == null){
            
            throw new NullPointerException("Keyboard Controller is null");
            
        }
        
        ec_engine.getKeyController().addCombo(combo);
        
    }
    
    /**
     * Same as <code>Environment.initEnvironment(r, x_grav, y_grav, sleep)</code> 
     * 
     */
    public static void initPhysicsEnvironment(float r, float x_grav, float y_grav, boolean sleep){
        
        Environment.initEnvironment(r, x_grav, y_grav, sleep);
        
    }
    
    public static World getWorld(){
        
        return Environment.getWorld();
        
    }
    
    /**
     * When you are dealing with collisions on Box2D, you need to call this
     * method after initializing the Box2D world.
     */
    public static void initBox2DCollisions(){
        
        new Box2DCollisionListener();
        
    }
    
    /**
     * Stops rendering alpha color bits,
     * this will cause images to no longer have transparent colors.
     */
    public static void setImageRenderType(int type){
        
        ec_engine.getWindow().getPanelFrame().setImageType(type);
        
    }
    
    /**
     * This method is used for static methods in your game 
     * engine class, since static methods can't access
     * non-static variables, this method can be used.
     */
    public static void killSprite(Sprite s){
        
        ec_engine.kill(s);
        
    }
    
    /**
     * This method is used for static methods in your game 
     * engine class, since static methods can't access
     * non-static variables, this method can be used.
     */
    public static void killAllSprites(){
        
        ec_engine.killAll();
        
    }

    /**
     * Sets up the controller that listens for click events 
     * on a sprite object.
     */
    public static void initClickableSprites() {
        
        ec_engine.registerClickableSprites(new SpriteClickListener());
        
    }

    /**
     * Initializes the hover listener for hovered sprites
     */
    public static void initHoverListener() {
        
        ec_engine.getMouseButtonController().addHoveredListener();
        
    }
    
    /**
     * Changes whether the current hovered listener is enabled or not
     */
    public static void enableHovered(boolean b){
        
        ec_engine.getMouseButtonController().getHoveredListener().enable(b);
        
    }
    
    /**
     * Registers a render list with the number of layers that will be
     * involved.
     */
    public static void registerRenderList(int list, int layers){
        
        ec_engine.getRenderList(list).registerRenderOrder(layers);
        
    }
    
    /**
     * Converts degrees into radians and returns a double as radians
     * @param degrees a double as degrees
     * @return radians
     */
    public static float convertToRadians(float degrees) {

        return (float) (degrees * (Math.PI / 180.0));

    }
    /**
     * Converts radians into degrees and returns a double as degrees
     * @param radians a double as radians
     * @return degrees
     */
    public static float convertToDegrees(float radians){
        
        return (float) (radians * (180.0/Math.PI));
        
    }
    
    /**
     * Returns the graphics render object from the sprite passed
     * into the method.
     */
    public static SpriteRender getGraphicsRenderFrom(SpriteObject sprite){
        
        return sprite.getSpriteImage().getGraphicsRender();
        
    }
    
    /**
     * Registers a collision list with the game engine
     */
    public static void registerCollisionList(SpriteCollisionList list){
        
        ec_engine.getSpriteCollisionLists().addList(list);
        
    }
    
    /**
     * Registers a collision list with the game engine
     */
    public static SpriteCollisionList createCollisionList(int setValue){
        
        SpriteCollisionList list = new SpriteCollisionList(setValue);
        
        registerCollisionList(list);
        
        return list;
        
    }
    
    /**
     * Scales a vector using the scaling value. The target vector
     * is the source and returns a new vector. This is used to convert
     * pixel measurements into meters for Box2D, the scale values are
     * based on the number of pixels per meter.
     */
    public static Vec2 scaleVectorToMeters(Vec2 target){
        
        target.set(target.x / Units.scale_value, 
                target.y / Units.scale_value);
        
        return target;
        
    }
    
    /**
     * Scales a vector using the scaling value. The target vector
     * is the source and returns a new vector. This is used to convert
     * meter measurements into pixels for Easy Clip, the scale values are
     * based on the number of pixels per meter.
     */
    public static Vec2 scaleVectorToPixels(Vec2 target){
        
        target.set(target.x * Units.scale_value, 
                target.y * Units.scale_value);
        
        return target;
        
    }
    
    /**
     * Returns the position of the mouse in the view port
     */
    public static SpritePoint getScaledMouse(MouseEvent e){
        
        return ECEngine.port.getScaledMouse(e);
        
    }
    
    /**
     * Returns the mouses position based off the screen's
     * center.
     */
    public static SpritePoint getTranslatedMouse(MouseEvent e){
        
        return ECEngine.port.getTranslatedMouse(e);
        
    }

    /**
     * Returns the position of a point based off the center of the screen
     * rather than from the origin.
     */
    public static Vec2 fromScreenCenter(Vec2 vec2) {
        
        vec2.set(vec2.x - ECEngine.port.getRadial().x, 
                vec2.y - ECEngine.port.getRadial().y);
        
        return vec2;
        
    }
    
    /**
     * Returns the position of a point based off the center of the screen
     * rather than from the origin.
     */
    public static SpritePoint fromScreenCenter(SpritePoint pos) {
        
        pos.translate(-ECEngine.port.getRadial().x, -ECEngine.port.getRadial().y);
        
        return pos;
        
    }

    /**
     * Returns the position of a sprite or mouse in normal coordinates.
     * For instance if you have a point at the origin (0, 0), the screen
     * position would be half the width and height of the screen.
     */
    public static SpritePoint getWindowPosition(SpritePoint position) {

        SpritePoint pos = SpritePoint.copyOf(position);
        
        // From the origin
        pos.translate(ECEngine.port.getRadial().x + (Tools.ec_engine.getWindow().getPanelFrame().getWidth()) / 2, 
                ECEngine.port.getRadial().y + (Tools.ec_engine.getWindow().getPanelFrame().getHeight()) / 2);
        
        return pos;
        
    }
    
    /**
     * Sets the current graphics device to the viewport of the currently
     * used game engine.
     */
    public static void setGraphicsToViewPort(Graphics2D g2d){
            
        SpritePoint scaler = ECEngine.port.getScaleValue();
        
        // Translate to the origin
        g2d.translate((Tools.ec_engine.getWindow().getPanelFrame().getWidth()) / 2, 
                (Tools.ec_engine.getWindow().getPanelFrame().getHeight()) / 2);

        g2d.scale(scaler.x, scaler.y);
        
    }
    
    /**
     * Returns the position of an object as a grid cell position.
     * This is not the same as the location within the grid but the
     * cells position on the screen. The cells location is based
     * on the grid. For instance if you have a 10x10 grid and you
     * want the cell at 3x3, the location is x = 3, y = 3, the position
     * is the position of the cell on the screen. There are no negative
     * position values for a grid.
     */
    public static SpritePoint getGridCellPosition(SpritePoint pos){
        
        SpritePoint cellLocal = SpritePoint.copyOf(ECEngine.port.getRadial());
        
        cellLocal.negate();
        
        cellLocal.translate(pos.x, pos.y);
        
        return cellLocal;
        
    }
    
    /**
     * Returns the sprite grid that is registered in a collision list
     * using the list's set value. The method will return null if there is
     * no grid.
     */
    public static SpriteGrid getSpriteGridFrom(int setValue){
     
        int id = Tools.ec_engine.getCollisionListID(setValue);
        
        return Tools.ec_engine.getSpriteCollisionLists().getList().get(id).getGrid();
        
    }
        
    /**
     * If your background sprites tend to sit and do nothing and you wish
     * to free up some processing time you can cache an image
     * of these sprites as long as they are not moving, otherwise
     * you will end up with unwanted results.
     */
    public static void cacheBackgroundSprites(){
        
        Tools.ec_engine.getWindow().getPanelFrame().cacheBackground(true);
        
    }
    /**
     * If your static sprites tend to sit and do nothing and you wish
     * to free up some processing time you can cache an image
     * of these sprites as long as they are not moving, otherwise
     * you will end up with unwanted results.
     */
    public static void cacheStaticSprites(){
        
        Tools.ec_engine.getWindow().getPanelFrame().cacheFrontground(true);
        
    }
    
    /**
     * If your default sprites tend to sit and do nothing and you wish
     * to free up some processing time you can cache an image
     * of these sprites as long as they are not moving, otherwise
     * you will end up with unwanted results.
     */
    public static void cacheDefaultSprites(){
        
        Tools.ec_engine.getWindow().getPanelFrame().cacheCenter(true);
        
    }
    
    /**
     * Once you are finished with the current cached image of your
     * background and/or static sprites and they need to be updated,
     * then you can free the cache and the re-cache them again.
     */
    public static void freeGraphicsCache(){
        
        Tools.ec_engine.getWindow().getPanelFrame().refreshBackground();
        
        Tools.ec_engine.getWindow().getPanelFrame().refreshCenter();
        
        Tools.ec_engine.getWindow().getPanelFrame().refreshFrontground();
        
    }
    
    public static void disableGraphicsCache(){
        
        Tools.ec_engine.getWindow().getPanelFrame().cacheBackground(false);
        
        Tools.ec_engine.getWindow().getPanelFrame().cacheCenter(false);
        
        Tools.ec_engine.getWindow().getPanelFrame().cacheFrontground(false);
        
    }

    public static void nullifyGrid(int setValue) {
        
        Tools.ec_engine.getSpriteCollisionLists().getList().get(Tools.ec_engine.getCollisionListID(setValue)).setSpriteGrid(null);
        
    }

    public static void resizePanelToFrame(boolean b) {
        
        Tools.ec_engine.getWindow().getPanelFrame().resizeToFrame(b);
        
    }

    public static void exitOnWindowClose(boolean bool) {
        
        if (bool){
            
            Tools.ec_engine.getWindow().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
        }else{
            
            Tools.ec_engine.getWindow().setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            
        }
        
    }
    
    /**
     * Obtaining the correct position for the view port 
     * can be tricky but you have to remember that Sprite
     * objects are special in the case that you need to obtain
     * a scaled mouse position and then you need to transform
     * the position to the view port.
     * This method performs this task for you.
     */
    public static SpritePoint getPosition(MouseEvent e){
        
        SpritePoint pos = Tools.getScaledMouse(e);
            
        ECEngine.port.transformToViewPort(pos);
        
        return pos;
        
    }
    
    
}