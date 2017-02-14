
package EasyClip2D.gui.util;

import EasyClip2D.core.ECEngine;
import EasyClip2D.geom.SpritePoint;
import EasyClip2D.sprite.Sprite;
import EasyClip2D.sprite.SpriteObject;
import EasyClip2D.util.Tools;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * Renders the origin and the camera's position onto the screen
 * @author Richard DeSilvey
 */
public class CoordinateSystem extends Sprite {

    private static boolean drawOrigin = false;
    
    public CoordinateSystem() {
        
        super(0, 0);
        
        renderOffScreen = true;
        
        Tools.ec_engine.add(this);
        
    }

    @Override
    public void spriteSetup() {
        
        setSpriteType(Tools.TYPE_STATIC);
        
    }

    @Override
    public void spriteUpdate(float x, float y) {
        
    }

    @Override
    public void collisionDetected(SpriteObject collidee) {
        
    }

    @Override
    public void nearestSprite(SpriteObject nearest) {
        
    }
    
    public static void drawOrigin(boolean b){
        drawOrigin = b;
    }

    @Override
    public void additionalGraphics(Graphics2D g2d) {
        
        if (drawOrigin){
            
            g2d.setColor(Color.CYAN);
            
            g2d.setStroke(new BasicStroke(2.0f));
            
            SpritePoint rPos = ECEngine.port.getRadial();
            
            SpritePoint pos = Tools.getWindowPosition(getPosition());
            
            int x = (int)(pos.x - rPos.x);
            int y = (int)(pos.y - rPos.y);
            
                g2d.drawLine(x, y, x + 10, y);

                g2d.drawLine(x, y, x - 10, y);

                g2d.drawLine(x, y, x, y + 10);

                g2d.drawLine(x, y, x, y - 10);
            
            g2d.setColor(Color.WHITE);

            Tools.setGraphicsToViewPort(g2d);

            x = (int)(getPosition().x + rPos.x);
            y = (int)(getPosition().y + rPos.y);

                g2d.drawLine(x, y, x + 50, y);

                g2d.drawLine(x, y, x - 50, y);

                g2d.drawLine(x, y, x, y + 50);

                g2d.drawLine(x, y, x, y - 50);
     
            g2d.setTransform(new AffineTransform());
            
        }
        
    }
    
}
