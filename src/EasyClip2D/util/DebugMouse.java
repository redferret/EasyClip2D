
package EasyClip2D.util;

import EasyClip2D.geom.SpritePoint;
import EasyClip2D.io.MouseMovement;
import java.awt.event.MouseEvent;

public final class DebugMouse extends MouseMovement{

    private SpritePoint mPos;
    
    public DebugMouse() {
        
        super();
        
        mPos = new SpritePoint();
        
    }
    
    public SpritePoint getMousePos(){
        
        return mPos;
        
    }
    
    public void mouseEntered(MouseEvent e) {
        
    }

    public void mouseExited(MouseEvent e) {
        
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        
        mPos.setLocation(e.getX(), e.getY());
        
    }
   
}
