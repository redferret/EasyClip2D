
package EasyClip2D.util.thead;

/**
 * CogEvent defines a process to be made by the user's program.
 * ProcessTask calls the CogEvent's update() method each time it
 * refreshes.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public interface CogEvent {
    
    /**
     * All processes, code, methods, etc.
     */
    public abstract void update();
    
}
