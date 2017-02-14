
package EasyClip2D.util;

import EasyClip2D.sprite.SpriteObject;
import java.util.Iterator;

/**
 * The Sprite List is a list that holds a reference to
 * the main sprite list in the game engine.
 * The sprite list is not like any list where you can
 * add sprites to keep track of them. The main sprite
 * list mentioned is used to link other sprites together
 * within the game engine. Each sprite holds a reference
 * to the next sprite.
 * 
 * @author Richard DeSilvey
 */
public class SpriteList {

    private SpriteObject next_member, list_head;
    
    public SpriteList() {
        
        next_member = null;
        
        list_head = null;
        
    }
    
    public synchronized void addSprite(SpriteObject s){
    
        assert (s == null) : "Can't add a null reference";
        
        if (list_head != null) {

            next_member.addNext(s);

            next_member = next_member.getNext();
                
        } else {

            list_head = s;

            next_member = s;
            
        }
        
    }
    
    public SpriteObject getList(){
        
        return list_head;
        
    }
    
    public void clear(){
        
        list_head = null;
        
        next_member = null;
        
    }
    
}
