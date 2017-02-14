
package EasyClip2D.geom;

import java.io.Serializable;

/**
 * 
 * A sprite point contains a floating point integer for
 * an x and y location.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class SpritePoint implements Serializable {

    public float x, y;
    
    public SpritePoint(){
        x = y = 0;
    }

    public SpritePoint(SpritePoint p){
        this.x = p.getX();
        this.y = p.getY();
    }
    
    public SpritePoint(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    /**
     * This method doesn't always imply the direct center of
     * this point. This method is treated as if this point
     * is being treated like a boundary with the x component being
     * a length or width. This method returns a copy of this
     * sprite point with their components halved.
     */
    public SpritePoint getCenter(){
        
        SpritePoint p = new SpritePoint(x / 2, y / 2);
        
        return p;
        
    }
    
    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public void setLocation(SpritePoint point) {
        this.x = point.getX();
        this.y = point.getY();
    }
    
    public void translate(float x, float y){
        
        this.x += x;
        
        this.y += y;
        
    }
    
    public void translateTo(SpritePoint point){
        
        this.x += point.x;
        
        this.y += point.y;
        
    }
    
    public boolean equals(SpritePoint point){
        
        return (x == point.x) && (y == point.y);
        
    }

    public void negate(){
        
        setLocation(-x, -y);
        
    }
    
    /**
     * Sets point1 to a copied version of point2
     * @param source original
     * @param destination source to copy from
     */
    public static void copyTo(SpritePoint source, SpritePoint destination){
        
        destination.setLocation(source.getX(), source.getY());
        
    }
    
    public static SpritePoint copyOf(SpritePoint p){
        
        return new SpritePoint(p.x, p.y);
        
    }
    
    /**
     * Rotates a single point from the point origin to the position of degrees,
     * and returns a new translated point after rotation.
     * @param point to be rotated
     * @param origin of rotation
     * @param radians of rotation
     * @return new point after rotation
     */
    public static SpritePoint rotatePoint(SpritePoint point, SpritePoint origin, float radians){
        
        return rotatePoint(point.x, point.y, origin.x, origin.y, radians);
        
    }
    
    /**
     * Rotates a single point from the point origin to the position of radians,
     * and returns a new translated point after rotation.
     * @param point to be rotated
     * @param origin of rotation
     * @param radians of rotation
     * @return new point after rotation
     */
    public static SpritePoint rotatePoint(float x, float y, float rx, float ry, float radians){
        
        SpritePoint newPoint = new SpritePoint();
        
        x += (-rx);
        y += (-ry);
        
        newPoint.x = (float) ((x * Math.cos(radians)) - (y * Math.sin(radians)));
        newPoint.y = (float) (Math.sin(radians) * x + Math.cos(radians) * y);
        newPoint.x += (rx);
        newPoint.y += (ry);
        
        return newPoint;
        
    }
    
    /**
     * Provides a translation calculation of two points of the same distance,
     * for instance one point defining the centre point of the sprite
     * with a point at (centre.x + x, centre.y + y) can be translated to
     * the new centre point of equal distance.
     * By recalculation of the new point assumes the point is the same distance
     * and relative position to the old centre point as it is to the new centre point.
     * @param px x position of point
     * @param py y position of point
     * @param newCentre relative to the old point
     * @param oldCentre of the old point
     * @return 
     */
    public static SpritePoint recalculatePoint(SpritePoint point, SpritePoint newCentre, SpritePoint oldCentre){
        
        float px = point.getX(), py = point.getY();
        
        px = (newCentre.x - oldCentre.x) + px;
        py = (newCentre.y - oldCentre.y) + py;
        
        return new SpritePoint(px, py);
        
    }
    
    /**
     * Returns the distance between two points.
     *
     * @param x1 the X coordinate of the first specified point
     * @param y1 the Y coordinate of the first specified point
     * @param x2 the X coordinate of the second specified point
     * @param y2 the Y coordinate of the second specified point
     * @return the distance between the two sets of specified
     * coordinates.
     * @since 1.2
     */
    public static float distance(float x1, float y1,
            float x2, float y2) {
        x1 -= x2;
        y1 -= y2;
        return (float) Math.sqrt(x1 * x1 + y1 * y1);
    }
    
    public String toString(){
        
        return "(" + x + ", " + y + ")";
        
    }
    
}
