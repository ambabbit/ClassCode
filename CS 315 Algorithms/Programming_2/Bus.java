/**
 * Implement of a stack for Evacuees representing a bus
 *
 */

public class Bus {

    int id;

    //@todo declare any class variables for the array-based stack implementation here.

    /**
     * Implement Default constructor
     * @param id - identifier for the bus (integer)
     * @param capacity - capacity of stack.
     */
    public Bus(int id, int capacity) {
        this.id = id;

        //@todo - initialize busStack
    }

    /**
     * Implement push
     * @param evac - evacuee pushed onto stack
     */
    public void push(Evacuee evac) {

        //@todo - implement array-based push method.
    }

    /**
     * Implement pop method
     * @return popped evacuee
     */
    public Evacuee pop() {
        //@todo - implement array-based pop method.
        return null;
    }

    /**
     * Implement top method
     * @return top evacuee
     */
    public Evacuee top() {
        //@todo - implement array-based top method
        return null;
    }

    /**
     * Implement isEmpty method
     * @return true if empty
     */
    public boolean isEmpty() {
        //@todo - implement array-based isEmpty method
        return true;
    }

    /**
     * Implement isFull method
     * @return true if full.
     */
    public boolean isFull() {
        //@todo - implement array-based isFull method
        return true;
    }
}
