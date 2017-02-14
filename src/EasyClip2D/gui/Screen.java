

package EasyClip2D.gui;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;

/**
 * The screen class is used for the FullGameWindow display.
 * It puts your games into full screen mode, rather than
 * having a windowed frame.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class Screen {

    private GraphicsDevice myDevice;
    
    private DisplayMode dm;

    /**
     * Constructs a new Screen object by setting up the resolution
     * and the bit size.
     */
    public Screen(int resWidth, int resHeight, int bitSize){
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        
        myDevice = ge.getDefaultScreenDevice();
        
        dm = new DisplayMode(resWidth, resHeight, bitSize, 
                DisplayMode.REFRESH_RATE_UNKNOWN);
        
    }

    /**
     * Places the game into Full Screen mode if the Display Mode is
     * supported on this computer.
     */
    public final void gotoFullScreen(FullGameWindow ge){

        ge.setUndecorated(true);

        ge.setResizable(false);

        myDevice.setFullScreenWindow(ge);

        if (dm != null && myDevice.isDisplayChangeSupported()){
            try {
                
                myDevice.setDisplayMode(dm);
                
            }catch (Exception e){
                
                e.printStackTrace();
                
                System.exit(0);
                
            }
        }
        
    }
    /**
     * Returns the GraphicsDevice's full screen. 
     */
    public Window getFullScreenWindow(){
        
        return myDevice.getFullScreenWindow();
        
    }
    /**
     * Disposes and closes the Full Screen mode.
     */
    public final void exitFullScreen(){
        
        Window w = getFullScreenWindow();

        if (w != null){
            w.dispose();
        }
        
        myDevice.setFullScreenWindow(null);
    }
}
