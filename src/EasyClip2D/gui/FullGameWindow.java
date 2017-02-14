
package EasyClip2D.gui;

import EasyClip2D.geom.SpriteRect;
import javax.swing.JFrame;

/**
 * Full game window is used to set up a full screen game.
 * This is passed into the ECEngine's constructor method.
 * If the resolution is not supported the game engine will
 * exit the program.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public class FullGameWindow extends GameFrame {
    
    private Screen screen;

    public FullGameWindow(){
        
    }
    
    public FullGameWindow(int resWidth, int resHeight, int bitSize, java.awt.Color background) {
        
        graphicsPanel = new GraphicsPanel(0, 0, resWidth, resHeight, background);
        
        add(graphicsPanel);
        
        setLayout(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        initScreen(resWidth, resHeight, bitSize);
        
        
    }
    
    private void initScreen(int resWidth, int resHeight, int bitSize){

        screen = new Screen(resWidth, resHeight, bitSize);

        screen.gotoFullScreen(this);

    }
    
    public void exitFullScreen(){
        
        if (screen != null){
            screen.exitFullScreen();
        }
        
    }
    

}



