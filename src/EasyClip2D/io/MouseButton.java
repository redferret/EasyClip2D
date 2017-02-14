
package EasyClip2D.io;

import EasyClip2D.util.Delay;
import java.awt.event.MouseEvent;

/**
 * MouseButton is used to control mouse input
 * for all mouse buttons.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public abstract class MouseButton {

    private boolean isPressed;
    
    private Delay pressedDelay;
    
    private int keyCode;
    
    private MouseEvent event;
    
    public MouseButton(int mouseType){
        
        isPressed = false;
        
        pressedDelay = new Delay();
        
        keyCode = mouseType;
        
    }
    
    public int getButton(){
        return keyCode;
    }
    
    public Delay getDelay(){
        return pressedDelay;
    }
    
    public boolean isPressed() {
        return isPressed;
    }
    
    public void setEvent(MouseEvent e){
        event = e;
    }
    
    public MouseEvent getEvent(){
        return event;
    }
    
    public final void mouseClicked(java.awt.event.MouseEvent e){

        MouseClicked();
        setEvent(e);
    }
    
    public final void mousePressed(java.awt.event.MouseEvent e){
        
        isPressed = true;
        setEvent(e);
        
    }

    public final void mouseReleased(java.awt.event.MouseEvent e){
        
        isPressed = false;
        setEvent(e);
        
    }
    
    public abstract void MouseClicked();
    
    public abstract void MouseHeld();
    
    public abstract void MouseIdle();
    
    
}
