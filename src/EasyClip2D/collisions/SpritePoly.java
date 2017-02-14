

package EasyClip2D.collisions;


import EasyClip2D.geom.SpritePoint;
import EasyClip2D.geom.SpriteSegment;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A sprite polygon object is used to setup collision
 * boundaries for sprite objects not including the Sprite2D object.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class SpritePoly extends SpriteBound implements Serializable{

    private ConcurrentLinkedQueue<SpriteSegment> segments;
    
    public SpritePoly(){
        
        segments = new ConcurrentLinkedQueue<SpriteSegment>();
        
        setCentre(new SpritePoint());
        
    }
    
    public SpritePoly(SpritePoint centre){
        
        segments = new ConcurrentLinkedQueue<SpriteSegment>();
        
        setCentre(new SpritePoint(centre));
        
    }

    public void addLineSeg(SpriteSegment seg){
        
        segments.offer(seg);
        
    }
    
    public void addSegments(SpriteSegment[] segs){
        
        for (SpriteSegment seg : segs){
            
            segments.offer(seg);
            
        }
        
    }
    
    public ConcurrentLinkedQueue<SpriteSegment> getSegments(){
        
        return segments;
        
    }
    
    public void removeAll(){
        
        segments.clear();
        
    }
    
    public int segCount(){
        
        return segments.size();
        
    }
    
    public void rotateBound(SpritePoint origin, float radians){
        
        Iterator<SpriteSegment> segList = segments.iterator();
        
        SpriteSegment segment;
        
        while(segList.hasNext()){
            
            segment = segList.next();
            
            segment.setLine(SpritePoint.rotatePoint(segment.getP1(), origin, radians), 
                            SpritePoint.rotatePoint(segment.getP2(), origin, radians));
            
        }
        
        prevRotation = radians;
        
    }
    
    public static SpritePoly copyRect(BufferedImage image, SpritePoint pos){
        
        float width = image.getWidth();
        float height = image.getHeight();

        return createRect(width, height, pos);
    }
    
    public static SpritePoly createRect(float width, float height, SpritePoint pos){

        SpriteSegment seg;
        
        SpritePoly poly = new SpritePoly(pos);

        seg = new SpriteSegment(pos.getX(), pos.getY(), pos.getX() + width, pos.getY());
        poly.addLineSeg(seg);
        seg = new SpriteSegment(pos.getX() + width, pos.getY(), pos.getX() + width, pos.getY() + height);
        poly.addLineSeg(seg);
        seg = new SpriteSegment(pos.getX() + width, pos.getY() + height, pos.getX(), pos.getY() + height);
        poly.addLineSeg(seg);
        seg = new SpriteSegment(pos.getX(), pos.getY() + height, pos.getX(), pos.getY());
        poly.addLineSeg(seg);
        
        return poly;
    }
    
    public void move(SpritePoint newCentre){
        
        Iterator<SpriteSegment> segList = segments.iterator();
        
        SpriteSegment segment;
        
        while(segList.hasNext()){
            
            segment = segList.next();
            
            segment.setLine(SpritePoint.recalculatePoint(segment.getP1(), newCentre, getCentre()),
                            SpritePoint.recalculatePoint(segment.getP2(), newCentre, getCentre()));
            
        }
        
        getCentre().setLocation(newCentre.x, newCentre.y);
        
    }
    
    public void draw(Graphics2D g2d){
        
        Iterator<SpriteSegment> segList = segments.iterator();
        
        SpriteSegment segment;
        
        GeneralPath path;
        
        while(segList.hasNext()){
            
            segment = segList.next();
            
            path = new GeneralPath();
            
            path.moveTo(segment.getP1().getX(), segment.getP1().getY());
            
            path.lineTo(segment.getP2().getX(), segment.getP2().getY());
            
            g2d.draw(path);
            
        }
        
        g2d.drawString("Centre - " + getCentre().getX() + ", " + getCentre().getY(), 
                (int)getCentre().getX(), (int)getCentre().getY() - 10);
    }
    
    public boolean intersects(SpriteBound bound){
        
        Iterator<SpriteSegment> tSegList = segments.iterator();
        
        Iterator<SpriteSegment> bSegList;
        
        SpriteSegment bSegment, tSegment;
        
        while(tSegList.hasNext()){
            
            tSegment = tSegList.next();
            
            if (tSegment != null){
                
                bSegList = ((SpritePoly)bound).segments.iterator();
                
                while(bSegList.hasNext()){
                    
                    bSegment = bSegList.next();
                    
                    if (bSegment != null){
                        
                        if (bSegment.intersects(tSegment)){
                            
                            return true;
                            
                        }
                        
                    }
                    
                }
                
            }
            
        }
        
        return false;
        
    }

}
