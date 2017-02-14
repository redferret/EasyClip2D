
package EasyClip2D.geom;

/**
 * Sprite vectors were originally created for a physics engine,
 * but since Box2D was implemented the sprite vector is
 * hardly ever used.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class Vec2d {

    public float xi, yj;
    
    public Vec2d() {
        
        xi = 0;
        
        yj = 0;
        
    }
    
    public Vec2d(float xi, float yi) {
        
        this.xi = xi;
        
        this.yj = yi;
        
    }

    public void setXi(float xi) {
        
        this.xi = xi;
        
    }

    public void setYj(float yj) {
        
        this.yj = yj;
        
    }

    public float getXi() {
        
        return xi;
        
    }

    public float getYj() {
        
        return yj;
        
    }
    
    public float getMagnitude(){
        
        return (float)Math.sqrt((xi*xi) + (yj*yj));
        
    }

    public float dot(Vec2d v){
        
        return (xi * v.xi) + (yj * v.yj);
        
    }
    
    public void zero(){
        
        xi = 0;
        
        yj = 0;
        
    }

    public Vec2d mul(float value){

        float xi = this.xi * value,
              yi = this.yj * value;

        return new Vec2d(xi, yi);

    }

    public static Vec2d addVectors(Vec2d v1, Vec2d v2){

        return new Vec2d((v1.xi + v2.xi), (v1.yj + v2.yj));

    }

    public Vec2d div(float value){

        float xi = this.xi / value,
              yj = this.yj / value;

        return new Vec2d(xi, yj);

    }

    
}
