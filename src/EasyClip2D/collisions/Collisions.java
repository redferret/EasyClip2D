
package EasyClip2D.collisions;

import EasyClip2D.geom.*;
import EasyClip2D.sprite.Sprite;

/**
 *
 * The Collisions interface contains two different kinds
 * of Collisions Algorithms you can use for your games.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public interface Collisions {
    
    /**
     * This method is required for each collision algorithm so that the 
     * game engine can process your algorithm. Two sprite objects must
     * be tested within your code and a boolean value of whether 
     * a collision has been made or not must be returned.
     */
    public boolean checkCollision(Sprite collider, Sprite collidee);
    
    /**
     * A pre-made collision algorithm that takes a polygon of each sprite
     * and checks for an intersection between those two polygons. Higher
     * resolution polygons take more processing time.
     */
    public final class PolyCollision implements Collisions {

        public boolean checkCollision(Sprite collider, Sprite collidee) {

            if (checkPCR(collider.getCenter(), collider.getPotentialCollisionRadius(), 
                    collidee.getCenter(), collidee.getPotentialCollisionRadius())){
                
                updateBoundaries(collider);
                
                updateBoundaries(collidee);
                
                try{
                    
                    collider.nearestSprite(collidee);
                    
                }finally{
                    
                    return collider.getCollisionBounds().intersects(collidee.getCollisionBounds());
                    
                }
                
            }else{
                
                return false;
                
            }
        }
        
        private void updateBoundaries(Sprite sprite){
            
            sprite.getCollisionBounds().move(sprite.getPosition());

            if (sprite.getCollisionBounds().getPrevRotation() != sprite.getSpriteImage().getAngleRadians()){
                
                sprite.getCollisionBounds().rotateBound(sprite.getSpriteImage().getGraphicsRender().getRotatePoint(), 
                        sprite.getSpriteImage().getAngleRadians() - sprite.getCollisionBounds().getPrevRotation());
                
                sprite.getCollisionBounds().setPrevRotation(sprite.getSpriteImage().getAngleRadians());
                
            }
            
        }

        private boolean checkPCR(SpritePoint center1, double pcr1, SpritePoint center2, double pcr2) {
            
            double dx1 = center1.getX(), dy1 = center1.getY();
            double dx2 = center2.getX(), dy2 = center2.getY();
            
            dx1 -= dx2;
            
            dy1 -= dy2;
            
            return (Math.sqrt((dx1*dx1) + (dy1 * dy1)) <= (pcr1 + pcr2));
            
        }

    }
    
    /**
     * Nearest Detection is used for collidees. Since colliders
     * are the main users for the collision checking class.
     * This algorithm is used to return all sprites
     * that fall within a collidee's PCR one at a time
     * during each frame.
     */
    public final class NearestDetection implements Collisions {

        @Override
        public boolean checkCollision(Sprite collider, Sprite collidee) {
            
            if (checkPCR(collider.getCenter(), collider.getPotentialCollisionRadius(), 
                    collidee.getCenter(), collidee.getPotentialCollisionRadius())){

                try{
                    
                    collider.nearestSprite(collidee);
                    
                }finally{
                    
                    return false;
                    
                }
                
            }else{
                
                return false;
                
            }
            
        }
        
        private boolean checkPCR(SpritePoint center1, double pcr1, SpritePoint center2, double pcr2) {
            
            double dx1 = center1.getX(), dy1 = center1.getY();
            double dx2 = center2.getX(), dy2 = center2.getY();
            
            dx1 -= dx2;
            
            dy1 -= dy2;
            
            return (Math.sqrt((dx1*dx1) + (dy1 * dy1)) <= (pcr1 + pcr2));
            
        }
        
    }

}
