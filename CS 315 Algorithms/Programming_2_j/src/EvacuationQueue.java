/**
 * Implementation of an queue for storing Evacuees
 */

 import java.util.LinkedList;

public class EvacuationQueue {
	
	public static void main(String[] args) {
		EvacuationQueue evac = new EvacuationQueue();
	}

    //TODO declare class variables here
    private LinkedList<Evacuee> evac;

    /**
     * Constructor to initialize queue
     */
    public EvacuationQueue() {
        //TODO initialize queue data structure.

        evac = new LinkedList<Evacuee>();
    }

    /**
     * Implementation of enqueue
     * @param evacuee - evacuee to enqueue
     */
    public void enqueue(Evacuee evacuee) {
        //TODO implement enqueue method

        evac.add(evacuee);
    }


    /**
     * Implementation of dequeue
     * @return dequeued evacuee
     */
    public Evacuee dequeue() {
        //@todo implement dequeue method
        Evacuee firstEvac = null;
        if (!isEmpty()) {
            firstEvac = evac.get(0);
            evac.remove(0);
        }
        return firstEvac;
    }

    /**
     * Implementation of peek
     * @return front of queue
     */
    public Evacuee peek() {
        //@todo implement peek method
        if (!isEmpty()) return evac.get(0);

        return null;
    }

    /**
     * Implementation of isEmpty();
     * @return true if full, false otherwise
     */
    public boolean isEmpty() {
        //@todo implement isEmpty method
        return evac.size() == 0;
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
