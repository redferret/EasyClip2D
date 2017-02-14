
package EasyClip2D.io;

import EasyClip2D.util.Delay;
import java.awt.event.KeyEvent;
import java.util.Arrays;

/**
 * The key combo class was created so that a player
 * can complete combo moves.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public class KeyCombo {

    private Key[] combo;
    
    private Delay combo_time;
    
    private int current_key;
    
    private boolean held_combo;
    
    private boolean combo_complete, combo_started;
    
    /**
     * Creates a combo list of the given keys. The order of the combo
     * is dependent on the order of the keys in the array.
     * If the combo starts with the key A, then the combo can only
     * start and be finished if A is pressed along with the other keys.
     * The time that a player has to complete the combo is the time parameter with units
     * of frames. If your game is running at 60 FPS, and you set your time
     * to 60 frames, then the amount of time a player has to complete the combo
     * is 1 second.
     */
    public KeyCombo(Key[] keyCombo, int time) {
        
        combo = Arrays.copyOf(keyCombo, keyCombo.length);
        
        combo_complete = combo_started = false;
        
        held_combo = true;
        
        current_key = 0;
        
        combo_time = new Delay(time);
        
    }
    
    /**
     * @return if this combo is an ordered combo
     */
    public boolean isHeldCombo(){
        return held_combo;
    }
    /**
     * Sets whether a player must complete the combo in order.
     */
    public void heldCombo(boolean b){
        held_combo = b;
    }
    
    /**
     * This method updates the combo, this method
     * is used by the game engine.
     */
    public final void update(){
        
        if (combo_started)
            
            combo_time.delay();
        
    }
    
    /**
     * This method is used by the game engine to test the
     * combo.
     */
    public final void testCombo(KeyEvent e){
        
        if (!combo_started){
            
            if (combo[current_key].getKeyCode() == e.getKeyCode()){
                
                combo_started = true;
                
            }else
                
                return;
            
        }
        
        if (combo_time.testDelay() == 0){
            
            if (combo[current_key].getKeyCode() == e.getKeyCode()){
                
                current_key++;
                
                if (current_key >= combo.length){
                        
                    combo_complete = true;
                    
                    return;
                }
                
            }else{
                
                reset();
                
            }
            
        }else{
            
            reset();
            
        }
        
    }
    
    /**
     * This method is used by the game engine for other tests on the
     * combo.
     */
    public final void testCompletion(KeyEvent e){
        
        if (combo_started && held_combo){
            
            for (int i = 0; i < combo.length; i++){
                
                if (combo[i].getKeyCode() == e.getKeyCode()){
                    
                    if (!combo[i].isPressed()){
                        
                        reset();
                        
                    }
                    
                }
                
            }
            
        }
        
    }
    
    /**
     * This method resets the combo.
     */
    public final void reset(){
        
        combo_time.reset();
            
        combo_complete = combo_started = false;

        current_key = 0;
        
    }
    
    /**
     * @return Whether the combo was completed or not.
     */
    public boolean comboCompleted(){
        
        return combo_complete;
        
    }
    
}
