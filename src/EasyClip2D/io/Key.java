


package EasyClip2D.io;

import EasyClip2D.util.Delay;
import java.awt.event.KeyEvent;

/**
 * The key object is used for user input. The class is abstract
 * and must be implemented. There are several methods in which
 * to create a key object. The most useful and common way is
 * to use the class as a template for many instances of the same
 * object rather than creating an UpKey, DownKey, etc. classes.
 * Instead MyKey would be a good use for this. Also the KeyController
 * class keeps track of the particular KeyEvent for a key.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public abstract class Key {

    private boolean isPressed, isReleased;
    
    private Delay pressedDelay;
    
    private int keyCode;
    
    private int eventKeyCode;
    
    private char eventChar;
    
    /**
     * Creates a new Key, the keyCode is very important however
     * it is possible to create your own key codes as well.
     * @param keyCode key code ex. <code>KeyEvent.getKeyCode();</code>
     */
    public Key(int keyCode) {
        
        isPressed = false;
        
        isReleased = true;
        
        pressedDelay = new Delay();
        
        pressedDelay.allowReset(true);
        
        this.keyCode = keyCode;
        
    }
    
    /**
     * Returns the key code of this key
     * @return the key's key code
     */
    public int getKeyCode(){
        return keyCode;
    }
    /**
     * Returns the Delay object of this key
     * @return the Delay object of this key
     */
    public Delay getDelay(){
        return pressedDelay;
    }

    
    /**
     * Set the state of the key to pressed or held down
     * @param e the state of the key to pressed or held down
     */
    public final void keyPressed(KeyEvent e) {
        
        if (e.getKeyCode() == this.keyCode && isReleased){
            
            isPressed = true;
            
            eventKeyCode = e.getKeyCode();
            
            eventChar = e.getKeyChar();
            
        }
        
    }
    /**
     * Set the key to released state
     * @param e the key to released state
     */
    public final void keyReleased(KeyEvent e) {
        
        if (e.getKeyCode() == this.keyCode){
            
            isPressed = false;
            
            isReleased = true;
            
            eventKeyCode = e.getKeyCode();
            
            eventChar = e.getKeyChar();
        }
        
    }
    
    /**
     * Gets the current key event for this key
     */
    public int getEventKeyCode(){
        return eventKeyCode;
    }

    /**
     * Gets the character for the key pressed
     */
    public char getEventChar() {
        return eventChar;
    }
    
    /**
     * Check whether the key is being pressed
     */
    public boolean isPressed() {
        return this.isPressed;
    }
    
    /**
     * Releases the key and puts the key into Idle.
     */
    public void release(){
        
        isPressed = false;
        
        isReleased = false;
        
    }
    
    public boolean isReleased() {
        
        return isReleased;
        
    }
    
    /**
     * When a key is held down. The game keeps track of this and
     * calls this method when ever this key is being held down.
     * This method can slow down the main thread of the game as well
     * if you use this method intensively.  
     */
    public abstract void keyHeld();
    
    /**
     * A key goes into an Idle state when it is not being pressed or held
     * down. This method can slow down the main thread of the game as well
     * if you use this method intensively.  
     */
    public abstract void keyIdle();

    
    
    
    
}