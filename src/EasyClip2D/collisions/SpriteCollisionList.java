
package EasyClip2D.collisions;

import EasyClip2D.sprite.SpriteObject;
import EasyClip2D.sprite.util.SpriteGrid;
import EasyClip2D.util.SpriteList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The sprites in this list are used to figure out
 * which sprites collide with each other. This kind
 * of setup is used so that certain sprites are colliders
 * to other collidees. For instance, a player
 * can be a collider to enemy sprites but also a
 * collidee to enemy sprite's bullets.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class SpriteCollisionList implements Comparable<SpriteCollisionList> {

    private ConcurrentLinkedQueue<SpriteObject> colliders;
    
    private ConcurrentLinkedQueue<SpriteObject> collidees;
    
    private ConcurrentLinkedQueue<SpriteObject> gridColliders;
    
    private SpriteGrid grid;
    
    private int setValue;
    
    /**
     * Creates a new Collision List with the set value of 0
     */
    public SpriteCollisionList() {
        
        this.colliders = new ConcurrentLinkedQueue<SpriteObject>();
        
        this.collidees = new ConcurrentLinkedQueue<SpriteObject>();
        
        this.gridColliders = new ConcurrentLinkedQueue<SpriteObject>();
        
        this.setValue = 0x00;
        
    }
    
    /**
     * Creates a new Collision List with the set value specified
     * @param setValue 
     */
    public SpriteCollisionList(int setValue) {
        
        this.colliders = new ConcurrentLinkedQueue<SpriteObject>();
        
        this.collidees = new ConcurrentLinkedQueue<SpriteObject>();
        
        this.gridColliders = new ConcurrentLinkedQueue<SpriteObject>();
        
        this.setValue = setValue;
        
    }
    
    /**
     * If there is a sprite grid that is being used
     * in a game, then that grid must be added to a sprite collision list
     * where collisions can be tested with any sprites with in
     * the sprite grid. 
     */
    public void setSpriteGrid(SpriteGrid grid){
        this.grid = grid;
    }
    
    /**
     * Sets the colliders and collidee lists. This
     * method is not needed but can be used if needed.
     */
    public void setCollisionLists(ConcurrentLinkedQueue<SpriteObject> colliders, ConcurrentLinkedQueue<SpriteObject> collidees){
        
        this.colliders = colliders;
        
        this.collidees = collidees;
        
    }
    
    /**
     * Returns the currently registered sprite grid
     */
    public SpriteGrid getGrid(){
        return grid;
    }
    
    /**
     * Returns the list for the grid colliders
     */
    public ConcurrentLinkedQueue<SpriteObject> gridColliders(){
        return gridColliders;
    }
    
    /**
     * Returns the list of colliders
     */
    public ConcurrentLinkedQueue<SpriteObject> colliders(){
        return colliders;
    }
    
    /**
     * Returns the list of collidees
     */
    public ConcurrentLinkedQueue<SpriteObject> collidees(){
        return collidees;
    }
    
    public int getSetValue(){
        return setValue;
    }

    @Override
    public int compareTo(SpriteCollisionList o) {
        
        int v1 = o.getSetValue();
        
        int v2 = getSetValue();
        
        if (v1 < v2){
            
            return 1;
            
        }else if (v1 > v2){
            
            return -1;
            
        }
        
        return 0;
        
    }
    
}
