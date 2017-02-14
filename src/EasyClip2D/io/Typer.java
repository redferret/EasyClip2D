
package EasyClip2D.io;

import EasyClip2D.audio.ECStaticSoundEffect;
import EasyClip2D.util.Delay;
import EasyClip2D.util.Tools;

/**
 * The typer object simulates the game typing a message to the player.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public class Typer extends KeyboardInput {

    private Delay typingDelay;
    
    private char[] text;
    
    private int index, delayTime;
    
    private ECStaticSoundEffect sound;
    
    /**
     * Default Constructor
     */
    public Typer() {
        
        this(0, 0);
        
    }
    
    public Typer(float x, float y) {
        
        super(x, y);
        
        enabled(false);
        
        typingDelay = new Delay();
        
        typingDelay.allowReset(true);
        
        index = delayTime = 0;
        
        text = new char[0];
        
    }
    
    public void initSoundEffect(String fileName, int clipLength, Class c){
        
        sound = new ECStaticSoundEffect(c);
        
        sound.LoadWav(fileName, clipLength);
        
    }
    
    public void type(String line){
        
        type(line, 1);
        
    }
    
    public void type(String line, int speed){
        
        text = line.toCharArray();
        
        index = 0;
        
        delayTime = speed;
        
        typingDelay.reset();
        
    }
    
    public boolean finished(){
        
        return (index >= text.length);
        
    }
    
    public void close(){
        
        Tools.ec_engine.kill(this);
        
    }
    
    public final void step(){
        
        if (typingDelay.delay(delayTime) == 1){
            
            if (index < text.length){
                
                if (text[index] == '\n'){
                    addLine();
                }
                
                if (sound != null && text[index] != ' ' && text[index] != '\n'){
                    
                    sound.play();
                    
                }
                
                appendToLast(Character.toString(text[index++]));
                
                
            }else
                return;
            
        }
        
    }
    
    
}
