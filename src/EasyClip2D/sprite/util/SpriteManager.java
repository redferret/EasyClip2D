
package EasyClip2D.sprite.util;

import EasyClip2D.collisions.SpriteCollisionList;
import EasyClip2D.collisions.Collisions;
import EasyClip2D.sprite.Sprite;
import EasyClip2D.sprite.SpriteObject;
import EasyClip2D.util.SpriteList;
import EasyClip2D.util.Tools;
import EasyClip2D.util.thead.CogEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The SpriteManager is not well documented because it's used solely by
 * the GameEngine. A user wont need to deal with this object on
 * a regular basis.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class SpriteManager {
   
//    private SpriteList backgroundSprites;
//    
//    private SpriteList collisionSprites;
//    
//    private SpriteList staticSprites;
    
    public class SpriteCollision implements CogEvent {
        
        private List<SpriteCollisionList> list;
        
        public SpriteCollision(){
            
            list = new ArrayList<SpriteCollisionList>();
            
        }
        
        public void addList(SpriteCollisionList list){
            
            this.list.add(list);
            
            Collections.sort(this.list);
            
        }
        
        public void removeList(SpriteCollisionList list){
            
            this.list.remove(list);
            
        }
        
        public List<SpriteCollisionList> getList(){
            
            return list;
            
        }
        
        public void killSprites(){
            
            for (int i = 0; i < list.size(); i++){
                
                list.get(i).collidees().clear();
                
                list.get(i).colliders().clear();
                
                if (list.get(i).getGrid() != null){
                    
                    list.get(i).getGrid().clearAllSprites();
                    
                }
                
            }
            
        }
        
        public void update(){
            
            for (int i = 0; i < list.size(); i++){
                
                if (list.get(i).getGrid() != null){
                    
                    gridCollisions(list.get(i).gridColliders(), list.get(i).getGrid());
                    
                }
                
                spriteCollisions(list.get(i).colliders(), list.get(i).collidees());
                
            }
            
        }
        
    }
    
    public class BackgroundSprites implements CogEvent {
        
        public void update(){
            
            updateSprites(Tools.ec_engine.getSpriteList(Tools.TYPE_BACKGROUND));
            
        }
        
    }
    
    public class CollisionSprites implements CogEvent {
        
        public void update(){
            
            updateSprites(Tools.ec_engine.getSpriteList(Tools.TYPE_DEFAULT));
            
        }
    }
    
    public class StaticSprites implements CogEvent {
        
        public void update(){
            
            updateSprites(Tools.ec_engine.getSpriteList(Tools.TYPE_STATIC));
            
        }
    }
    
    public SpriteManager() {
        
//        backgroundSprites = new SpriteList();
//        
//        collisionSprites = new SpriteList();
//        
//        staticSprites = new SpriteList();
        
    }
    
    public final BackgroundSprites getBackgroundThread(){
        return new BackgroundSprites();
    }
    
    public final CollisionSprites getSpriteCollisionThread(){
        return new CollisionSprites();
    }
    
    public final StaticSprites getStaticThread(){
        return new StaticSprites();
    }
    
    public final SpriteCollision getCollisionManager() {
        return new SpriteCollision();
    }
    
//    public synchronized void killSprites() {
//        
//        backgroundSprites.clear();
//        
//        staticSprites.clear();
//        
//        collisionSprites.clear();
//        
//    }

//    public final SpriteList getDefaultSprites() {
//        return collisionSprites;
//    }
//
//    public final SpriteList getStaticSprites() {
//        return staticSprites;
//    }
//
//    public final SpriteList getBackgroundSprites() {
//        return backgroundSprites;
//    }
    
//    public final void addSprite(SpriteObject s){
//        
//        switch (s.getSpriteType()){
//            
//            case Tools.TYPE_BACKGROUND:
//                backgroundSprites.addSprite(s);
//                break;
//                
//            case Tools.TYPE_STATIC:
//                staticSprites.addSprite(s);
//                break;
//                
//            case Tools.TYPE_DEFAULT:
//            case Tools.TYPE_GRID:
//                collisionSprites.addSprite(s);
//                break;
//                
//            default:
//                throw new IllegalStateException("Sprite Type Undefined");
//        }
//        
//    }
    
    public final static synchronized void gridCollisions(ConcurrentLinkedQueue<SpriteObject> gridColliders, SpriteGrid grid){
        
        Sprite obj = (Sprite)gridColliders.peek(), testSprite, temp;
            
        Iterator<Sprite> tList;
        
        while (obj != null) {

            temp = (Sprite)obj.getNext();

            if (!obj.isDead()) {

                tList = grid.findPotentialCollisions(obj).iterator();
                
                while (tList.hasNext()){
                
                testSprite = tList.next();
                
                if (Sprite.checkCollision(obj, testSprite)){
                    
                    try {
                        
                        obj.collisionDetected(testSprite);
                        
                    } finally {}
                    
                }
                
            }

            }else

                obj.delete();

            obj = temp;

        }
        
    }
    
    public final static void spriteCollisions(ConcurrentLinkedQueue<SpriteObject> colliders, ConcurrentLinkedQueue<SpriteObject> collidees){
        
        Sprite collider = (Sprite)colliders.peek(),
               collidee = (Sprite)collidees.peek(), 
               colliderTemp, collideeTemp;
            
        while (collider != null) {

            colliderTemp = (Sprite)collider.getNext();
            
            if (!collider.isDead()) {

                while (collidee != null){
                    
                    collideeTemp = (Sprite)collidee.getNext();
                    
                    if (!collidee.isDead()){
                        
                        if (collider.checkBounds() && collidee.checkBounds()){
                            
                            if (Sprite.checkCollision(collider, collidee)) {

                                try {

                                    collider.collisionDetected(collidee);

                                } finally {
                                    
                                }

                            }
                            
                            if (collidee.getCollisionType() instanceof Collisions.NearestDetection){

                                Sprite.checkCollision(collidee, collider);

                            }
                            
                        }
                        
                    }
                    
                    collidee = collideeTemp;
                    
                }

            }

            collider = colliderTemp;

        }
        
    }
    
    public final static void updateSprites(SpriteList list){
        
        SpriteObject obj = list.getList(), temp;
            
        while (obj != null) {

            temp = obj.getNext();
            
            try {
                
                if (!obj.isDead() && !obj.toBeDeleted()) {
                    
                    obj.update();
                    
                } else {
                    
                    obj.delete();
                    
                }
                
            } catch (Exception e){
                
                e.printStackTrace();
                
            }
            
            obj = temp;

        }
        
    }
    
}
