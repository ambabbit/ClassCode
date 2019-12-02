
@SuppressWarnings("unchecked")
public class MinHeap<P extends Comparable<P>, T> {

		
	HeapNode<P,T> [] heap;
	int count = 0;
	
	public MinHeap(int maxSize)
	{
		heap = new HeapNode[maxSize];
	}
	
	
	public void enqueue(P priority, T item)
	{
		if (count == heap.length) {
			System.err.println("Heap full, unable to add");
			return;
		}
		
		int parent;
		int cur = count; //Add item to the last location
		heap[cur] = new HeapNode<P,T>(priority, item);
		count++;  //Increment Count;
		
		while (cur > 0) {
			
			parent = getParentIndex(cur);
			
			//if cur is smaller than parent, then swap
			if (heap[cur].priority.compareTo(heap[parent].priority) < 0) {
				swap(cur, parent);
				cur = parent;
			}
			else
				return;	
		}
	}
	
	
	public T dequeue()
	{
		//Check for empty
		if (count == 0) return null;
		
		//Remove item and store in tmp
		T tmp = (T) heap[0].info;
		count--;
		
		//Copy last leaf into the root location
		heap[0] = heap[count];
		
		//Move new root down to its proper locaiton in the heap
		//i.e. repair heap
		moveDown(0,count-1);
			
		return (T) tmp;
	}
	
	public void moveDown(int first, int last)
	{
	    int cur = first;
	    int largest = 2*cur+1; //Left child of cur
	    
	    while (largest <= last) {

	    	//If two children, find smallest of two.
	    	if (largest < last) 
	    		if (heap[largest].priority.compareTo(heap[largest+1].priority) > 0)
	    			largest++; 

	    	//Compare cur with smallest sub-child. 
	    	if (heap[cur].priority.compareTo(heap[largest].priority) > 0) {
	    		swap(cur, largest);
	    		cur = largest; 
	    		largest = 2*cur + 1; 
	    	}
	    	else
	    		return; //Done restoring
	    }

	}

	private int getParentIndex(int index)
	{
		if ((index % 2) == 0)
			return (int) (index - 2)/2;
		else
			return (int) (index - 1)/2;
	}
	
	private void swap(int cur, int parent)
	{
		HeapNode tmp = heap[cur];
		heap[cur] = heap[parent];
		heap[parent] = tmp;
	}

	public boolean isEmpty()
	{
		return (count == 0);
	}
	
	public boolean isFull()
	{
		return (count == heap.length);
	}
		
	

	@SuppressWarnings("hiding")
	public class HeapNode<P, T> {
		public P priority;
		public T info;
		
		public HeapNode(P priority, T info) {
			this.priority = priority;
			this.info = info;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("here");
		
		Integer [] data = new Integer[]{1, 2, 10, 5, 30, 7, 9, 100};
		
		MinHeap<Integer,Integer> h = new MinHeap<Integer,Integer>(10);
		
		
		for (int i=0; i < data.length; i++) {
			h.enqueue(data[i],data[i]);
		}
		
		
		while (h.isEmpty() == false) {
			System.out.println(h.dequeue());
		}
		
	}
	
}
