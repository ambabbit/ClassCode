/**
 * Implement of a stack for Evacuees representing a bus
 *
 */

public class Bus {

    private int id, size, capacity;
    
    Evacuee[] busStack;

	public static void main(String[] args) {
		Bus b = new Bus(1,2);
		
		System.out.println("Is Bus Empty: " +b.isEmpty());
		System.out.println("Is Bus Full: " +b.isFull());
        System.out.println("Top of Stack: " +b.top());
        System.out.println("Adding Evacuee");
		b.push(new Evacuee("tester"));
		System.out.println("Top of Stack: " + b.top());
		System.out.println("Is Bus Empty: " +b.isEmpty());
		System.out.println("Is Bus Full: " +b.isFull());
		System.out.println("Pop of Stack: " + b.pop());
		
	}


    /**
     * Implement Default constructor
     * @param id - identifier for the bus (integer)
     * @param capacity - capacity of stack.
     */
    public Bus(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        
        busStack = new Evacuee[capacity];
        size = 0;
    }

    /**
     * Implement push
     * @param evac - evacuee pushed onto stack
     */
    public void push(Evacuee evac) {

    	if (!isFull()) busStack[size++] = evac;
    }

    /**
     * Implement pop method
     * @return popped evacuee
     */
    public Evacuee pop() {    	
    	if (!isEmpty()) return busStack[--size];
        return null;
    }

    /**
     * Implement top method
     * @return top evacuee
     */
    public Evacuee top() {
        if (!isEmpty()) return busStack[size-1];
        return null;
    }

    /**
     * Implement isEmpty method
     * @return true if empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Implement isFull method
     * @return true if full.
     */
    public boolean isFull() {
        return size == capacity;
    }
    
    public int setId(int id) {
    	this.id = id;
    	
    	return this.id;
    }
    
    public int getId() {
    	return id;
    }
}
