
package EasyClip2D.audio;

import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**
 * Plays .WAV files loaded into memory.
 * This is only recommended for very small sound files. The amount of
 * space such as RAM and hard disk space, can end up being 90% of
 * the total size of your game. For music files, use OggMusic for
 * a smaller less expensive file.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public class ECStaticSoundEffect {

    private AudioInputStream stream;
    
    private Class soundClass;
    
    private boolean isPaused;
    
    private int currentClip;
    
    private Clip[] clip;
    
    private int clipSize;

    /**
     * Main Constructor for this audio file.
     * @param soundClass 
     */
    public ECStaticSoundEffect(Class soundClass){
        
        assert soundClass != null : "Null Reference to Sound Class";
        
        this.soundClass = soundClass;
    }
    /**
     * Load a WAV file by specifying the file name
     * @param fileName 
     */
    public void LoadWav(String fileName){
        
        clipSize = 1;
        
        clip = new Clip[clipSize];
        
        currentClip = 0;
        
        isPaused = false;
        
        load(fileName);
    }
    /**
     * Load a WAV file by specifying the file name, also recommend
     * to load a clip size. For example, if this audio file is 
     * a sound effect that plays frequently, the suggested clip size
     * should be more than 1. This eliminates sound delays in this
     * sound effect. Warning: NEVER set the clip size to an audio file
     * that has a large amount of memory such as a music file. The clip
     * size multiplies the size of the WAV file. i.e. 1MB with a clip size
     * of 10 is 10MB.
     * @param fileName 
     */
    public void LoadWav(String fileName, int clipSize){
        
        this.clipSize = clipSize;
        
        clip = new Clip[clipSize];
        
        currentClip = 0;
        
        isPaused = false;
        
        load(fileName);
    }

    /**
     * Pause the audio file.
     */
    public void pause(){
        
        clip[currentClip].stop();
        
        isPaused = true;
    }
    /**
     * Restart the audio file.
     */
    public void restart(){
        
        clip[currentClip].stop();
        
        setFramePosition(0);
        
        updateClip();
        
        isPaused = true;
    }

    /**
     * Check to see if the audio file is currently running.
     * @return 
     */
    public boolean isPlaying(){
        return clip[currentClip].isRunning();
    }

    /**
     * The frame position is the current position of the audio clip.
     * @return 
     */
    public int getFramePosition(){
        return (clip[currentClip].getFramePosition());
    }
    /**
     * Sets the frame position of the audio clip.
     * @param frame 
     */
    public void setFramePosition(int frame){
        clip[currentClip].setFramePosition(frame);
    }
    
    /**
     * Returns the length of this audio file. For instance the middle
     * of this audio track would be the frame length divided in half.
     * @return 
     */
    public int getFrameLength(){
        return clip[currentClip].getFrameLength();
    }

    /**
     * Play the audio file
     */
    public void play() {
        
        if (!isPlaying()) {
            
            if (!isPaused)
                
                setFramePosition(0);
            
            else if (isPaused)
                
                isPaused = false;
            
            clip[currentClip].start();
            
        }
        
        updateClip();
    }

    private void updateClip(){
        
        if (clipSize < 2) return;
        
        if ((currentClip++) >= clipSize - 1) {
            currentClip = 0;
        }
    }

    private boolean load(String fileName){
        try {
            
            InputStream is = soundClass.getResourceAsStream(fileName);
                
            stream = AudioSystem.getAudioInputStream(is);
            
            for (int i = 0; i < clipSize; i++) {
                
                clip[i] = AudioSystem.getClip();
                
                clip[i].open(stream);
            }
            
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}




