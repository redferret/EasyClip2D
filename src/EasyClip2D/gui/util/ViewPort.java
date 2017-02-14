
package EasyClip2D.gui.util;

import EasyClip2D.Box2D.org.jbox2d.common.Vec2;
import EasyClip2D.geom.SpritePoint;
import EasyClip2D.util.Tools;
import java.awt.event.MouseEvent;

/**
 * The ViewPort is used for Easy Clip when rendering
 * sprites in a virtual world, this allows you to
 * pan and zoom in your world.
 * 
 * @author Richard DeSilvey
 */
public class ViewPort {

    private SpritePoint pos, org_dim, cur_dim, n_dim;
    
    public ViewPort(float width, float height){

        this(0, 0, width, height);
        
    }
    
    public ViewPort(float x, float y, float width, float height){
        
        pos = new SpritePoint(x, y);
        
        org_dim = new SpritePoint(width, height);
        
        cur_dim = new SpritePoint(width, height);
        
        n_dim = new SpritePoint();
        
    }
    
    /**
     * If your screen is a different size than your panel frame, 
     * then you can subtract off a certain amount to compensate 
     * for the size difference.
     * @param x
     * @param y 
     */
    public void setOffSetDimensions(float x, float y){
        
        n_dim.setLocation(x, y);
        
    }
    
    /**
     * Changes the position of the view port.
     */
    public void moveTo(float x, float y){
        
        waitToRender();
        
        pos.setLocation(-x, -y);
        
    }
    
    /**
     * Pans the camera around your world by an amount
     * specified by x and y
     */
    public void scroll(float x, float y){
        
        waitToRender();
        
        pos.translate(x, y);
        
    }
    
    /**
     * Changes the scale of the viewport. 
     * The scale would be 50% if the view port was 1000x1000
     * and you scaled to 500x500
     */
    public void scaleTo(float w, float h){
        
        cur_dim.setLocation(w, h);
        
    }
    
    /**
     * Scale to a specific percentage
     */
    public void scale(float scale){
        
        cur_dim.setLocation(scale * org_dim.x, scale * org_dim.y);

    }
    
    /**
     * This method performs tiny increments of zooming
     * depending on sign and the value of n. mul is
     * the amount to scale at. 1.0f means no scaling should
     * happen if n is 1 or -1.
     * This method is best used for the mouse wheel.
     */
    public void scale(int n, float mul){
        
        float d = n * mul;
        
        d = (n > 0) ? 1 / d : -d;

        float w = cur_dim.x * d,
              h = cur_dim.y * d;
        
        cur_dim.setLocation(w, h);
        
    }
    
    public SpritePoint getScaleValue(){
        
        return new SpritePoint(cur_dim.x / org_dim.x, cur_dim.y / org_dim.y);
        
    }
    
    public SpritePoint getReciprocalScaleValue(){
        
        return new SpritePoint(org_dim.x / cur_dim.x, org_dim.y / cur_dim.y);
        
    }
    
    public SpritePoint getRadial(){

        return pos;
        
    }
    
    public SpritePoint getCurrentDimensions(){
        
        return cur_dim;
        
    }
    
    public SpritePoint getInitialDimensions(){
        
        return org_dim;
        
    }
    
    /**
     * Converts the position to view port coordinates
     */
    public void transformToViewPort(SpritePoint p){
        
        p.translate(-pos.x, -pos.y);
        
    }
    
    /**
     * Changes the position from view port coordinates
     */
    public void transformFromViewPort(SpritePoint p){
        
        p.translate(pos.x, pos.y);
        
    }
    
    /**
     * Changes the position into viewport coordinates,
     * this is already performed by the engine.
     */
    public void translateToViewPort(Vec2 p){
        
        p.set(p.x - pos.x, p.y - pos.y);
        
    }
    
    
    public SpritePoint getScaledPoint(SpritePoint sp){
        
        return getScaledPoint(sp.x, sp.y);
        
    }
    
    public SpritePoint getOffSetDimensions(){
        
        return n_dim;
        
    }
    
    /**
     * Transforms sprite positions that were called via getPosition() method
     * back into view port coordinates.
     * @param x position in the x direction
     * @param y position in the y direction
     * @return New SpritePoint representing view port coordinates
     */
    public SpritePoint getScaledPoint(float x, float y){
        
        SpritePoint mPos = new SpritePoint(x, y);
        
        // Translate to the origin
        
        if (!Tools.ec_engine.getWindow().getPanelFrame().resizeToFrame()){
            
            mPos.translate(-(Tools.ec_engine.getWindow().getPanelFrame().getWidth() - n_dim.x) / 2, 
                   -(Tools.ec_engine.getWindow().getPanelFrame().getHeight() - n_dim.y) / 2);
            
        }else {
            
            mPos.translate(-(Tools.ec_engine.getWindow().getWidth() - n_dim.x) / 2, 
                   -(Tools.ec_engine.getWindow().getHeight() - n_dim.y) / 2);
            
        }
        
        mPos.setLocation((mPos.x * org_dim.x) / cur_dim.y, (mPos.y * org_dim.y) / cur_dim.y);
        
        return mPos;
        
    }
    
    public SpritePoint getScaledMouse(MouseEvent e){
        
        return getScaledPoint(e.getX(), e.getY());
        
    }
    
    public SpritePoint getTranslatedMouse(MouseEvent e){
        
        SpritePoint mPos = new SpritePoint(e.getX(), e.getY());

        mPos.translate(-pos.x, -pos.y);
        
        return mPos;
        
    }
    
    private void waitToRender(){
        
        while (Tools.ec_engine.getWindow().getPanelFrame().rendering()){}
        
    }

}
