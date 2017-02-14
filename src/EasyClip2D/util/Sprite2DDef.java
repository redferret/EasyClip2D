
package EasyClip2D.util;

import EasyClip2D.Box2D.org.jbox2d.collision.shapes.Shape;
import EasyClip2D.Box2D.org.jbox2d.common.Vec2;
import EasyClip2D.Box2D.org.jbox2d.dynamics.BodyDef;
import EasyClip2D.Box2D.org.jbox2d.dynamics.BodyType;
import EasyClip2D.Box2D.org.jbox2d.dynamics.FixtureDef;

/**
 * The Sprite2D definition list is used to bundle both body and fixture
 * definitions into one object. Easy Clip takes care of the rest
 * on the side of Box2D when your sprite2d is added to the engine.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class Sprite2DDef {

    private BodyDef body_def;
    
    private FixtureDef fix_def;
    
    private FixtureDefList list;
    
    public Sprite2DDef() {
        
        body_def = new BodyDef();
        
        fix_def = new FixtureDef();
        
        list = new FixtureDefList();
        
    }
    
    /**
     * Body Definition
     */
    public void setAngularVelocity(float vel){
        
        body_def.angularVelocity = vel;
        
    }
    /**
     * Body Definition
     */
    public void setLinearVelocity(float vel_x, float vel_y){
        
        body_def.linearVelocity = new Vec2(vel_x, vel_y);
        
    }
    /**
     * Body Definition
     */
    public void setAngle(float radians){
        
        body_def.angle = radians;
        
    }
    /**
     * Body Definition
     */
    public void setType(BodyType type){
        
        body_def.type = type;
        
    }
    /**
     * Body Definition
     */
    public void allowSleep(boolean b){
        
        body_def.allowSleep = b;
        
    }
    /**
     * Body Definition
     */
    public void fixedRotation(boolean b){
        
        body_def.fixedRotation = b;
        
    }
    /**
     * Body Definition
     */
    public void setDamping(float linear, float angular){
        
        body_def.linearDamping = linear;
        
        body_def.angularDamping = angular;
        
    }
    /**
     * Body Definition
     */
    public void setPosition(Vec2 pos){
        
        body_def.position = pos;
        
    }
    /**
     * Body Definition
     */
    public void isBullet(boolean b){
        
        body_def.bullet = b;
        
    }

    public BodyDef getBodyDef() {
        
        return body_def;
        
    }
    
    public FixtureDef getFixtureDef(){
        
        return fix_def;
        
    }
    
    public void addFixtureToList(){
        
        list.addFixture(fix_def);
        
        FixtureDef tempFix = new FixtureDef();
        
        tempFix.density = fix_def.density;
        tempFix.friction = fix_def.friction;
        tempFix.isSensor = fix_def.isSensor;
        tempFix.restitution = fix_def.restitution;

        fix_def = new FixtureDef();
        
        fix_def.density = tempFix.density;
        fix_def.friction = tempFix.friction;
        fix_def.isSensor = tempFix.isSensor;
        fix_def.restitution = tempFix.restitution;
        
        
    }
    
    public FixtureDefList getFixtureDefList(){
        
        return list;
        
    }
    /**
     * Body Definition
     */
    public Vec2 getPos(){
        
        return body_def.position;
        
    }
    /**
     * Fixture Definition
     */
    public void setDensity(float density){
        
        fix_def.density = density;
        
    }
    /**
     * Fixture Definition
     */
    public void setFilter(int categoryBits, int maskBits, int groupIndex){
        
        fix_def.filter.categoryBits = categoryBits;
        
        fix_def.filter.maskBits = maskBits;
        
        fix_def.filter.groupIndex = groupIndex;
        
    }
    /**
     * Fixture Definition
     */
    public void setShape(Shape s){
        
        fix_def.shape = s;
        
    }
    /**
     * Fixture Definition
     */
    public void setRestitution(float r){
        
        fix_def.restitution = r;
        
    }
    /**
     * Fixture Definition
     */
    public void setFriction(float f){
        
        fix_def.friction = f;
        
    }
    /**
     * Fixture Definition
     */
    public void isSensor(boolean sensor){
        
        fix_def.isSensor = sensor;
        
    }
    
}
