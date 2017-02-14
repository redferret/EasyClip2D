
package EasyClip2D.gui.graphic;

import EasyClip2D.gui.util.Units;
import EasyClip2D.Box2D.org.jbox2d.collision.shapes.PolygonShape;
import EasyClip2D.Box2D.org.jbox2d.common.Vec2;
import EasyClip2D.Box2D.org.jbox2d.dynamics.Fixture;
import EasyClip2D.core.ECEngine;
import EasyClip2D.geom.SpritePoint;
import EasyClip2D.sprite.Sprite2D;
import EasyClip2D.util.Tools;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * The Debug Graphic class is used to draw fixture shapes in box2d,
 * allowing a user to debug their sprites.
 * 
 * This is best used within the additional graphics method
 * of your sprite class.
 * 
 * 
 */
public class DebugGraphic {

    private Vec2[] verts;
    
    private Sprite2D s2d;
    
    private AlphaComposite trans, solid;
    
    public DebugGraphic(Sprite2D s2d) {
        
        this(s2d, 0.5f);
        
    }

    /**
     * Sets the sprite and the transparent value, between 1.0f and 0.0f
     */
    public DebugGraphic(Sprite2D s2d, float f) {
        
        this.s2d = s2d;
        
        trans = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, f);
        
        solid = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        
    }
    
    private void checkVerts(Fixture fix){
        
        verts = ((PolygonShape)fix.m_shape).m_vertices;
        
    }
    
    /**
     * Draws a polygon shape from a fixture.
     */
    public void drawPolygon(Graphics2D g2d, Fixture fix, Color color){
    
        checkVerts(fix);
        
        SpritePoint rPos = ECEngine.port.getRadial();
        
        Tools.setGraphicsToViewPort(g2d);
        
        g2d.setColor(color);
        
        g2d.setComposite(trans);
        
        int length = ((PolygonShape)fix.m_shape).getVertexCount() + 1;
        
        int xPoints[] = new int[length];
        
        int yPoints[] = new int[length];
        
        float x = s2d.getPosition().x + rPos.x, y = s2d.getPosition().y + rPos.y;
        
        for (int i = 0; i < length - 1; i++){
            
            xPoints[i] = (int)(verts[i].x * Units.scale_value + x);
            
            yPoints[i] = (int)(verts[i].y * Units.scale_value + y);
            
        }
        
        xPoints[length - 1] = (int)(verts[0].x * Units.scale_value + x);
        
        yPoints[length - 1] = (int)(verts[0].y * Units.scale_value + y);
        
        g2d.rotate(s2d.getBody().getAngle(), s2d.getPosition().x + rPos.x, 
                s2d.getPosition().y + rPos.y);
        
        g2d.fillPolygon(xPoints, yPoints, length);
        
        g2d.setComposite(solid);
        
        g2d.setStroke(new BasicStroke(1.8f));
        
        g2d.drawPolygon(xPoints, yPoints, length);
    
        g2d.setTransform(new AffineTransform());
        
    }
    
    /**
     * Draws a center point for your Sprite2D, use this method
     * within your additionalGraphics method.
     */
    public void drawPositionPoint(Graphics2D g2d, Color color){
        
        g2d.setColor(color);
        
        g2d.fillOval((int)s2d.getPosition().x, (int)s2d.getPosition().y, 3, 3);
        
    }
    
    /**
     * Draws a circle shape based off the fixture.
     */
    public void drawCircle(Graphics2D g2d, Fixture fix, Color color){
        
        g2d.setColor(color);
        
        g2d.setComposite(trans);
        
        SpritePoint rPos = ECEngine.port.getRadial();
        
        Tools.setGraphicsToViewPort(g2d);
        
        int diam = (int)(fix.m_shape.m_radius * Units.scale_value) * 2;
        
        int radius = diam / 2;
        
        int x = (int)(s2d.getPosition().x + rPos.x) - radius,
                y = (int)(s2d.getPosition().y + rPos.y) - radius;

        g2d.fillOval(x, y, diam, diam);
        
        g2d.setComposite(solid);
        
        g2d.setStroke(new BasicStroke(1.8f));
        
        g2d.drawOval(x, y, diam, diam);
        
        g2d.rotate(s2d.getBody().getAngle(), s2d.getPosition().x + rPos.x, s2d.getPosition().y + rPos.y);
        
        g2d.drawLine((int)(s2d.getPosition().x + rPos.x), (int)(s2d.getPosition().y + rPos.y), 
                (int)(s2d.getPosition().x + rPos.x), (int)(s2d.getPosition().y + rPos.y + radius));

        g2d.setTransform(new AffineTransform());
        
    }
    
}
