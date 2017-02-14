

package EasyClip2D.core;

import EasyClip2D.util.thead.Cog;
import EasyClip2D.geom.SpritePoint;
import EasyClip2D.io.KeyboardInput;
import EasyClip2D.io.KeyController;
import EasyClip2D.io.MouseController;
import EasyClip2D.util.Text;
import EasyClip2D.util.listeners.SpriteClickListener;
import EasyClip2D.sprite.Sprite;
import EasyClip2D.sprite.Sprite2D;
import EasyClip2D.collisions.SpriteCollisionList;
import EasyClip2D.sprite.util.SpriteManager;
import EasyClip2D.sprite.util.SpriteManager.SpriteCollision;
import EasyClip2D.util.Tools;
import EasyClip2D.gui.GameFrame;
import java.awt.Color;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import EasyClip2D.Box2D.org.jbox2d.common.Vec2;
import EasyClip2D.Box2D.org.jbox2d.dynamics.Body;
import EasyClip2D.Box2D.org.jbox2d.dynamics.FixtureDef;
import EasyClip2D.Box2D.org.jbox2d.dynamics.World;
import EasyClip2D.gui.util.CoordinateSystem;
import EasyClip2D.gui.util.RenderList;
import EasyClip2D.gui.util.ViewPort;
import EasyClip2D.io.Typer;
import EasyClip2D.sprite.SpriteObject;
import EasyClip2D.util.Chain;
import EasyClip2D.util.Delay;
import EasyClip2D.util.GameEvent;
import EasyClip2D.util.SpriteList;
import EasyClip2D.util.listeners.ClickableEvent;
import EasyClip2D.util.thead.CogEvent;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;


/**
 * The Game Engine class. Used to implement the ECEngine
 * into your games.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public abstract class ECEngine {

    /**
     * The sprite manager contains all the sprites and performs
     * all the necessary updates and collision tests.
     * The physics engine is not apart of this object.
     */
    private SpriteManager ec_manager;
    
    /**
     * The current frame being used by the game engine.
     */
    private GameFrame frame;
    
    /**
     * The keyboard controller for the engine. This is not a necessary
     * object to have but it is good to use if the engine uses
     * the keyboard as well.
     */
    private KeyController ec_kController;
    
    /**
     * The mouse controller for the engine. This is a necessary object to
     * have due to the fact that the engine uses the mouse a lot.
     */
    private MouseController ec_mController;
    
    /**
     * The current object that is used to process sprites that can be clicked
     * by the user. Sprite2D objects are currently not apart of this.
     */
    private SpriteClickListener ec_clickable;
    
    /**
     * This Text object is used to display the FPS for all the major threads
     * in the game engine.
     */
    private static Text ec_display;
    
    /**
     * Locations used when the graphics panel is being dragged by
     * the user.
     */
    private SpritePoint debugMouse;
    
    private static Chain<Cog> cogs;
    
    /**
     * Reads input from the user and can display the input
     * on the screen as a text sprite.
     */
    private KeyboardInput ec_keys;
    
    /**
     * The collision lists used for sprites. Sprite2D objects are not
     * used by this object.
     */
    private static SpriteCollision ec_collisions;
    
    /**
     * The ViewPort for this engine.
     */
    public static final ViewPort port = new ViewPort(5000.0f, 5000.0f);

    private float mainThread, updateThread;
    
    private boolean running, exitOnEscape, displayFPS;
    /**
     * The done flag is used for the main game loop. If done is flagged as
     * true, then the engine will terminate the program.
     */
    public boolean done;
    
    /**
     * CollisionCycle determines the cycle speed for the Cog
     * that controls collision checking
     */
    public float collisionCycle;
    
    /**
     * Used to search and sort collision lists
     */
    private Comparator<SpriteCollisionList> comparator;
    
    /**
     * Controls event messages sent to the game engine
     * by other class files for the game. Best used as 
     * flags for the game engine.
     */
    private GameEvent events;
    
    /**
     * The text color for the debug
     */
    private Color debugColor;

    /**
     * Formats the debug
     */
    private DecimalFormat df;
    
    /**
     * The update delay for the debug
     */
    private Delay debug_delay;
    
    /**
     * Step flag for stepping the engine one frame
     */
    private boolean step;
    
    /**
     * The number of sprites currently active in the engine
     */
    private int spriteCount;
    
    /**
     * Main list for all sprites in the engine
     */
    private SpriteList spriteList[];
    
    /**
     * Main list for all sprites to be rendered.
     */
    private RenderList renderList[];
    
    /**
     * This is the physics engine used for your games. Accessing this
     * method will allow you to access the physics engine Box2D and initialize it.
     * You wont be able to use the physics engine if you don't have 
     * the libraries loaded into your game correctly but Easy Clip 
     * was implemented with Box2D where you don't need the binaries of Box2D.
     */
    public static class Environment {

        public static float time_step;
        
        public static int velocityIters, positionIters;
        
        private static World ec_world;
        
        /**
         * Initializes the physics engine for use. Not calling
         * this method will throw null pointer exceptions within your
         * game during runtime.
         */
        public static void initEnvironment(float timeStep, float grav_x, float grav_y, boolean sleep) {

            Vec2 vec2 = new Vec2(grav_x, grav_y);

            ec_world = new World(vec2, sleep);

            time_step = timeStep;

            velocityIters = 4;

            positionIters = 3;
            
        }

        /**
         * Returns the world object for use. This method may not work
         * if you do not have JBox2D as a stand alone library to your own.
         * @return 
         */
        public static World getWorld() {

            return ec_world;

        }

        private static void step() {

            ec_world.step(time_step, velocityIters, positionIters);

        }
    }
    
    /**
     * Default Constructor, performs nothing
     */
    public ECEngine(){
    
        this(1.0f, null);
        
    }
    
    /**
     * Sets up your engine, this does not run the engine. Getting
     * the engine to run is a separate operation.
     * @param fps The frames per second desired for the game.
     * @param frame The type of frame that will be used.
     */
    public ECEngine(float cycle, GameFrame frame) {
        
        Tools.ec_engine = this;
        
        updateThread = mainThread = cycle;
        
        ec_manager = new SpriteManager();
        
        this.frame = frame;
        
        this.frame.getPanelFrame().setSpriteManager(ec_manager);
        
        this.frame.initListeners(new KeyBoard(), new MouseListener(), 
                new MouseMovementListener(), new JWindowListener(), 
                new MouseWheelListener(), null);
        
        initVariables();
        
        loadThreads();
        
    }
    
    public Cog add(float cycle, CogEvent event){
        
        Cog cog = new Cog(event, cycle);
        
        cogs.offer(cog);
        
        return cog;
        
    }

    /**
     * Post a new game event to this game engine
     * @param eventID 
     */
    public void postGameEvent(int eventID){
        
        events.postEvent(eventID);
        
    }
    
    /**
     * By default the escape key terminates the game engine. 
     * Calling this method and passing false into the parameter
     * will no longer close the program when escape is pressed
     */
    public void exitOnEscapeKey(boolean s){
        exitOnEscape = s;
    }

    /**
     * Get the current window loaded in the game engine
     */
    public GameFrame getWindow(){
        return frame;
    }
    
    /**
     * Pauses the game engine
     */
    public void pause() {

        running = false;
        
        Iterator<Cog> iter = cogs.iterator();
        
        Cog cog;
        
        while(iter.hasNext()){
            
            cog = iter.next();
            
            if (cog.synch()) cog.disable();
            
        }
        
    }

    /**
     * Plays the game engine, this is not the same as the run() method.
     */
    public void play(){

        running = true;
        
        Iterator<Cog> iter = cogs.iterator();
        
        Cog cog;
        
        while(iter.hasNext()){
            
            cog = iter.next();
            
            if (cog.synch()) cog.enable();
            
        }
        
    }
    
    /**
     * Tells whether or not to show the FPS for each running thread
     * @param show whether or not to show the FPS for each running thread
     */
    public void debugVisible(boolean show){
        displayFPS = show;
    }
    
    /**
     * Set the text color for the FPS when drawn
     * @param color the text color for the FPS when drawn
     */
    public void setDebugColor(Color color){
        debugColor = color;
    }
    
    /**
     * Adds a keyboard input object to the engine.
     */
    public void add(KeyboardInput kb){
        
        ec_keys = kb;
        
        add(ec_keys);
        
    }
    
    /**
     * The collision list is returned. This is the best way to access
     * the list to add other collision lists.
     * @return The SpriteCollision list for this engine.
     */
    public SpriteCollision getSpriteCollisionLists(){

        return ec_collisions;
        
    }
    
    /**
     * Sets the SpriteClickListener object for this engine.
     */
    public void registerClickableSprites(SpriteClickListener c){
        this.ec_clickable = c;
    }
    
    /**
     * Returns the current clickable sprites in this engine.
     */
    public SpriteClickListener getClickableSprites(){
        return ec_clickable;
    }

    /**
     * Puts the engine into run mode. This method
     * must be called after your game is setup and initialized.
     */
    public void run(){

        if (getWindow() != null)
            
            getWindow().setVisible(true);
        
        play();
        
        renderWorld();
        
        try {
            
            preRun();
            
        } catch (java.lang.UnsatisfiedLinkError ule){
        }finally {
            
            do {
            
                try {

                    preRender();

                } catch (java.lang.UnsatisfiedLinkError ule){
                }finally {

                    renderWorld();

                }
                
                checkGameEvents();
                
                if (running){
                    
                    updatePhysics();
                    
                }else if (step){
                    
                    step();
                    
                    if (step){
                        
                        System.out.println("Frame Stepped");
                        
                        step = false;
                        
                    }
                    
                }
                
                gameLoop();
                
                checkPanelSize();
                
                if (displayFPS) {

                    showFPS();

                } else {

                    closeFPS();

                }
                
                Cog.cycle(mainThread);

            } while (!done);

            killAll();

            CoordinateSystem.drawOrigin(false);

            frame.dispose();
            
            System.exit(0);
            
        }
        
    }
    
    public void flagStep(){
        
        step = true;
        
    }
    
    public void step(){
        
        try {

            updatePhysics();

            stepCogs();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void stepCogs(){
        
        Iterator<Cog> iter = cogs.iterator();
        
        Cog cog;
        
        while(iter.hasNext()){
            
            cog = iter.next();
            
            if (cog.synch() && !cog.isRunning()) cog.manualStep();
            
        }
        
    }

    public void setCycleSpeed(float cycle){
        
        Iterator<Cog> iter = cogs.iterator();
        
        Cog cog;
        
        while(iter.hasNext()){
            
            cog = iter.next();
            
            cog.setCycle(cycle);
            
        }
        
        mainThread = cycle;
        
    }

    /**
     * If the game is paused the method will return true.
     * @return is the game engine paused
     */
    public boolean isPaused() {
        
        if (running) return false;
        
        else return true;
        
    }

    /**
     * Adds a Sprite2D object to the game engine. Once the sprite
     * is added then Box2D will start calculating the physics on 
     * this sprite.
     */
    public boolean add(Sprite2D s2d){
        
        try {
            
            if (!Environment.getWorld().isLocked()){
            
                Body body = Environment.getWorld().createBody(s2d.getDefList().getBodyDef());
                
                body.setUserData(s2d);

                FixtureDef[] def_list = s2d.getDefList().getFixtureDefList().getDefintions();

                for (FixtureDef def : def_list){
                    
                    body.createFixture(def);
                    
                }

                s2d.setBody(body);
                
                spriteList[s2d.getSpriteType()].addSprite(s2d);
            
                renderList[s2d.getSpriteType()].add(s2d);
                
                spriteCount++;
                
            }
            
        } catch (Exception exception) {
            
            kill(s2d);
            
            return false;
            
        }
        
        try {
            
            if (s2d instanceof ClickableEvent){
                    
                getClickableSprites().addClickable(s2d);

            }
            
            spriteAdded(s2d);
            
        } finally {
            
            return true;
            
        }
        
    }
    
    /**
     * Adds a sprite as a background sprite. All sprites are painted above
     * these sprites and no collisions are checked with these sprites.
     * This method does not except default sprite types.
     * @param s a sprite to be a background sprite
     */
    public boolean add(Sprite s){
        
        return add(s, 0);
        
    }
    
    /**
     * Adds a sprite as a background sprite. All sprites are painted above
     * these sprites and no collisions are checked with these sprites.
     * This method does not except default sprite types.
     * @param s a sprite to be a background sprite
     */
    public boolean add(Sprite s, int setValue){
        
        return add(s, setValue, 1, 1);
        
    }
    
    /**
     * Adds a sprite by specifying the colliding type.
     * @param s The sprite being added.
     * @param list The sprite collision list being used.
     * @param collisionID The type of collision the sprite will be. 
     * These values can be found in the ECEngine.
     */
    public boolean add(Sprite s, int setValue, int collisionID){
        
        return add(s, setValue, collisionID, false);
        
    }
    
    /**
     * Performs the same operation when adding a sprite to the game engine
     * that has collisions applied to it. By passing a true value into the
     * gridCollider parameter, you specify that this sprite will collide
     * with any grid sprites applied to the collision list.
     * @param s The sprite being added.
     * @param list The sprite collision list being used.
     * @param collisionID The type of collision the sprite will be. 
     * These values can be found in the ECEngine.
     * @param gridCollider If the sprite also collides with grid sprites.
     */
    public boolean add(Sprite s, int setValue, int collisionID, boolean gridCollider){
        
        try {
            
            int id = getCollisionListID(setValue);
            
            if (id < 0){
                
                return false;
                
            }
            
            if (s.getSpriteType() == Tools.TYPE_DEFAULT){
                
                if (s instanceof ClickableEvent){
                    
                    getClickableSprites().addClickable(s);
                    
                }
                
                switch (collisionID){
                    
                    case Tools.TYPE_COLLIDER:
                        ec_collisions.getList().get(id).colliders().add(s);
                        break;
                    case Tools.TYPE_COLLIDEE:
                        ec_collisions.getList().get(id).collidees().add(s);
                        break;
                    case Tools.TYPE_COLLIDE_ALL:
                        ec_collisions.getList().get(id).colliders().add(s);
                        ec_collisions.getList().get(id).collidees().add(s);
                        break;
                    case Tools.NONE:
                        break;
                    default:
                        throw new Exception("Invalid Colliding Type");
                }
                
                if (gridCollider){
                    ec_collisions.getList().get(id).gridColliders().add(s);
                }
                
                spriteList[s.getSpriteType()].addSprite(s);
            
                renderList[s.getSpriteType()].add(s);
                
                try {
                    
                    spriteAdded(s);
                    
                } finally {
                    
                    spriteCount++;
                    
                    return true;
                    
                }
                
            }else {
                throw new Exception("Sprite Type Invalid");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
        
    }
    
    /**
     * Adds a grid sprite to a grid in a collision list.
     * The width and height variables are used to determine how
     * much of the grid this sprite takes up.
     * 
     * If a sprite appears to take 2x2 blocks, then the width and
     * height would be 2.
     */
    public boolean add(Sprite s, int setValue, int width, int height){
       
        try {
            
            if (s.getSpriteType() == Tools.TYPE_BACKGROUND
                    || s.getSpriteType() == Tools.TYPE_STATIC){

                if (s instanceof ClickableEvent){
                    
                    getClickableSprites().addClickable(s);
                    
                    
                    
                }
                
                spriteList[s.getSpriteType()].addSprite(s);
            
                renderList[s.getSpriteType()].add(s);
                
                try {
                    
                    spriteAdded(s);
                    
                } catch (java.lang.UnsatisfiedLinkError ule) {  
                }finally{
                    
                    spriteCount++;
                    
                    return true;
                    
                }
                
            }else if (s.getSpriteType() == Tools.TYPE_GRID) {
                
                int id = getCollisionListID(setValue);
            
                if (id < 0){

                    return false;

                }
                
                if (ec_collisions.getList().get(id).getGrid().addSprite(s, width, height)){
                    
                    spriteList[Tools.TYPE_DEFAULT].addSprite(s);
            
                    renderList[Tools.TYPE_DEFAULT].add(s);
                    
                    try {
                    
                        spriteAdded(s);

                    } catch (java.lang.UnsatisfiedLinkError ule) {  
                    }finally{
                        
                        spriteCount++;
                        
                        return true;

                    }
                    
                }else
                    
                    return false;
                
            }else {
                throw new Exception("Cannot add due to sprite type");
            }
            
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        
        return false;
        
    }

    public int getCollisionListID(int setValue){
        
        return Collections.binarySearch(ec_collisions.getList(),
                    new SpriteCollisionList(setValue), comparator);
        
    }
    
    /**
     * Flags the sprite for removal, this method is better used because
     * it fully cleans the sprite managers as well.
     * @param s The sprite being deleted
     */
    public void kill(SpriteObject s) {
        
        if (s != null){
            
            s.isDead(true);
            
            if (s instanceof KeyboardInput){
                
                ec_keys.isDead(true);
                
            }
            
            try{
                
                spriteCount--;
                
                spriteKilled(s);
                
            }finally{
                
                return;
                
            }
            
        }
        
    }

    /**
     * Removes all sprites including any sprites currently
     * being used by the Box2D physics engine.
     */
    public void killAll(){
        
        ec_collisions.killSprites();
        
        for (int i = 0; i < 4; i++){
            
            renderList[i].removeAll();
            
            spriteList[i].clear();
            
        }
        
        clearEnvironment();
        
        spriteCount = 0;
        
        initText();
        
        new CoordinateSystem();
        
    }
    
    /**
     * Registers a keyboard controller for this engine.
     */
    
    public void registerKeyController(KeyController controller){
        
        this.ec_kController = controller;
        
    }
    /**
     * Returns the keyboard controller.
     */
    public KeyController getKeyController(){
        return this.ec_kController;
    }
    
    /**
     * Registers the mouse for the engine.
     */
    public void registerMouseButtonController(MouseController controller){
        
        ec_mController = controller;
        
    }
    
    /**
     * Returns the mouse object.
     */
    public MouseController getMouseButtonController(){
        return ec_mController;
    }

    public RenderList getRenderList(int index){
        
        return renderList[index];
        
    }
    
    public SpriteList getSpriteList(int index){
        
        return spriteList[index];
        
    }
    
    /**
     * Native method, can be implemented to check which sprites
     * have been recently killed. Most of the time your sprite
     * class can handle events when a sprite is killed.
     */
    public native void spriteKilled(SpriteObject dSprite);

    /**
     * Native method, can be implemented to check which sprites
     * have been recently added.
     */
    public native void spriteAdded(SpriteObject aSprite);
    
    /**
     * This method is called by the main thread. The 
     * heavier this method is on the main thread, the slower the 
     * updates will be as well as rendering sprites.
     */
    public abstract void gameLoop();
    
    /**
     * Pre-Render is when the game is about to render all the sprites
     * onto the window. No graphics device has been created yet at
     * this point. This method would be suitable for refreshing cached
     * sprites. The gameLoop method is called after this method is called.
     */
    public native void preRender();
    
    /**
     * This method is called when a game event needs to be
     * handled. 
     * @param eventID The game event's id
     */
    public abstract void gameEvent(int eventID);
    
    /**
     * Post-Render is when the game has finished rendering a frame.
     * The current graphics device is passed into this method.
     */
    public native void postRender(Graphics2D g2d);
    
    /**
     * Once you call the run method any line of code after will not execute
     * until the game engine is shut down. This method is called just after
     * the first frame in the game has started. This is a good time to call
     * a cache image on the graphics panel or via the Tools class.
     */
    public native void preRun();
    
    /**
     * Native method that is called whenever the window
     * is opened.
     */
    public native void WindowOpened(WindowEvent e);
    
    /**
     * Native method that is called whenever the window
     * is maximized.
     */
    public native void WindowMaximized(WindowEvent e);
    
    /**
     * Native method that is called whenever the window
     * is minimized.
     */
    public native void WindowMinimized(WindowEvent e);
    
    /**
     * Native method that is called whenever the window
     * is closing.
     */
    public native void WindowClosing(WindowEvent e);
    
    public class JWindowListener extends WindowAdapter {
        
        public void windowOpened(WindowEvent e) {
            
            try{
                WindowOpened(e);
            }finally{
                return;
            }
        }
        
        public void windowDeiconified(WindowEvent e) {
            
            try{
                WindowMaximized(e);
            }finally{
                return;
            }
        }
        
        public void windowIconified(WindowEvent e) {
            
            try{
                WindowMinimized(e);
            }finally{
                return;
            }
        }
        
        public void windowClosing(WindowEvent e) {
            
            try{
                WindowClosing(e);
            }finally{
                return;
            }
        }
        
    }
    
    public class KeyBoard implements java.awt.event.KeyListener {

        public void keyTyped(java.awt.event.KeyEvent e) {
            
        }

        public void keyPressed(java.awt.event.KeyEvent e) {
            
            if (ec_kController != null){
                ec_kController.keyPressed(e);
            }
            
            if (ec_keys != null) {
                
                ec_keys.read(e);
                
            }
        }

        public void keyReleased(java.awt.event.KeyEvent e) {
            
            try {
                
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE && exitOnEscape){
                    done = true;
                }
                
                if (ec_kController != null){
                    ec_kController.keyReleased(e);
                }
                
                
            } finally {
                return;
            }
            
        }
        
    }

    public class MouseListener implements java.awt.event.MouseListener {

        public void mouseClicked(java.awt.event.MouseEvent e) {
            
            if (ec_mController != null) {
                ec_mController.mouseClicked(e);
            }
            
        }

        public void mousePressed(java.awt.event.MouseEvent e) {
            
            
            if (ec_mController != null) {
                ec_mController.mousePressed(e);
            }
            
            if (ec_clickable != null){
                ec_clickable.processClicked(e);
            }
            
        }

        public void mouseReleased(java.awt.event.MouseEvent e) {
            
            if (ec_mController != null) {
                ec_mController.mouseReleased(e);
            }
            
        }

        public void mouseEntered(java.awt.event.MouseEvent e) {
            
            if (ec_mController != null) {
                ec_mController.mouseEntered(e);
            }
            
        }

        public void mouseExited(java.awt.event.MouseEvent e) {
            
            if (ec_mController != null) {
                ec_mController.mouseExited(e);
            }
            
        }

    }

    public class MouseMovementListener implements java.awt.event.MouseMotionListener {

        public void mouseDragged(java.awt.event.MouseEvent e) {
            
            try {
                if (running){
                    
                    if (ec_mController != null) {
                        ec_mController.mouseDragged(e);
                    }
                }
            } finally {
                return;
            }
        }

        public void mouseMoved(java.awt.event.MouseEvent e) {
            
            if (ec_mController != null) {
                ec_mController.mouseMoved(e);
            }
            
            debugMouse = Tools.getScaledMouse(e);
            
        }

    }
    
    private class MouseWheelListener implements java.awt.event.MouseWheelListener{

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            
            if (ec_mController != null) {
                ec_mController.mouseWheelMoved(e);
            }
            
        }
        
    }

    private class KeyBoardCog implements CogEvent {

        @Override
        public void update() {
            
            updateUserInput();

            updateKeyboardInput();
            
        }
        
    }
    
    /*
     * All private methods for the game engine class.
     */
    private void loadThreads(){
        
        ec_collisions = ec_manager.getCollisionManager();
        
        Cog cog = new Cog(new KeyBoardCog(), 1f / 1000f);
        
        cog.synchCog(false);
        
        cog.enable();
        
        cogs.offer(new Cog(ec_collisions, collisionCycle));
        
        cogs.offer(new Cog(ec_manager.getBackgroundThread(), updateThread));
        
        cogs.offer(new Cog(ec_manager.getSpriteCollisionThread(), updateThread));
        
        cogs.offer(new Cog(ec_manager.getStaticThread(), updateThread));
        
    }
        
    private void initVariables(){
        
        cogs = new Chain<Cog>();
        
        spriteList = new SpriteList[4];
        
        renderList = new RenderList[4];
        
        for (int i = 0; i < 4; i++){
            
            spriteList[i] = new SpriteList();
            
            renderList[i] = new RenderList();
            
        }
        
        done = running = displayFPS = step = false;
        
        debug_delay = new Delay(120);
        
        debug_delay.allowReset(true);
        
        exitOnEscape = true;
        
        spriteCount = 0;
        
        events = new GameEvent();
        
        new CoordinateSystem();
        
        debugColor = Color.WHITE;
        
        collisionCycle = 1f / 500f;
        
        comparator = new Comparator<SpriteCollisionList>() {

            @Override
            public int compare(SpriteCollisionList o1, SpriteCollisionList o2) {

                int v1 = o1.getSetValue();

                int v2 = o2.getSetValue();

                if (v1 > v2){

                    return 1;

                }else if (v1 < v2){

                    return -1;

                }

                return 0;

            }
        };
        
        df = new DecimalFormat("0.##%");
        
        initText();
        
    }
    
    private void initText(){
        
        ec_display = new Text();
        
        ec_display.setColor(debugColor);
        
        ec_display.setPosition(10, -10);
        
        ec_display.addLine(7);
        
        debugMouse = new SpritePoint();
        
        add(ec_display);
        
    }
    
    private void showFPS(){
        
        ec_display.line("Zoom-X: " + new DecimalFormat("#0.00%").format(port.getScaleValue().x), 4);

        ec_display.line("View Port: " + 
                new DecimalFormat("#0.0000").format(-port.getRadial().x) + ", " + 
                new DecimalFormat("#0.0000").format(-port.getRadial().y), 5);

        ec_display.line("Camera Mouse: " + 
                new DecimalFormat("#0.0000").format(debugMouse.x) + ", " + 
                new DecimalFormat("#0.0000").format(debugMouse.y), 6);
        
        ec_display.line("Mouse From Origin: " + 
                new DecimalFormat("#0.0000").format(debugMouse.x - port.getRadial().x) + ", " + 
                new DecimalFormat("#0.0000").format(debugMouse.y - port.getRadial().y), 7);
        
        ec_display.line("Sprite Count: " + spriteCount, 3);
        
    }
    
    private void closeFPS(){
        
        ec_display.clearAllLines();
        
    }
   
    private void renderWorld(){
        
        frame.getPanelFrame().repaint();

        if (frame.getPanelFrame().resizeToFrame())
            
            frame.getPanelFrame().getBounds().setSize(frame.getWidth(), frame.getHeight());
        
    }
    
    private void updatePhysics(){
        
        if (Environment.getWorld() != null){
                
            Environment.step();

        }
        
    }
    
    private void updateUserInput(){
        
        if (ec_mController != null){
            
            ec_mController.updateMouse();
            
        }

        if (ec_kController != null){
            
            ec_kController.updateComboList();
            
            ec_kController.updateKeyBoard();
            
        }
                
    }
    
    private void clearEnvironment(){
        
        if (Environment.getWorld() != null){
            
            Body body, temp_body;
            
            if (Environment.getWorld().getBodyList() != null){
                
                body = Environment.getWorld().getBodyList();
            
                temp_body = body;
                
            }else
                
                return;
            
            while(body.m_next != null){
                
                body = body.m_next;
                
                Environment.getWorld().destroyBody(body);
                
            }
            
            Environment.getWorld().destroyBody(temp_body);
            
        }
        
    }
    
    private void checkGameEvents() {
        
        Iterator<GameEvent.Event> iter = events.getEvents().iterator();
        
        if (iter.hasNext()){
            
            GameEvent.Event event = iter.next();

            gameEvent(event.getEventID());

            iter.remove();
            
        }
        
    }
    
    private void updateKeyboardInput() {
        
        if (ec_keys instanceof Typer){
                    
            ((Typer)ec_keys).step();

        }
        
    }
    
    private void checkPanelSize() {
        
        getWindow().getPanelFrame().setSize((int)(getWindow().getWidth() - port.getOffSetDimensions().x), 
                (int)(getWindow().getHeight() - port.getOffSetDimensions().y));
        
    }
    
}
