
package EasyClip2D.util;

import java.io.Serializable;

/**
 * Delay objects are used when something needs a slower update
 * than what your game may need. Delays are important
 * for also status updates on sprites or the game itself.
 * 
 * @author Richard DeSilvey
 */
public final class Delay implements Serializable {

    private int delayTime;
    
    private int maxDelay;
    
    private boolean reset;
    
    /**
     * Default constructor
     */
    public Delay(){
        
        delayTime = 0;
        
        maxDelay = 0;
        
        reset = false;
        
    }
    /**
     * Initializes this delay with the number of frames to delay at.
     */
    public Delay(int frames){
        
        assert frames <= 0 : "Invalid Delay Time";
        
        delayTime = 0;
        
        this.maxDelay = frames;
        
        reset = false;
    }

    /**
     * When the delay is finished, this boolean value
     * will determine whether or not to reset this delay.
     * By default the reset is turned off.
     */
    public void allowReset(boolean b){
        reset = b;
    }
    
    /**
     * Specifies the number of frames to delay and returns 1 if the delay has been met
     * or 0 if the delay has not yet been reached.
     */
    public int delay(int frames){
        
        assert frames <= 0 : "Invalid Delay Time";
        
        if (++delayTime >= frames){
            
            if (!reset)
                
                delayTime = frames;
            
            else
                
                reset();
            
            return 1;
            
        }else
            
            return 0;
    }
    
    /**
     * Test delay only works if you initialize your delay object
     * with a delay value given in the constructor's parameter.
     * This does not increment the delay.
     * @return 1 if the delay is met, 0 if the delay is not met.
     */
    public int testDelay(){
        
        if (delayTime >= maxDelay){
            
            return 1;
            
        }
        
        return 0;
        
    }
    
    /**
     * Resets the delay. This method is not needed if you are allowing
     * the delay to reset itself.
     */
    public void reset(){
        
        delayTime = 0;
        
    }

    /**
     * Calls the delay and uses the initial delay value passed into
     * the constructor of this delay.
     */
    public int delay(){
        
        return delay(maxDelay);
        
    }

}


