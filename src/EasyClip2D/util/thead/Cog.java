

package EasyClip2D.util.thead;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Cog is an object used to manage a unique process defined by the
 * user. This class enables your program to have multitasking.
 * 
 * Try to not add too many Cogs, this will eventually become redundant.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public class Cog extends Thread {

    protected boolean running, done, synchCog, step;
    
    protected CogEvent pt;
    
    protected float cycle;
    
    private double currentCycle;

    /**
     * Error10 or 1200 is a Cog error where an internal exception
     * was thrown within your program and was not caught.
     * This can be any Exception during a frame step for this Cog.
     */
    public static final int ERROR10 = 1200;
    
    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")
    public Cog(float cycleTime){
        
        this(null, cycleTime);
        
    }
    
    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")
    public Cog(CogEvent pt, float cycleTime){
        
        this.pt = pt;
        
        running = done = step = false;
        
        synchCog = true;
        
        cycle = cycleTime;
        
        start();
    }
    
    public final void synchCog(boolean b){
        
        synchCog = b;
        
    }
    
    public final boolean synch(){
        return synchCog;
    }
    
    /**
     * @return the frames per second that are processed.
     */
    public final double getCurrentCycle(){
        return currentCycle;
    }
    
    public final void setCycle(float cycle){
        
        this.cycle = cycle;
        
    }
    
    /**
     * @return true if this thread is running.
     */
    public final boolean isRunning(){
        return running;
    }
    
    /**
     * Stops the thread from running
     */
    public final void disable(){
        running = false;
    }
    
    /**
     * Runs the thread again
     */
    public final void enable(){
        running = true;
    }
    
    public final void step(){
        
        step = true;
        
    }
    
    public final void manualStep(){
        
        try {
            
            pt.update();
            
            currentCycle = cycle(cycle);
            
        } catch (Exception e) {
            
            System.err.println("Cog Error!");
            
            e.printStackTrace();
            
            System.exit(ERROR10);
            
        }
        
    }

    /**
     * This is an inner public method of the Thread class and doesn't
     * need to be called.
     */
    @Override 
    public final void run(){
        
        try {
            
            while (!done) {
                
                if (running) {
                    
                    manualStep();
                    
                }else if (step){
                    
                    manualStep();
                    
                    step = false;
                    
                }
                
            }
            
        } catch (Exception e){
            
            e.printStackTrace();
            
        }
    }
    
    public final static double cycle(float time){
        
        double mili = System.currentTimeMillis(), endMili = 0;
        
        while(time >= 0){
            
            time -= 0.001f;
            
            try {
                
                Thread.sleep(1);
                
            } catch (InterruptedException ex) {
                
                Logger.getLogger(Cog.class.getName()).log
                        (Level.SEVERE, null, ex);
                
            }
            
        }
        
        endMili = System.currentTimeMillis();
        
        return (endMili - mili) / 1000d;
        
    }

}
