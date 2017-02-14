
package EasyClip2D.gui;

import EasyClip2D.core.ECEngine.JWindowListener;
import EasyClip2D.util.Tools;
import java.awt.event.*;


/**
 * The GameFrame is an abstract object used to define
 * the FullGameWindow and GameWindow objects.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public abstract class GameFrame extends javax.swing.JFrame {

    protected GraphicsPanel graphicsPanel;
    
    public class FrameListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            
            Tools.ec_engine.done = true;
            
        }
        
    }
    
    public final void initListeners(KeyListener kl, MouseListener ml, MouseMotionListener mml, JWindowListener jwl, MouseWheelListener mwl, ComponentListener cl) {
        
        this.addKeyListener(kl);
        
        this.graphicsPanel.addMouseListener(ml);
        
        this.graphicsPanel.addMouseMotionListener(mml);
        
        this.addWindowListener(jwl);
        
        this.graphicsPanel.addMouseWheelListener(mwl);
        
        this.addComponentListener(cl);
        
    }

    public final GraphicsPanel getPanelFrame() {
        
        return graphicsPanel;
        
    }
    
    /**
     * Sets the sprite manager object
     */
    public final void setGraphicsPanel(GraphicsPanel manager){
        graphicsPanel = manager;
    }

    
}
