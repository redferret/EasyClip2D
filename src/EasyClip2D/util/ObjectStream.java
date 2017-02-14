
package EasyClip2D.util;

import java.io.*;

/**
 * Saves any object with in Easy Clip except for the Screen object
 * and the Game Engine.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class ObjectStream {
    
    public static void SaveObject(String fileName, Object object){
        
        try{
            
            File file = new File(fileName);
            
            if (!file.exists()){
                file.createNewFile();
            }
            
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            
            out.writeObject(object);
            
        }catch (Throwable e){
            e.printStackTrace();
        }
        
    }
    
    public static Object LoadObject(String fileName){
        
        try{
            
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            
            return in.readObject();
            
        }catch (Throwable e){
            System.err.println(e.getMessage());
            return null;
        }
        
    }
    
    
}
