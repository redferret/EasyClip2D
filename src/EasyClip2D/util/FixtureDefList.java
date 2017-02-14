
package EasyClip2D.util;

import EasyClip2D.Box2D.org.jbox2d.dynamics.FixtureDef;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Richard DeSilvey
 */
public final class FixtureDefList {

    private List<FixtureDef> defs;
   
    public FixtureDefList() {
        
        defs = new ArrayList<FixtureDef>();
        
    }
    
    public void addFixture(FixtureDef def){
        
        defs.add(def);
        
    }
    
    public FixtureDef[] getDefintions(){
        
        FixtureDef[] list = new FixtureDef[defs.size()];
        
        Arrays.copyOf(defs.toArray(list), defs.size());
        
        return list;
        
    }
    
    
}
