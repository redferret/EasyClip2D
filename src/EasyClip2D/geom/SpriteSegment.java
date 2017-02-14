
package EasyClip2D.geom;


/**
 * A Sprite segment is used to create paths or polygon shapes
 * for collision boundaries.
 * 
 * Copyright © 2012 Easy Clip2D All Rights Reserved. 
 * 
 * @author Richard Evans DeSilvey
 */
public final class SpriteSegment {
    
    private SpritePoint p1, p2;
    
    public SpriteSegment(){
        p1 = new SpritePoint();
        p2 = new SpritePoint();
    }
    
    public SpriteSegment(float x1, float y1, float x2, float y2){
        p1 = new SpritePoint(x1, y1);
        p2 = new SpritePoint(x2, y2);
    }
    
    public SpritePoint getP1(){
        return p1;
    }
    
    public SpritePoint getP2(){
        return p2;
    }
    
    public void setLine(SpriteSegment line) {
        p1.setLocation(line.getP1());
        p2.setLocation(line.getP2());
    }
    
    public void setLine(SpritePoint p1, SpritePoint p2) {
        this.p1.setLocation(p1);
        this.p2.setLocation(p2);
    }
    
    public void setLine(float x1, float y1, float x2, float y2) {
        p1.setLocation(x1, y1);
        p2.setLocation(x2, y2);
    }
    
    public boolean intersects(SpriteSegment line) {
        
	return segmentsIntersect(line.getP1().getX(), line.getP1().getY(), line.getP2().getX(), line.getP2().getY(),
			      getP1().getX(), getP1().getY(), getP2().getX(), getP2().getY());
    }
    
    private static boolean segmentsIntersect(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
	return ((pointRelativeToLine(x1, y1, x2, y2, x3, y3) *
		 pointRelativeToLine(x1, y1, x2, y2, x4, y4) <= 0)
		&& (pointRelativeToLine(x3, y3, x4, y4, x1, y1) *
		    pointRelativeToLine(x3, y3, x4, y4, x2, y2) <= 0));
    }

    private static int pointRelativeToLine(float x1, float y1,float x2, float y2, float px, float py) {
	
        x2 -= x1;
	y2 -= y1;
	px -= x1;
	py -= y1;
        
	float r = px * y2 - py * x2;
        
	if (r == 0.0f) {
	    
	    r = px * x2 + py * y2;
	    if (r > 0.0f) {
		
		px -= x2;
		py -= y2;
		r = px * x2 + py * y2;
		if (r < 0.0f) {
		    r = 0.0f;
		}
	    }
	}
        
	return (r < 0.0f) ? -1 : ((r > 0.0f) ? 1 : 0);
    }


}
