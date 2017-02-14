
package EasyClip2D.io;

import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;


/**
 * The Key Controller is used to keep track of keys on the keyboard.
 * The controller does not keep track of every key though, 
 * you must specify which keys are going to be used by creating
 * a separate key using the Key class, documentation and examples
 * are given online on how to effectively do this.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class KeyController {

    private LinkedList<Key>keys;
    
    private LinkedList<KeyCombo> combo_list;
    
    /**
     * Default Constructor
     */
    public KeyController() {
        
        keys = new LinkedList<Key>();
        
        combo_list = new LinkedList<KeyCombo>();
    }
    
    public void addKey(Key k){
        
        keys.offer(k);
        
    }
    
    public void addCombo(KeyCombo combo){
        
        combo_list.offer(combo);
        
    }
    
    public Key getKey(int keyCode){
        
        for (int i = 0; i < keys.size(); i++){
            
            if (keys.get(i).getKeyCode() == keyCode){
                return keys.get(i);
            }
            
        }
        
        return null;
    }
    
    public void removeKey(Key k){
        keys.remove(k);
    }
    
    public void keyPressed(KeyEvent e){
        
        Iterator<Key> key_list = keys.iterator();
        
        Iterator<KeyCombo> combos = combo_list.iterator();
        
        while(key_list.hasNext()){
            
            key_list.next().keyPressed(e);
            
        }
        
        while(combos.hasNext()){
            
            combos.next().testCombo(e);
            
        }
        
    }
    
    public void keyReleased(KeyEvent e){
        
        Iterator<Key> key_list = keys.iterator();
        
        Iterator<KeyCombo> combos = combo_list.iterator();
        
        while(key_list.hasNext()){
            
            key_list.next().keyReleased(e);
            
        }
        
        while(combos.hasNext()){
            
            combos.next().testCompletion(e);
            
        }
        
    }
    
    public void updateComboList(){
        
        Iterator<KeyCombo> list = combo_list.iterator();
        
        while(list.hasNext()){
            
            list.next().update();
            
        }
        
    }
    
    public void updateKeyBoard(){
        
        Iterator<Key> list = keys.iterator();
        
        while(list.hasNext()){
            
            Key key = list.next();
            
            if (key.isPressed()) {
                    
                key.keyHeld();

            }else{

                key.keyIdle();

            }
            
        }
        
    }
    
}
