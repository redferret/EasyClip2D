
package EasyClip2D.util;

import java.util.concurrent.ConcurrentLinkedQueue;


/**
 *
 * @author Richard DeSilvey
 */
public class GameEvent {

    private ConcurrentLinkedQueue<Event> events;
    
    public class Event {
        
        private int eventID;
        
        public Event(int eventID){
            
            this.eventID = eventID;
            
        }

        public int getEventID() {
            
            return eventID;
            
        }
        
        public String toString(){
            
            return Integer.toString(eventID);
            
        }
        
    }
    
    public GameEvent() {
        
        events = new ConcurrentLinkedQueue<Event>();
        
    }
    
    public void postEvent(int eventID){
        
        if (!events.offer(new Event(eventID))){
            
            System.out.println("Could not add new Event");
            
        }
        
    }
    
    public ConcurrentLinkedQueue<Event> getEvents(){
        
        return events;
        
    }
    
    public String toString(){
        
        return events.toString();
        
    }
    
}
