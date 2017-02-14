
package EasyClip2D.sprite;

import EasyClip2D.core.ECEngine;
import EasyClip2D.geom.SpritePoint;
import EasyClip2D.geom.SpriteRect;
import EasyClip2D.gui.graphic.image.SpriteImage;
import EasyClip2D.sprite.util.HoveredSprite;
import EasyClip2D.util.Tools;
import EasyClip2D.util.listeners.ClickableEvent;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 *
 * @author Richard DeSilvey
 */
public abstract class Button extends SpriteObject implements ClickableEvent, HoveredSprite{

    /**
     * Default constructor
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Button() {
        
        this(0, 0, true);
        
    }
    /**
     * Constructs the sprite at the x and y position
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Button(float x, float y) {
        
        this(x, y, true);
        
    }
    
    /**
     * Constructs the sprite at the x and y position
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Button(float x, float y, boolean renderToViewPort) {
        
        super();
        
        pos = new SpritePoint(x, y);
        
        this.renderToViewPort = renderToViewPort;
        
        initSprite();

        spriteSetup();
        
        ECEngine.port.transformToViewPort(pos);
        
        if (renderToViewPort){
            
            SpritePoint rPos = ECEngine.port.getRadial();

            pos.translate(rPos.x, rPos.y);
        
        }
    }
    
    public final void initSprite(){
        
        spriteImage = new SpriteImage();
        
        center = new SpritePoint();
        
        checkBounds = true;
        
        spriteType = Tools.TYPE_DEFAULT;
        
        animate = hasImage = isDead = renderOffScreen = false;
        
        renderOrder = -1;
        
        render = true;
        
        anchor = new SpritePoint();
        
    }

    /**
     * A mouse event is passed to check if the mouses position
     * is within bounds of this sprite.
     * @param e Mouse Event
     * @return if the mouses pos is within bounds of this sprite
     */
    public static boolean checkMouseBounds(Sprite s, MouseEvent e){
        
        SpriteRect rect = new SpriteRect();
        
        SpritePoint rPos = ECEngine.port.getRadial();
        
        rect.setRect(s.getPosition().x + s.anchor.x, s.getPosition().y + s.anchor.y, s.getWidth(), s.getHeight());
        
        return (rect.contains(e.getX() - rPos.x, e.getY() - rPos.y));
        
    }
    
    @Override
    public void update() {
        
        
        
    }

    @Override
    public void draw(Graphics2D g2d) {
        
        
        
    }

    @Override
    public void collisionDetected(SpriteObject collidee) {
        
        throw new UnsupportedOperationException("Not supported.");
        
    }

    @Override
    public void nearestSprite(SpriteObject nearest) {
        
        throw new UnsupportedOperationException("Not supported.");
        
    }
    
}
