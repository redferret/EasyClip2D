

package EasyClip2D.io;

import EasyClip2D.util.Text;
import java.awt.event.KeyEvent;

/**
 * Reads input from the keyboard by passing key events into a method
 * to appendInput characters to a string then return the input as a String.
 * 
 * This is not the same as the KeyController, the KeyController obtains
 * the KeyEvents and the KeyEvents are passed into this Sprite object.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public class KeyboardInput extends Text {
    
    private String input;
    
    private boolean enabled;
    /**
     * The default keys for input, all keys including upper and lower
     * case are excepted, and all numbers and symbols.
     */
    public static final String DEAFULT_KEYS = " ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvwxyz1234567890"
                + "`~!@#$%^&*()_+-={}|:\"<>?,./;'[]\\";
    /**
     * Upper case letters only
     */
    public static final String ABC_CASE_UPPER = " ABCDEDFHIJKLMNOPQRSTUVWXYZ";
    /**
     * Lower case letters only
     */
    public static final String ABC_CASE_LOWER = " abcdefghijklmnopqrstuvwxyz";
    /**
     * Upper and lower case letters only
     */
    public static final String ABC_ALL = " ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz";
    
    public static final String NUMERAL_UNSIGNED_FLOAT = " .0123456789";
    
    public static final String NUMERAL_SIGNED_FLOAT = " .0123456789-";
    
    public static final String NUMERAL_UNSIGNED = " 0123456789";
    
    public static final String NUMERAL_SIGNED = " 0123456789-";
    
    private boolean lineReturned;
    
    private String keysAllowed;
    
    private boolean multiLine;
    
    private int maxChars;

    /**
     * Main constructor to contract this object
     */
    public KeyboardInput(){
        
        this(0, 0);
        
    }
    
    public KeyboardInput(float x, float y){
        
        super(x, y);
        
        input = "";
        
        keysAllowed = DEAFULT_KEYS;
        
        multiLine = false;
        
        maxChars = 30;
        
        lineReturned = false;
        
        enabled = true;
        
    }

    /**
     * Writes directly to the input of this keyboard object.
     * This does not appendInput strings.
     * @param s a string that will replace not appendInput
     * @return the spring entered
     */
    public String write(String s){
        
        return input = s;
        
    }
    /**
     * Returns true if the user pressed the enter key
     * @return if the user pressed the enter key
     */
    public boolean lineReturned(){
        
        return lineReturned;
        
    }
    /**
     * Similar to that of a console, printing a line will appendInput
     * a new line to the input if Multi-line is supported for this
     * object.
     * @see #setMultiLine(boolean) 
     */
    public void println(String s){
        
        if (multiLine)
            
            addLine(s);
        
        else
            
            print(s);
        
    }
    
    /**
     * Sets the most recent line or last line to a string
     */
    public void print(String s){
        
        lastLine(s);
        
    }
    /**
     * Appends a string directly to the input string of this object.
     * Does not replace the input such as the write() method does.
     * @param s string to appendInput to the input
     * @return the appended string
     */
    public String appendInput(String s){
        
        return input += s;
        
    }
    
    /**
     * Appends a string to the last line of this text.
     */
    public void appendToLast(String s){
    
        if (lines[currentLine] == null)
            
            lines[currentLine] = s;
        
        else
            
            lines[currentLine] += s;
        
    }
    
    /**
     * Set the maximum number of characters allowed to be appended to input.
     * @param max the max characters
     */
    public void setMaxChars(int max){
        
        maxChars = max;
        
    }
    /**
     * Adds additional characters to the allowed characters to be typed.
     * @param keySequence additional characters to the allowed characters to be typed.
     */
    public void appendKeySequence(String keySequence){
        
        keysAllowed += keySequence;
        
    }
    /**
     * Set the allowed keys that will be appended to the input.
     * @param keySequence the allowed keys that will be appended to the input.
     */
    public void setKeys(String keySequence){
        
        keysAllowed = keySequence;
        
    }
    /**
     * Sets if this KeyboardInput object has multi-lines
     */
    public void setMultiLine(boolean m){
        
        multiLine = m;
        
    }
    /**
     * 
     * @return if this keyboard has multi-lines
     */
    public boolean multiLine(){
        
        return multiLine;
        
    }
    
    /**
     * Clears the input received.
     */
    public void clearInput(){
        
        clear();
        
        input = "";
        
    }
    
    /**
     * Enables this keyboard input, if enabled then this
     * keyboard input will read input from the keyboard.
     */
    public void enabled(boolean b){
        enabled = b;
    }
    
    /**
     * 
     * @return if this keyboard is enabled.
     */
    public boolean enabled(){
        return enabled;
    }
    
    /**
     * Reads a key event and checks if the number of keys allowed don't
     * exceed the limit and if the key is a valid input character if
     * this keyboard input is enabled.
     * @param e the current key event
     */
    public final void read(KeyEvent e) {
        
        if (enabled){
        
            lineReturned = false;

            if (e.getKeyCode() == KeyEvent.VK_ENTER){

                if (!multiLine){

                    lineReturned = true;

                    return;

                }else{

                    addLine();

                    lineReturned = true;

                }
            }else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {

                if (nextLine().equalsIgnoreCase("")){

                    deleteCurrentLine();

                    lastLine(write(nextLine()));

                    return;
                } 

                lastLine(append(e));

                return;

            }

            try {

                if (maxChars <= nextLine().length()) {

                    return;

                }else{

                    lastLine(append(e));

                }

            } catch (Exception ex) {

                lastLine(append(e));

            }
        }
    }
    /**
     * Returns the current active line or input, if multi-lined.
     * Returns the first line in the object if single-lined.
     */
    public String readLine(){

        lineReturned = false;
        
        if (!multiLine)
            return line(1);
        else
            return nextLine();
    }
    /**
     * Appends all the lines into one String object.
     */
    public String readLines(){
        
        lineReturned = false;
        
        StringBuilder sb = new StringBuilder();
        
        for (int i = 1; i < getNumberOfLines(); i++){
            
            if (line(i) != null && !line(i).equalsIgnoreCase("")){
                
                sb.append(line(i));
                
                if (!line(i + 1).equalsIgnoreCase("")){
                    sb.append("\n");
                }
                
            }
            
        }
        
        return sb.toString();
        
    }
    
    private boolean findChar(String s, char numericValue){
        
        for (int i = 0; i < s.length(); i++){
            
            if (s.charAt(i) == numericValue){
                
                return true;
            }
        }
        
        return false;
    }
    
    private String append(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_ENTER){
            
            return input = "";
            
        }else if (findChar(keysAllowed, e.getKeyChar())){
            
            input += e.getKeyChar();
            
            return input;
            
        }else if (code == KeyEvent.VK_BACK_SPACE){

            try {
                
                char[] newArray = new char[input.length() - 1];

                for (int i = 0; i < newArray.length; i++){
                    newArray[i] = input.charAt(i);
                }

                input = String.copyValueOf(newArray);

                return input;
                
            } catch (Exception ex) {}
        }
        return input;
    }
}

