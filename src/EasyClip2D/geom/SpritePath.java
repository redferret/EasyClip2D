
package EasyClip2D.geom;

import java.util.LinkedList;


/**
 * SpritePath used to create a path for a sprite to move on. 
 * Object can be initialized with an array of points passed into the
 * constructor. SpritePoints can be added and removed and cleared from
 * this path.
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class SpritePath {

    private LinkedList<SpritePoint> path;
    
    public SpritePath() {
        path = new LinkedList<SpritePoint>();
    }
    
    public SpritePath(SpritePoint[] points){
        
        path = new LinkedList<SpritePoint>();
        
        for (int i = 0; i < points.length; i++){
            addPoint(points[i]);
        }
        
    }
    /**
     * Add a new SpritePoint location to this path.
     * @param point 
     */
    public void addPoint(SpritePoint point){
        path.add(point);
    }
    /**
     * Remove an old SpritePoint location from this path.
     * @param point 
     */
    public void removePoint(SpritePoint point){
        path.remove(point);
    }
    
    /**
     * Clears this path's list
     */
    public void clear(){
        path.clear();
    }
    
    /**
     * Get the next SpritePoint from this path. This method acts as 
     * a queue method for popping the next element and offering
     * it back to the end of the path. This creates a circular 
     * path for a sprite object to follow.
     * @return the next SpritePoint on the path.
     */
    public SpritePoint getNext(){
        
        SpritePoint temp = path.pop();
        
        path.offer(temp);
        
        return temp;
        
    }
    
    /**
     * Get the a SpritePoint at index i. This returns a copy
     * of the object and does not effect it's location on the path.
     * @param i Index of the point
     * @return the copy of the SpritePoint
     */
    public SpritePoint getPoint(int i){
        return path.get(i);
    }
    
    /**
     * Get the last SpritePoint from this list
     * @return 
     */
    public SpritePoint last(){
        return path.getLast();
    }
    
    /**
     * Get the first SpritePoint from this list
     * @return 
     */
    public SpritePoint first(){
        return path.getFirst();
    }
    
    
}
