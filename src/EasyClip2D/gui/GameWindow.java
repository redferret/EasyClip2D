
package EasyClip2D.gui;

import javax.swing.JFrame;

/**
 * GameWindow sets up a GraphicsPanel object for your game.
 * To Center your window simply call the <code>
 * getWindow().setLocationRelativeTo(null);</code> method. 
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public class GameWindow extends GameFrame {

    public GameWindow() {
        
//        prevRect = new SpriteRect();
        
    }
    
    public GameWindow(float width, float height, String title, java.awt.Color background) {
        
        graphicsPanel = new GraphicsPanel(0, 0, (int)width, (int)height, background);
        
        add(graphicsPanel);
        
        addWindowListener(new FrameListener());
        
        setSize((int)width, (int)height);
        
        setLayout(null);
        
        setLocationRelativeTo(null);
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        setTitle(title);
        
    }
    
    /**
     * The name of the method is a bit confusing but it
     * redefines the GraphicsPanel's original scale dimensions.
     * This is not the same as setting the dimensions of the GraphicsPanel.
     */
//    public void setPanelAlignment(int width, int height){
//        
//        graphicsPanel.setDimensions(width, height);
//        
//        graphicsPanel.getOrgRect().setRect(0, 0, width, height);
//        
//        graphicsPanel.centerPanel(getBounds().getWidth(), getBounds().getHeight(), 0, 0);
//        
//    }
//    
//    public void setPanelAlignment(int width, int height, int offsetX, int offsetY){
//        
//        graphicsPanel.setDimensions(width, height);
//        
//        graphicsPanel.getOrgRect().setRect(0, 0, width, height);
//        
//        graphicsPanel.centerPanel(getBounds().getWidth(), getBounds().getHeight(), offsetX, offsetY);
//        
//    }
    

    /**
     * Does not perform the same as the GraphicsPanel method which
     * is the exact same name. The GramFrame performs the
     * resize for the GraphicsPanel.
     */
//    public final void resizePanel(float width, float height, int offsetX, int offsetY){
//        
//        prevRect.setWidth(curRect.getWidth());
//        
//        prevRect.setHeight(curRect.getHeight());
//
//        
//        curRect.setWidth(width);
//        
//        curRect.setHeight(height);
//
//        if (graphicsPanel != null){
//
//            int dx = (int)(curRect.getWidth() - prevRect.getWidth());
//            
//            graphicsPanel.resizePanel((int)graphicsPanel.getBounds().getWidth() + dx, 
//                    (int)graphicsPanel.getBounds().getHeight() + 
//                    (int)((graphicsPanel.getBounds().getHeight() / 
//                    graphicsPanel.getBounds().getWidth())*dx));
//            
//            graphicsPanel.centerPanel(getBounds().getWidth(), getBounds().getHeight(), offsetX, offsetY);
//
//        }
//
//    }


}




