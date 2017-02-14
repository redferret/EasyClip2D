
package EasyClip2D.util;

import EasyClip2D.sprite.Sprite;
import EasyClip2D.geom.SpritePoint;
import EasyClip2D.collisions.SpritePoly;
import EasyClip2D.sprite.SpriteObject;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * Text object allows multi-line strings to be painted in the
 * game engine
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public class Text extends Sprite {

    protected String[] lines;
    
    private Font font;
    
    private Color color;
    
    protected int spacing;
    
    protected int currentLine;

    /**
     * Constructs a default text with one line
     */
    public Text(){
        
        this(0, 0);
        
    }
    
    /**
     * Constructs with one line added and sets the position
     */
    public Text(float x, float y){
        
        super(x, y, false);
        
        spacing = 12;
        
        lines = new String[1];
        
        currentLine = 0;
        
        renderOffScreen = true;
        
    }

    /**
     * Sets the font for this text
     */
    public void setFont(Font font){
        
        this.font = font;
        
        spacing = font.getSize();
        
    }
    
    /**
     * Sets the spacing between lines
     */
    public void setSpacing(int space){
        
        spacing = space;
        
    }

    /**
     * Clears all the lines
     */
    public void clearAllLines(){
        
        for (int i = 0; i < lines.length; i++){
            
            lines[i] = "";
            
        }
        
    }
    /**
     * Set the text in the first line
     */
    public void firstLine(String line){
        
        lines[0] = line;
        
    }
    /**
     * Set the text in the last line
     */
    public void lastLine(String line){
        
        lines[currentLine] = line;
        
    }
    /**
     * Return text of the current line
     */
    public String nextLine(){
        return lines[currentLine];
    }
    /**
     * Returns a line's text
     */
    public String line(int line){
        
        line -= 1;
        
        if (line < 0) 
            
            line = 0;
        
        return lines[line];
        
    }
    /**
     * Sets a line to the string
     */
    public void line(String line, int l){
        
        lines[l - 1] = line;
        
    }
    /**
     * returns the line number of the current line
     */
    public int currentLine(){
        
        return currentLine;
        
    }
    /**
     * Resets the number of lines to 1 and the current line to 1
     * and clears all the lines within this text.
     */
    public void clear(){
        
        lines = new String[1];
        
        currentLine = 0;
        
    }
    /**
     * Adds a new blank line
     */
    public void addLine(){
        
        addLine(1);
        
    }
    /**
     * Adds a specified number of blank lines
     */
    public void addLine(int numOfLines){

        assert (numOfLines < 1) : "Number of lines must be positive";
        
        String[] heldLines = lines;

        lines = new String[heldLines.length + numOfLines];

        int i = 0; for (String line : heldLines) {
            lines[i++] = line;
        }
        
        currentLine++;

    }

    /**
     * Appends a new line with the string
     */
    public void addLine(String newline){

        addLine();

        lines[currentLine] = newline;

    }
    /**
     * Returns the number of lines
     */
    public int getNumberOfLines(){
        
        return lines.length;
        
    }
    /**
     * deletes the line that is the current line
     */
    public void deleteCurrentLine(){
        
        currentLine--;
        
        if (currentLine < 0)
            
            currentLine = 0;
        
    }
    /**
     * Set the color of the string
     */
    public void setColor(Color color){
        
        this.color = color;
        
    }
    /**
     * Set the position of the text on the screen
     */
    public void setPosition(SpritePoint p){
        
        getPosition().setLocation(p.getX(), p.getY());
        
    }
    /**
     * Set the position of the text on the screen
     */
    public void setPosition(int x, int y){
        
        getPosition().setLocation(x, y);
        
    }

    public final void spriteUpdate(float x, float y) {
        
        return;
        
    }

    public void spriteSetup() {
        
        checkBounds(false);
        
        setCollisionBounds(new SpritePoly());
        
        setSpriteType(Tools.TYPE_STATIC);
        
    }

    public final void additionalGraphics(Graphics2D g2d) {

        g2d.setColor(color);

        g2d.setFont(font);        
        
        for (int i = 0; i < lines.length; i++) {
            
            if (lines[i] != null) {
                
                g2d.drawString(lines[i], getPosition().getX(), 
                        getPosition().getY() + (i * spacing));
                
            }
        }
        
        g2d.setTransform(new AffineTransform());

    }

    public final void collisionDetected(Sprite cSprite) {
        
        return;
        
    }

    @Override
    public final void nearestSprite(SpriteObject nearest) {
        
        return;
        
    }

    @Override
    public final void collisionDetected(SpriteObject collidee) {
        
        return;
        
    }


}
