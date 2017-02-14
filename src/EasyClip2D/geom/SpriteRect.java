package EasyClip2D.geom;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * A Sprite Rect is used to create a rectangle boundary for
 * sprites. This is not used for collision checking.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public class SpriteRect implements Serializable {

    public float x;
    
    public float y;
    
    public float width;
    
    public float height;

    public SpriteRect() {
    }

    public SpriteRect(SpritePoint p, float w, float h) {
        setRect(p.x, p.y, w, h);
    }

    public SpriteRect(float x, float y, float w, float h) {
        setRect(x, y, w, h);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
    
    public float getCenterX(){
        return x + (width / 2);
    }
    
    public float getCenterY(){
        return y + (height / 2);
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setWidth(float width) {
        this.width = width;
    }
    
    public void setDim(float w, float h){
        
        width = w;
        
        height = h;
        
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
    
    public void setLocation(float x, float y){
        
        this.x = x;
        
        this.y = y;
        
    }

    public boolean isEmpty() {
        return (width <= 0.0f) || (height <= 0.0f);
    }

    public void setRect(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public void setRect(Rectangle2D r) {
        this.x = (float) r.getX();
        this.y = (float) r.getY();
        this.width = (float) r.getWidth();
        this.height = (float) r.getHeight();
    }

    public boolean contains(float x, float y) {
        float x0 = getX();
        float y0 = getY();
        return (x >= x0
                && y >= y0
                && x < x0 + getWidth()
                && y < y0 + getHeight());
    }

    public boolean intersects(float x, float y, float w, float h) {
        if (isEmpty() || w <= 0 || h <= 0) {
            return false;
        }
        float x0 = getX();
        float y0 = getY();
        return (x + w > x0
                && y + h > y0
                && x < x0 + getWidth()
                && y < y0 + getHeight());
    }

    public boolean contains(float x, float y, float w, float h) {
        if (isEmpty() || w <= 0 || h <= 0) {
            return false;
        }
        float x0 = getX();
        float y0 = getY();
        return (x >= x0
                && y >= y0
                && (x + w) <= x0 + getWidth()
                && (y + h) <= y0 + getHeight());
    }
}
