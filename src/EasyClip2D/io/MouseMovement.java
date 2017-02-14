
package EasyClip2D.io;

import EasyClip2D.util.Delay;
import java.awt.event.MouseEvent;

/**
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public abstract class MouseMovement {

    private Delay delay;
    
    private MouseEvent event;
    
    public MouseMovement(){
        
        delay = new Delay();
        
    }
    
    public void setEvent(MouseEvent e){
        event = e;
    }
    
    public MouseEvent getEvent(){
        return event;
    }
    
    public Delay getDelay(){
        return delay;
    }
    
    public abstract void mouseEntered(java.awt.event.MouseEvent e);

    public abstract void mouseExited(java.awt.event.MouseEvent e);
    
    public abstract void mouseDragged(java.awt.event.MouseEvent e);

    public abstract void mouseMoved(java.awt.event.MouseEvent e);
    
    
}
