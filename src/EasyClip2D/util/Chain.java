
package EasyClip2D.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The Chain data structure was designed to be simplistic and
 * fast. The Chain is similar to a Linked List, however, the difference
 * is that the Chain acts like a queue. You can only obtain Links
 * through the iterator and remove them through the iterator.
 * 
 * The rules of the Chain are simple yet restrictive. The Chain will
 * always allow you to add new objects all the time and even remove
 * objects as well via the iterator, however, no object can
 * be removed suddenly from the Chain directly. The only time 
 * a Link can be removed is if the iterator is currently selecting
 * a Link within the queue.
 * 
 * In some very rare cases you may find that multi-threading may cause
 * a chain to break. In this case a BrokenChainException is thrown.
 * This is possibly caused by a thread that is clipping a link off
 * while at the same time another thread is trying to add an object
 * in the same place being read/write with threads or a write/write 
 * problem occurs.
 * 
 * @author Richard Evans DeSilvey
 */
public class Chain<T> {

    /**
     * The chain must have a head and tail, the head begins
     * the iteration of the queue while the tail helps
     * with adding new links. The chain can exist with a null
     * head and tail.
     */
    private Link<T> chainHead, chainTail;
    
    /**
     * The length of the chain or the number of links including the 
     * head and tail links of this Chain
     */
    private int size;

    public boolean isEmpty() {
        
        return chainHead == null;
        
    }
    
    /**
     * Each Chain has a Link that holds an object. The Link
     * also has a head and a tail pointer so that it can be
     * connected with other links. Only the Node or data
     * stored in the Link can be accessed.
     * @param <T> Object Type
     */
    public class Link<T> {
        
        /**
         * The data stored in this Link. The data type is the same
         * as the Chain's data type.
         */
        private T node;
        
        /**
         * Each Link must have a head and tail to continue 
         * the chain. In some cases either the head or
         * tail will be null. This indicates a broken chain
         * or the Link is the head or tail of the chain.
         */
        private Link<T> head, tail;
        
        /**
         * Creates a new link. Null is not recommended for data
         * initialization since the node cannot be set again later
         * in the future.
         * @param s - the object or data being added to the link.
         */
        public Link(T s){
            node = s;
            head = tail = null;
        }
        
        /**
         * You can only access and read what is in a link.
         * This method acts like a peek for a queue. This ensures
         * that multi-threaded programs wont corrupt the data.
         * @return Returns the node held in this link
         */
        public T getNode(){
            return node;
        }
        
        public String toString(){
            
            return node.toString();
            
        }
        
    }
    
    /**
     * A chain does not need to be initialized with a link. The head
     * of the chain will be set once a new Link is added. This is
     * the same for the tail link as well.
     */
    public Chain() {
        
        chainHead = null;
        
        chainTail = null;
        
        size = 0;
        
    }
    
    /**
     * Adds a new object to the chain by creating a new Link to
     * store the data in. The Link is then added to the end of the
     * Chain. This method is synchronized for multi-threaded 
     * applications. This is the only write method a user can use.
     * @param s - The new object or data being added to the chain.
     */
    public synchronized boolean offer(T s){

        return offer(new Link<T>(s));
        
    }
    /**
     * Private add method for the chain. If the Chain is empty, that being
     * the chainHead is null, then the first object added will become the
     * head until all links are removed or the chain is cleared. If
     * there exists a head link but no tail link then the next object will
     * be the first tail link in the chain. Once the chain is built
     * newer objects in the future will be added to the end of the queue and
     * will become the new chainTail.
     */
    private boolean offer(Link<T> link){
        
        try {
            
            if (chainHead == null) {
                
                chainHead = link;
                
            } else {
                
                if (chainTail == null) {
                    
                    chainTail = link;
                    
                    chainTail.head = chainHead;
                    
                    chainHead.tail = chainTail;
                    
                } else {
                    
                    Link<T> tempLink = chainTail;
                    
                    chainTail = link;
                    
                    chainTail.head = tempLink;
                    
                    tempLink.tail = chainTail;
                    
                }
                
            }
            
            size++;
            
        } catch (Exception e) {
            
            return false;
            
        }
        
        return true;
        
    }
    
    /**
     * Allows you to see what is at the head of the chain.
     * The position of the head never changes and neither of
     * the Links ever change their position until removed.
     * @return The object currently held at the head of the chain.
     */
    public T getHead(){
        return chainHead.getNode();
    }
    /**
     * Allows you to obtain what is at the tail of the chain.
     * The position of the tail never changes and neither of
     * the Links ever change their position until removed.
     * @return The object currently held at the tail of the chain.
     */
    public T getTail(){
        return chainTail.getNode();
    }
    /**
     * The length of the chain represents how many links are connected
     * from counting the head down to the tail.
     * @return The length or size of the chain.
     */
    public int size(){
        return size;
    }
    
    /**
     * Clips off a Link from this chain. This is an inner class
     * method and is used by the iterator for cleaning up Links 
     * that are ready to be removed from the list.
     * @param link 
     */
    private void clipLink(Link<T> link){
        
        if (link == null){
            return;
        }
        
        if (link.equals(chainHead) && chainHead.tail != null) {

            chainHead = chainHead.tail;

            chainHead.head = null;

        }else if (link.equals(chainTail)){

            chainTail = chainTail.head;
                
            chainTail.tail = null;
            
        }else {

            if (link.tail != null){

                link.head.tail = link.tail;

                link.tail.head = link.head;

            }else{
                throw new NullPointerException("Broken Chain Detected");
            }

        }

        link = null;

        size--;
        
    }
    
    /**
     * Completely clears all Links from this chain including the head
     * and tail links by having every object be placed out of scope.
     */
    public void clear(){
        
        chainHead = null;
        
        chainTail = null;
        
    }
    /**
     * Obtain the iterator for this Chain, this is recommended for
     * accessing the Chain to peek and get Link nodes and remove links
     * that are currently selected by the iterator. Each time a link
     * is selected by the <code>next()</code> method, the iterator will
     * hold onto that link for removal by the <code>remove()</code> method.
     * @return The Chain's iterator.
     */
    public Iterator iterator(){
        return new Iter();
    }
    
    private class Iter<T> implements Iterator {

        /**
         * Next node to return an item. The prevNode is used to keep a copy
         * of the link until the next node is selected.
         */
        private Link nextNode, prevNode;


        Iter() {
            nextNode = chainHead;
        }

        /**
         * Moves to the next valid node and returns the link to the next() 
         * method for retrieval of it's data.
         */
        private Link<T> getLink() {
            
            prevNode = nextNode;

            nextNode = nextNode.tail;
            
            return prevNode;
            
        }

        /**
         * Returns true if the tail link is not reached yet.
         * @return 
         */
        public boolean hasNext() {
            return (nextNode != null);
        }

        /**
         * Retrieves the next link in the chain. 
         * @return 
         */
        public T next() {
            
            if (nextNode == null) {
                throw new NoSuchElementException();
            }
            
            return getLink().getNode();
        }

        public void remove() {
            
            if (prevNode == null){
                throw new IllegalStateException();
            }
            
            clipLink(prevNode);
            
        }
        
    }
    
    public String toString(){
        
        StringBuilder str = new StringBuilder();
        
        str.append(chainHead.toString()).append(", ");
        
        Link<T> next = chainHead;
        
        for(;;){
            
            next = next.tail;

            
            if (next != null){
                str.append(next.toString()).append(", ");
            }else{
                break;
            }
            
        }
        
        return str.toString();
        
    }
    
}
