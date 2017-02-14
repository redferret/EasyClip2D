
package EasyClip2D.util.listeners;

import EasyClip2D.core.ECEngine.Environment;
import EasyClip2D.sprite.Sprite2D;
import EasyClip2D.Box2D.org.jbox2d.callbacks.ContactImpulse;
import EasyClip2D.Box2D.org.jbox2d.callbacks.ContactListener;
import EasyClip2D.Box2D.org.jbox2d.collision.Manifold;
import EasyClip2D.Box2D.org.jbox2d.dynamics.contacts.Contact;

/**
 * The Box2DCollisionListener is used for Box2D to find collisions
 * between Sprite2D objects. Since the Sprite object in Easy Clip
 * needs a Collision algorithm, Box2D already supplies the
 * algorithms used for checking collisions, but you still need
 * to setup a listener for Box2D. Add this listener to
 * Box2D to get a collision between two bodies.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class Box2DCollisionListener implements ContactListener {

    private static Sprite2D fixA, fixB;
    
    public Box2DCollisionListener(){
        
        fixA = null;
        
        fixB = null;

        Environment.getWorld().setContactListener(this);
        
    }
    
    @Override
    public void beginContact(Contact contact) {
        
        fixA = (Sprite2D) contact.getFixtureA().getBody().getUserData();
        
        fixB = (Sprite2D) contact.getFixtureB().getBody().getUserData();
        
        if (fixA != null && fixB != null){
            
            fixA.collisionDetected(fixB);
            
            fixB.collisionDetected(fixA);
            
        }
        
    }

    @Override
    public void endContact(Contact contact) {
        
        fixA = (Sprite2D) contact.getFixtureA().getBody().getUserData();
        
        fixB = (Sprite2D) contact.getFixtureB().getBody().getUserData();
        
        if (fixA != null && fixB != null){
            
            fixA.collisionEnded(fixB);
            
            fixB.collisionEnded(fixA);
            
        }
        
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        
    }
}
