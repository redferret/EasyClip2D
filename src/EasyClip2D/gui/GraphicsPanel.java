

package EasyClip2D.gui;

import EasyClip2D.core.ECEngine;
import EasyClip2D.geom.SpritePoint;
import EasyClip2D.gui.graphic.image.ECImage;
import EasyClip2D.sprite.util.SpriteManager;
import EasyClip2D.sprite.SpriteObject;
import EasyClip2D.gui.util.RenderList;
import EasyClip2D.gui.util.RenderOrder;
import EasyClip2D.sprite.Sprite;
import EasyClip2D.sprite.Sprite2D;
import EasyClip2D.util.Tools;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The GraphicsPanel is a JPanel that has a bit more added to it.
 * It can be manipulated as a JPanel but has other methods
 * that deal with drawing sprites and other game graphics
 * onto this panel. The GameEngine also resizes and
 * changes the position of the panel based off of certain
 * specifications.
 *
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class GraphicsPanel extends javax.swing.JPanel {

    /**
     * prevPoint declares a distance value used when 
     * a user is able to move the panel.
     */
    private SpritePoint prevPoint;
    
    /**
     * The center point of this panel
     */
    private SpritePoint centerPoint;
    
    private BufferedImage cachedBackground, cachedFrontground, cachedCenter;
    
    /**
     * The sprite manager that is being used in a game
     */
    private SpriteManager manager;
    
//    private RenderList bg_sprites, df_sprites, st_sprites;
    
    private boolean scaleGraphics, rendering, cacheBackground, 
            cacheCenter, cacheFrontground, reCacheCenter, reCacheBackground, reCacheFrontground;
    
    private int imageType;
    
    private boolean resizeToFrame;

    public GraphicsPanel(int x, int y, int xSize, int ySize, Color color) {
        
        prevPoint = new SpritePoint();
        
        centerPoint = new SpritePoint();
        
        setBounds(x, y, xSize, ySize);
        
        setBackground(color);
        
        setLayout(null);
        
        setDoubleBuffered(true);
        
        cacheBackground = cacheFrontground = rendering = 
                reCacheCenter = reCacheBackground = reCacheFrontground = false;
        
        imageType = BufferedImage.TYPE_4BYTE_ABGR;
        
        scaleGraphics = resizeToFrame = true;
        
//        bg_sprites = new RenderList();
//    
//        df_sprites = new RenderList();
//    
//        st_sprites = new RenderList();
        
    }
    
//    public void addSprite(SpriteObject s){
//        
//        switch (s.getSpriteType()){
//            
//            case Tools.TYPE_BACKGROUND:
//                bg_sprites.add(s);
//                break;
//                
//            case Tools.TYPE_STATIC:
//                st_sprites.add(s);
//                break;
//                
//            case Tools.TYPE_DEFAULT:
//            case Tools.TYPE_GRID:
//                df_sprites.add(s);
//                break;
//            default:
//                throw new IllegalStateException("Sprite Type Undefined");
//        }
//        
//    }
    
//    public void killSprites(){
//        
//        bg_sprites.removeAll();
//        
//        df_sprites.removeAll();
//        
//        st_sprites.removeAll();
//        
//    }
    
//    public RenderList getRenderList(int index){
//        
//        switch (index){
//            
//            case Tools.TYPE_BACKGROUND:
//                return bg_sprites;
//                
//            case Tools.TYPE_DEFAULT:
//            case Tools.TYPE_GRID:
//                return df_sprites;
//                
//            case Tools.TYPE_STATIC:
//                return st_sprites;
//                
//            default:
//                throw new IndexOutOfBoundsException("Index " + index + " is not a valid index");   
//            
//        }
//        
//    }
    
    public void cacheCenter(boolean cache){
        
        cacheCenter = cache;
        
    }
    
    public void refreshCenter(){
        
        reCacheCenter = true;
        
    }
    
    public void cacheBackground(boolean cache){
        
        cacheBackground = cache;
        
    }
    
    public void refreshBackground(){
        
        reCacheBackground = true;
        
    }
    
    public void cacheFrontground(boolean cache){
        
        cacheFrontground = cache;
        
    }
    
    public void refreshFrontground(){
        
        reCacheFrontground = true;
        
    }
    
    /**
     * Sets which SpriteManager to use for drawing sprites onto this
     * panel. 
     */
    public void setSpriteManager(SpriteManager manager){
        this.manager = manager;
    }
    
    /**
     * 
     * @return The sprite manager for this panel
     */
    public SpriteManager getSpriteManager(){
        return manager;
    }
    
    /**
     * The spriteImage type is important to determine.
     * If the game uses alpha color bits, then you 
     * must specify it. The spriteImage type is given within
     * the BufferedImage class.
     */
    public void setImageType(int imageType){
        this.imageType = imageType;
    }
    
    /**
     * @return The numerical value of the spriteImage type
     */
    public int getImageType(){
        return imageType;
    }
    
    /**
     * This method is called by the game engine. It's mechanics are used
     * when free window constraints are turned on.
     */
    public final void updatePrevLocation(){
        
        prevPoint.setLocation((float)getBounds().getX(), (float)getBounds().getY());
        
    }
    
    /**
     * Sets the center point for the panel
     */
    public void setCenterPoint(float width, float height){
        
        centerPoint.setLocation((float)((width / 2.0) - getBounds().getWidth() / 2.0) - 7, 
                (float)((height / 2.0) - getBounds().getHeight() / 2.0) - 20);
        
    }
    
    /**
     * It's always best to leave the scale to length to true.
     * If the graphics are rendered and the panel
     * resizes, then the graphics remain un-scaled.
     */
    public void scaleToSize(boolean scale){
        scaleGraphics = scale;
    }
    
    /**
     * 
     * @return If the panel is scaling the graphics
     */
    public boolean isScaled(){
        return scaleGraphics;
    }

    
    /**
     * Renders the graphics onto this panel by drawing all
     * the sprites in their special order
     */
    public final synchronized void paintComponent(Graphics graphics) {
        
        Graphics2D main_g2d = (Graphics2D) graphics;

        rendering = true;
        
        super.paintComponent(main_g2d);
        
        if (manager != null){
            
            if (!cacheBackground){
                
                drawSprites(Tools.ec_engine.getRenderList(Tools.TYPE_BACKGROUND), main_g2d);
            
            }else {
                
                if (reCacheBackground){
                    
                    cachedBackground = new BufferedImage(Tools.ec_engine.getWindow().getWidth(), 
                            Tools.ec_engine.getWindow().getHeight(), BufferedImage.TYPE_INT_ARGB);
                    
                    cachedBackground = ECImage.toCompatibleImage(cachedBackground);
                    
                    drawSprites(Tools.ec_engine.getRenderList(Tools.TYPE_BACKGROUND), cachedBackground.createGraphics());
                    
                    reCacheBackground = false;
                    
                }
                
                main_g2d.drawImage(cachedBackground, 0, 0, this);
                
            }
            
            if (!cacheCenter){
                
                drawSprites(Tools.ec_engine.getRenderList(Tools.TYPE_DEFAULT), main_g2d);
            
            }else {
                
                if (reCacheCenter){
                    
                    cachedCenter = new BufferedImage(Tools.ec_engine.getWindow().getWidth(), 
                            Tools.ec_engine.getWindow().getHeight(), BufferedImage.TYPE_INT_ARGB);
                    
                    cachedCenter = ECImage.toCompatibleImage(cachedCenter);
                    
                    drawSprites(Tools.ec_engine.getRenderList(Tools.TYPE_DEFAULT), cachedCenter.createGraphics());
                    
                    reCacheCenter = false;
                    
                }
                
                main_g2d.drawImage(cachedCenter, 0, 0, this);
                
            }
            
            if (!cacheFrontground){
                
                drawSprites(Tools.ec_engine.getRenderList(Tools.TYPE_STATIC), main_g2d);
            
            }else {
                
                if (reCacheFrontground){
                    
                    cachedFrontground = new BufferedImage(Tools.ec_engine.getWindow().getWidth(), 
                            Tools.ec_engine.getWindow().getHeight(), BufferedImage.TYPE_INT_ARGB);
                    
                    cachedFrontground = ECImage.toCompatibleImage(cachedFrontground);
                    
                    drawSprites(Tools.ec_engine.getRenderList(Tools.TYPE_STATIC), cachedFrontground.createGraphics());
                    
                    reCacheFrontground = false;
                    
                }
                
                main_g2d.drawImage(cachedFrontground, 0, 0, this);
                
            }
            
        }
        
        try {
            
            Tools.ec_engine.postRender(main_g2d);
            
        } catch (java.lang.UnsatisfiedLinkError ule) {
            
        } finally {
            
            rendering = false;
        
            main_g2d.dispose();
            
        }
        
        
        
    }
    
    public boolean rendering(){
        return rendering;
    }
    
    private void drawSprites(RenderList spts, Graphics2D g2d){
        
        try {
            
            RenderOrder renderOrder = spts.getRenderOrder();
            
            SpriteObject list = spts.getDefaultOrder(), temp;
            
            while (list != null) {
                
                temp = list.getNext();
                
                if (!list.isDead()) {
                    
                    if (checkOffScreen(list) && list.renderSprite()){

                        list.draw(g2d);
                        
                    }
                    
                }else
                    
                    list.delete();
                
                list = temp;
                
            }
            
            ArrayList<SpriteObject> lists[] = renderOrder.getLists();
            
            for (ArrayList<SpriteObject> spriteList : lists){
                
                for (int i = 0; i < spriteList.size(); i++){
                    
                    list = spriteList.get(i);
                    
                    if (!list.isDead()) {

                        if (checkOffScreen(list) && list.renderSprite()) {

                            list.draw(g2d);

                        }

                    }
                    
                }
                
            }
            
        } catch (NullPointerException ne)  {
        
        }
        
    }
    
    private boolean checkOffScreen(SpriteObject s){
        
        SpritePoint pos;
        
        SpritePoint bounds = SpritePoint.copyOf(s.getBounds());
        
        if (s.renderOffScreen()){
            
            return true;
            
        }
        
        if (s instanceof Sprite){
            
            pos = SpritePoint.copyOf(s.getPosition());
            
            if (s.getSpriteType() == Tools.TYPE_GRID){
                
                bounds.negate();
                
            }
            
        }else if (s instanceof Sprite2D){
            
            bounds.negate();
            
            pos = SpritePoint.copyOf(((Sprite2D)s).getPosition());
            
        }else{
            
            return false;
            
        }
        
        SpritePoint rad = SpritePoint.copyOf(ECEngine.port.getRadial());
            
        SpritePoint scaler = ECEngine.port.getReciprocalScaleValue();

        SpritePoint dim = new SpritePoint((Tools.ec_engine.getWindow().getWidth() / 2) * scaler.x,
                (Tools.ec_engine.getWindow().getHeight() / 2) * scaler.y);

        pos.translate(rad.x, rad.y);

        if (dim.x - (Math.abs(pos.x) + (bounds.x / 2)) <= 0){

            return false;

        }else if (dim.y - (Math.abs(pos.y) + (bounds.y / 2)) <= 0){

            return false;

        }
            
        return true;
        
    }

    public boolean resizeToFrame() {
        
        return resizeToFrame;
        
    }
    
    public void resizeToFrame(boolean b){
     
        resizeToFrame = b;
        
    }
    
}
