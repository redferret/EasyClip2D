
package EasyClip2D.sprite.util;

import EasyClip2D.Box2D.org.jbox2d.dynamics.BodyType;
import EasyClip2D.geom.SpritePoint;
import EasyClip2D.gui.GameFrame;
import EasyClip2D.sprite.Sprite;
import EasyClip2D.sprite.Sprite2D;
import EasyClip2D.sprite.SpriteObject;
import EasyClip2D.util.Chain;
import EasyClip2D.util.Tools;

/**
 * The SpriteGrid is a feature where your games can be setup
 * to use a grid or tile sprites.
 * 
 * Any method that uses type Integer for coordinates are specifying
 * a cells location in the grid's array. Floating point Integers
 * are the cells location in the view port. The Tools class gives
 * a few methods for converting positions in the view port to 
 * positions used for the sprite grid.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class SpriteGrid {
    
    private SpriteObject[][] gridSprites;
    
    private int gridWidth, gridHeight;
    
    private SpritePoint grid_dim;

    private boolean gridWrap;
    
    private static final int LOCAL_X[] = {1,  1,  0, -1, -1, -1, 0, 1, 0};
    
    private static final int LOCAL_Y[] = {0, -1, -1, -1,  0,  1, 1, 1, 0};
    
    public static final int TOP = 0x2, TOP_RIGHT = 0x1, RIGHT = 0x0, NONE = 0x8, BOTTOM_RIGHT = 0x7,
                            BOTTOM = 0x6, BOTTOM_LEFT = 0x5, LEFT = 0x4, TOP_LEFT = 0x3;
    
    /**
     * Creates a new grid object for the sprite collision
     * lists. Each value of r and c represent 1 pixel.
     * A grid being 10x10 of a screen 500x500 would make
     * the actual grid 50 sprites x 50 sprites.
     */
    public SpriteGrid(int r, int c) {
        
        gridWidth = r;
        
        gridHeight = c;
        
        gridWrap = true;
        
        gridSprites = new SpriteObject[gridWidth][gridHeight];

        grid_dim = new SpritePoint(Tools.ec_engine.getWindow().getWidth(), 
                Tools.ec_engine.getWindow().getHeight());
    }
    
    /**
     * External method for the game engine to call.
     */
    public boolean addSprite(SpriteObject s){
        
        return addSprite(s, 1, 1);
        
    }
    /**
     * External method for the game engine to call.
     */
    public boolean addSprite(SpriteObject s, int width, int height){
        
        try {
            
            if (s instanceof Sprite2D){
            
                if (((Sprite2D)s).getBody().m_type != BodyType.STATIC){
                    
                    return false;
                    
                }
                
            }
            
            if (true){
                
                int x = (int)(s.getPosition().x / getTileWidth());
        
                int y = (int)(s.getPosition().y / getTileHeight());

                if (arrayOutOfBounds(x, y)){

                    if (gridWrap){
                
                        if (x < 0){

                            x = gridSprites.length - 1;

                        }else if (x > gridSprites.length - 1){

                            x = 0;

                        }

                        if (y < 0){

                            y = gridSprites[x].length - 1;

                        }else if (y > gridSprites[x].length - 1){

                            y = 0;

                        }

                    }else

                        return false;

                }

                if (isEmpty(x, y, width, height)) {

                    insertSprite(s, x, y, width, height);

                    float sx = (x * getTileWidth() + (getTileWidth() / 2)),

                          sy = (y * getTileHeight() + (getTileHeight() / 2));

                    if (s instanceof Sprite)

                        ((Sprite)s).setLocationTo(sx, sy);

                    else if (s instanceof Sprite2D){

                        ((Sprite2D)s).getBody().getTransform().position.set(sx, sy);

                    }

                    return true;

                }

                return false;
                
            } else {
                throw new Exception("Sprite Type must be Type Grid");
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
        
    }
    
    public boolean isEmpty(int x, int y, int width, int height){
        
        int empty_cells = 0;
        
        for (int i = 0; i < width; i++){
            
            for (int j = 0; j < height; j++){
                
                if (gridSprites[x + i][y + j] == null){
                
                    empty_cells++;

                }
                
            }
            
        }
        
        return (empty_cells == (width * height));
        
    }
    
    /**
     * Returns the sprite at this location. This is not the true location
     * on the grid, instead if a sprite is at 10x10 on the grid, 
     * and the x and y values are (7, 9) then what ever sprite
     * is within these coordinates will be the sprite that is returned.
     */
    public SpriteObject getSprite(SpritePoint pos) {
        
        SpritePoint cellLocation = getCellLocation(pos.x, pos.y);
        
        if (arrayOutOfBounds((int)cellLocation.x, (int)cellLocation.y))
            
            return null;
        
        return gridSprites[(int)cellLocation.x][(int)cellLocation.y];
        
    }
    
    public SpriteObject getSprite(int x, int y) {
        
        if (arrayOutOfBounds(x, y))
            
            return null;
        
        return gridSprites[x][y];
        
    }
    
    public SpriteObject getUpperFrom(SpriteObject s) {
        
        return getFrom(s.getPosition().x, s.getPosition().y, 0, -1);
        
    }
    
    public SpriteObject getLowerFrom(SpriteObject s) {
        
        return getFrom(s.getPosition().x, s.getPosition().y, 0, 1);
        
    }
    
    public SpriteObject getLeftFrom(SpriteObject s) {
        
        return getFrom(s.getPosition().x, s.getPosition().y, -1, 0);
        
    }
    
    public SpriteObject getRightFrom(SpriteObject s) {
        
        return getFrom(s.getPosition().x, s.getPosition().y, 1, 0);
        
    }
    
    public SpriteObject getFrom(SpriteObject s, int directionID){
        
        return getFrom(s.getPosition().x, s.getPosition().y, 
                LOCAL_X[directionID], LOCAL_Y[directionID]);
        
    }
    
    /**
     * Returns a sprite object based off of the location of another sprite.
     * If you use the location of a grid sprite and pass in cellX and cellY
     * to equal 0 then you will get the same sprite back. This method
     * is similar to getRightFrom, getLowerFrom, etc.
     * @param x - x location of the sprite
     * @param y - y location of the sprite
     * @param cellX - scan value for the grid
     * @param cellY - scan value for the grid
     */
    public SpriteObject getFrom(float x, float y, int cellX, int cellY) {
        
        SpritePoint cellLocation = getCellLocation(x, y);
        
        if (arrayOutOfBounds((int)cellLocation.x, (int)cellLocation.y))
            
            return null;
        
        if ((cellLocation.x + cellX) < 0 || (cellLocation.x + cellX) > gridWidth){
            
            return null;
        
        }else if ((cellLocation.y + cellY) < 0 || (cellLocation.y + cellY) > gridHeight){
            
            return null;
        }
        
        return gridSprites[(int)cellLocation.x + cellX][(int)cellLocation.y + cellY];
        
    }
    /**
     * This will move the sprite one block depending of the direction id value.
     * The sprite must exist in this grid in order for the proper movement 
     * of the sprite. These values can be found in this Class. If a cell contains a sprite
     * the new sprite will overwrite the old one and the old sprite will be
     * returned otherwise a null value is returned.
     * @param s The sprite to be moved
     * @param directionID The direction to move the sprite
     * @return The old sprite contained in a cell if one exists
     */
    public SpriteObject move(SpriteObject s, int directionID){
        
        return move(s, directionID, false);
        
    }
    /**
     * This will move the sprite one block depending of the direction id value.
     * The sprite must exist in this grid in order for the proper movement 
     * of the sprite. These values can be found in this Class. If a cell contains a sprite
     * the new sprite will over-right the old one and the old sprite will be
     * returned otherwise a null value is returned.
     * @param s The sprite to be moved
     * @param directionID The direction to move the sprite
     * @return The old sprite contained in a cell if one exists
     */
    public SpriteObject move(SpriteObject s, int directionID, boolean merge){
        
        SpritePoint cellLocation = getCellLocation(s);
        
        return move(s, (int)cellLocation.x + LOCAL_X[directionID], 
                (int)cellLocation.y + LOCAL_Y[directionID], merge);
        
    }
    
    /**
     * The sprite doesn't have to exist in a grid but the x and y
     * indexes must exist in this grid for correct placement.
     * If the x and y indexes are out-of-bounds then nothing will
     * happen and the method will return null.
     * @param s Sprite being moved
     * @param x x Index
     * @param y y Index
     * @return 
     */
    public SpriteObject move(SpriteObject s, int x, int y){
        
        return move(s, x, y, false);
        
    }
    
    /**
     * The sprite doesn't have to exist in a grid but the x and y
     * indexes must exist in this grid for correct placement.
     * If the x and y indexes are out-of-bounds then nothing will
     * happen and the method will return null.
     * @param s Sprite being moved
     * @param x x Index
     * @param y y Index
     * @return 
     */
    public SpriteObject move(SpriteObject s, int x, int y, boolean merge){
        
        SpritePoint oldCellLocation = getCellLocation(s);
        
        if (arrayOutOfBounds(x, y)){
            
            if (gridWrap){
                
                if (x < 0){
                    
                    x = gridSprites.length - 1;
                    
                }else if (x > gridSprites.length - 1){
                    
                    x = 0;
                    
                }
                
                if (y < 0){
                    
                    y = gridSprites[x].length - 1;
                    
                }else if (y > gridSprites[x].length - 1){
                    
                    y = 0;
                    
                }
                
            }else
                
                return null;
        
        }
            
        SpriteObject temp = gridSprites[x][y];
        
        if (temp != null && !merge){
            
            return null;
            
        }
        
        gridSprites[(int)oldCellLocation.x][(int)oldCellLocation.y] = null;
        
        gridSprites[x][y] = null;
        
        gridSprites[x][y] = s;
        
        float sx = (x * getTileWidth() + (getTileWidth() / 2)),
                            
              sy = (y * getTileHeight() + (getTileHeight() / 2));

        if (s instanceof Sprite)

            ((Sprite)s).setLocationTo(sx, sy);

        else if (s instanceof Sprite2D){

            ((Sprite2D)s).getBody().getTransform().position.set(sx, sy);

        }
        
        return temp;
        
    }
    
    public void gridWrapOnAdd(boolean b){
        
        gridWrap = b;
        
    }
    
    /**
     * Returns the grid in a 2-Dimensional array of all the sprites.
     * Be careful since this is not returning a copy of the array.
     */
    public SpriteObject[][] getGrid(){
        
        return gridSprites;
        
    }
     
    /**
     * Removes the sprite from the grid
     */
    public void killSprite(SpriteObject s) throws Exception{
        
        int x = (int) (s.getPosition().getX() / getTileWidth());
                
        int y = (int) (s.getPosition().getY() / getTileHeight());
        
        if (arrayOutOfBounds(x, y))
            
            return;
        
        gridSprites[x][y] = null;
        
    }
    
    /**
     * @return the width of the grid counted in cells not pixels.
     */
    public int getTileWidth(){
        
        return (int)(grid_dim.x / gridWidth);
        
    }
    
    /**
     * @return the height of the grid counted in cells not pixels.
     */
    public int getTileHeight(){
        
        return (int)(grid_dim.y / gridHeight);
        
    }
    
    /**
     * This will specify to the grid how big it will be.
     * This directly effects the size of each cell.
     */
    public void setGridDimensions(int width, int height){
        
        grid_dim.setLocation(width, height);
        
    }
    
    public void setGridDimensionsTo(GameFrame frame){
        
        grid_dim.setLocation(frame.getWidth(), frame.getHeight());
        
    }
    
    /**
     * Clears all sprites in this grid. If you wish to kill all the
     * sprites in this grid and have them fully removed from the
     * game engine then call the killGridSprites method.
     */
    public void clearAllSprites(){
        
        gridSprites = new SpriteObject[gridWidth][gridHeight];
        
    }
    
    
    /**
     * External method for the game engine.
     */
    public Chain<SpriteObject> findPotentialCollisions(SpriteObject collider){

        int leftTest = (int)((collider.getCenter().getX() - collider.getPotentialCollisionRadius()) / getTileWidth());
        
        int rightTest = (int)((collider.getCenter().getX() + collider.getPotentialCollisionRadius()) / getTileWidth());
        
        int bottomTest = (int)((collider.getCenter().getY() - collider.getPotentialCollisionRadius()) / getTileHeight());
        
        int topTest = (int)((collider.getCenter().getY() + collider.getPotentialCollisionRadius()) / getTileHeight());
           
        Chain<SpriteObject> pList = new Chain<SpriteObject>();
        
        for (int y = bottomTest; y <= topTest; y++){
            
            for (int x = leftTest; x <= rightTest; x++){
                
                if (arrayOutOfBounds(x, y))
                    break;
                
                if (gridSprites[x][y] != null) {
                    
                    pList.offer(gridSprites[x][y]);
                    
                }

            }
            
        }
        
        return pList;
        
    }
    
    private SpritePoint getCellLocation(SpriteObject s){
        
        SpritePoint cPos = SpritePoint.copyOf(s.getPosition());
        
        SpritePoint cellLocation = getCellLocation(cPos.x, cPos.y);
        
        return cellLocation;
        
    }
    
    private boolean arrayOutOfBounds(int x, int y){
        
        if (x < 0 || y < 0 || x >= gridWidth || y >= gridHeight) {
            return true;
        }
        
        return false;
    }
    
    private SpritePoint getCellLocation(float x, float y){
        
        return new SpritePoint((x / getTileWidth()), (y / getTileHeight()));
        
    }
    
    private void insertSprite(SpriteObject s, int x, int y, int width, int height){
        
        for (int i = 0; i < width; i++){
            
            for (int j = 0; j < height; j++){
                
                gridSprites[x + i][y + j] = s;
                
            }
            
        }
        
    }
    
}
