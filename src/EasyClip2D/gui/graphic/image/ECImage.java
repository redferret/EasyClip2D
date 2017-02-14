
package EasyClip2D.gui.graphic.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * ECImage loads and stores your image into memory. This
 * is a much faster way to load images for a sprite.
 * Once the image is cached you can always call the image
 * from this ECImage object. A new image can be re-loaded
 * into this object as well.
 * 
 * This class is best used as a static object within
 * your sprite class to reduce memory usage. Otherwise
 * it would defeat the purpose of having an ECImage object.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class ECImage {

    /**
     * The image that is currently cached in this
     * ECImage.
     */
    private BufferedImage image;
    
    /**
     * The current Class used to load the image.
     */
    private Class imageClass;

    /**
     * Make sure that your files in your jar is at least
     * on the same level as the images being loaded.
     * For instance, if your images are in "src/game/images/"
     * then your calling Class should not be in "src/game/other/".
     * 
     */
    public ECImage(Class imageClass){
        this.imageClass = imageClass;
    }

    public void uploadFromSystem(String imageFile, Color background, float trans){
        
        try{
            
            image = translucentImage(transparentBackground(ImageIO.read(new File(imageFile)), background), trans);
            
        }catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Upload an image from the directory, must be at the same level
     * or above to where the Class was defined.
     */
    public void uploadImage(String imageFile) {
        // upload image
        try {
            
            InputStream is = imageClass.getResourceAsStream(imageFile);
            
            image = ImageIO.read(is);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * Upload an image from the directory, must be at the same level
     * or above to where the Class was defined.
     * 
     * This method selects a solid color and turns it 100%
     * transparent. This is important for sprites that need
     * a transparent background, rather than seeing a sprite
     * with a rectangle background in what ever color it's in.
     */
    public void uploadImage(String imageFile, Color color) {
        
        try {
            
            InputStream is = imageClass.getResourceAsStream(imageFile);
            
            image = transparentBackground(ImageIO.read(is), color);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * Upload an image from the directory, must be at the same level
     * or above to where the Class was defined.
     * 
     * This method removes a background color and sets the entire
     * image to an alpha bit value between 1.0f and 0.0f. Where
     * 1.0f is solid color, 0.0f is completely transparent.
     */
    public void uploadImage(String imageFile, Color color, float trans) {
        // upload image and make the image's background transparent and
        // translucent
        try {
            
            InputStream is = imageClass.getResourceAsStream(imageFile);
            
            image = translucentImage(transparentBackground(ImageIO.read(is), color), trans);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * Makes the image transparent between the values of 1.0f and 0.0f, 1.0f = solid.
     * 0.0f = complete transparent.
     */
    public final void uploadImage(String imageFile, float trans) {
        // upload image and make the image's background transparent and
        // translucent
        try {
            
            InputStream is = imageClass.getResourceAsStream(imageFile);
            
            image = translucentImage(ImageIO.read(is), trans);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Removes a solid color from the BufferedImage and returns
     * the BufferedImage with the color removed.
     */
    public static BufferedImage transparentBackground(BufferedImage image,
            final Color color){
        
        BufferedImage new_image = new BufferedImage(image.getWidth(),
                    image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = new_image.createGraphics();
        
        g2d.setComposite(AlphaComposite.Src);
        
        g2d.drawImage(image, null, 0, 0);
        
        g2d.dispose();
        
        for(int i = 0; i < new_image.getHeight(); i++) {
            
            for(int j = 0; j < new_image.getWidth(); j++) {
                
                if(new_image.getRGB(j, i) == color.getRGB()) {
                    
                new_image.setRGB(j, i, 0x00FFFFFF);
                
                }
            }
        }
        return new_image;
    }

    /**
     * Sets the BufferedImage's alpha bits to a given transparent value
     * 1.0f is solid, where 0.0f is completely invisible.
     */
    public static BufferedImage translucentImage(BufferedImage image, float tValue) {

        BufferedImage aimg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TRANSLUCENT);
        
        Graphics2D g = aimg.createGraphics();
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, tValue));
        
        g.drawImage(image, null, 0, 0);
        
        g.dispose();
        
        return aimg;
    }
    
    /**
     * This method doesn't need to be called by the user since
     * the Sprite and Sprite2D objects use this method already.
     * It takes the loaded image and makes the image more
     * compatible for rendering on the current machine.
     */
    public static BufferedImage toCompatibleImage(BufferedImage image) {
        
        GraphicsConfiguration gc_config = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

        if (image.getColorModel().equals(gc_config.getColorModel())) {
            return image;
        }

        BufferedImage new_image = gc_config.createCompatibleImage(
                image.getWidth(), image.getHeight(), image.getTransparency());

        Graphics2D g2d = (Graphics2D) new_image.getGraphics();

        g2d.drawImage(image, 0, 0, null);
        
        g2d.dispose();

        return new_image;
    }
    
    /**
     * This method splits a BufferedImage into how ever man other
     * images and returns an array of those images. This
     * method is used when sprites are needing animation rather 
     * than having a simple BufferedImage for the sprite.
     */
    public static BufferedImage[] splitImage(BufferedImage img, int cols, int rows) {

        int width = img.getWidth()/cols;
        
        int height = img.getHeight()/rows;
        
        int num = 0;
        
        BufferedImage imgs[] = new BufferedImage[cols*rows];
        
        for(int y = 0; y < rows; y++) {
            
            for(int x = 0; x < cols; x++) {
                
                imgs[num] = new BufferedImage(width, height, img.getType());
                
                Graphics2D g = imgs[num].createGraphics();
                
                g.drawImage(img, 0, 0, width, height, width * x, height * y,
                            width * x + width, height * y + height, null);
                
                g.dispose();
                
                num++;
                
            }
        }
        
        return imgs;
    }
    /**
     * Fades a BufferedImage object and returns the newly faded Image.
     * Performs the same operation as translucentImage
     */
    public static BufferedImage fadeImage(BufferedImage image, float value) {

        return translucentImage(image, value);
        
    }

    /**
     * @return the copy of the image held
     */
    public BufferedImage getBufferedImage(){
        return image;
    }

    /**
     * @param img set the image to another image
     */
    public void setBufferedImage(BufferedImage img){
        image = img;
    }

}
