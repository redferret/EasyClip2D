
package EasyClip2D.io;

import EasyClip2D.util.listeners.HoveredListener;
import java.util.ArrayList;

/**
 * The mouse controller is optional but an easier way 
 * to control mouse inputs.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class MouseController {

    private ArrayList<MouseButton> mouseButtons;
    
    private MouseMovement mouseMovements;
    
    private MouseWheel wheel;
    
    private HoveredListener hovered;
    
    public MouseController() {
        mouseButtons = new ArrayList<MouseButton>();
    }
    
    public void addMouseButton(MouseButton button){
        mouseButtons.add(button);
    }
    
    public void addMouseMovement(MouseMovement movement){
        mouseMovements = movement;
    }
    
    public void addMouseWheel(MouseWheel mw){
        this.wheel = mw;
    }
    
    public void addHoveredListener() {
        
        hovered = new HoveredListener();
        
    }
    
    public HoveredListener getHoveredListener(){
        return hovered;
    }
    
    public MouseButton getButton(int mouseCode){
        
        for (int i = 0; i < mouseButtons.size(); i++){
            
            if (mouseButtons.get(i).getButton() == mouseCode){
                
                return mouseButtons.get(i);
                
            }
            
        }
        
        return null;
        
    }
    
    public void mouseClicked(java.awt.event.MouseEvent e) {
        
        for (int i = 0; i < mouseButtons.size(); i++){
            
            if (mouseButtons.get(i).getButton() == e.getButton()){
                
                try {
                    mouseButtons.get(i).mouseClicked(e);
                } finally {
                }
                
            }
            
        }
        
    }

    public void mousePressed(java.awt.event.MouseEvent e) {
        
        for (int i = 0; i < mouseButtons.size(); i++){
            
            if (mouseButtons.get(i).getButton() == e.getButton()){
                
                try {
                    mouseButtons.get(i).mousePressed(e);
                } finally {
                }
                
            }
            
        }
        
    }

    public void mouseReleased(java.awt.event.MouseEvent e) {
        
        for (int i = 0; i < mouseButtons.size(); i++){
            
            if (mouseButtons.get(i).getButton() == e.getButton()){
                
                try{
                    mouseButtons.get(i).mouseReleased(e);
                } finally {
                }
            }
            
        }
        
    }
    
    public void updateMouse(){
        
        if (mouseButtons != null){
            
            for (int i = 0; i < mouseButtons.size(); i++){

                if (mouseButtons.get(i).isPressed()) {
                    
                    mouseButtons.get(i).MouseHeld();
                    
                }else{
                    
                    mouseButtons.get(i).MouseIdle();
                    
                }

            }
            
        }
        
    }

    public void mouseEntered(java.awt.event.MouseEvent e) {
        
        if (mouseMovements != null) {
            mouseMovements.mouseEntered(e);
        }
        
    }

    public void mouseExited(java.awt.event.MouseEvent e) {
        
        if (mouseMovements != null) {
            mouseMovements.mouseExited(e);
        }
    }
    
    public void mouseDragged(java.awt.event.MouseEvent e) {
        
        if (mouseMovements != null) {
            mouseMovements.mouseDragged(e);
        }
    }

    public void mouseMoved(java.awt.event.MouseEvent e) {

        if (mouseMovements != null) {
            
            mouseMovements.mouseMoved(e);
            
        }
        
        if (hovered != null){
            
            hovered.highlight(e);
            
        }
        
    }
    
    public void mouseWheelMoved(java.awt.event.MouseWheelEvent e){
        
        if (wheel != null)
            
            wheel.mouseWheel(e);
        
        
    }

    
    
}
