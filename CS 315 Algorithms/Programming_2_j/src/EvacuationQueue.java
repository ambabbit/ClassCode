/**
 * Implementation of an queue for storing Evacuees
 */

 import java.util.LinkedList;

public class EvacuationQueue {
	
	public static void main(String[] args) {
		EvacuationQueue evac = new EvacuationQueue();
	}

    //TODO declare class variables here


    /**
     * Constructor to initialize queue
     */
    public EvacuationQueue() {
        //TODO initialize queue data structure.
        LinkedList<Evacuee> evac = new LinkedList<Evacuee>();
    }

    /**
     * Implementation of enqueue
     * @param evacuee - evacuee to enqueue
     */
    public void enqueue(Evacuee evacuee) {
        //TODO implement enqueue method
    }


    /**
     * Implementation of dequeue
     * @return dequeued evacuee
     */
    public Evacuee dequeue() {
        //@todo implement dequeue method
        return null;
    }

    /**
     * Implementation of peek
     * @return front of queue
     */
    public Evacuee peek() {
        //@todo implement peek method
        return null;
    }

    /**
     * Implementation of isEmpty();
     * @return true if full, false otherwise
     */
    public boolean isEmpty() {
        //@todo implement isEmpty method
        return true;
    }

    /**
     * Implementation of isFull
     * @return true if full, false otherwise
     */
    public boolean isFull() {
        //@todo implement isEmpty method
        return true;
    }
}