/***
 * CS 420 Operating Systems Homework 3
 * 
 * @author Adam Babbit
 * @date 10/14/2019
 * 
 * This file creates two threads that are designed to share the same resource with
 * the intent to display how the JVM scheduler handles the scheduling in this case
 * 
 * Explination at the Bottom
 */
import java.util.Random;

public class race3{
	
	public static void main(String[] args) {
		Resource3 r = new Resource3();
		
		Thread t1 = new ResourceThread3(r);
		Thread t2 = new ResourceThread3(r);
		
		t1.setName("Thread 1");
		t2.setName("Thread 2");
		
		t1.start();
		t2.start();
	}
}

class ResourceThread3 extends Thread {
	public Resource3 r;
	public int count;
	
	public ResourceThread3(Resource3 r) {
		this.r = r;
	}
	
	public void run() {
		while (count++ < 100) {
			int val = r.getValue();
			if (Thread.currentThread().getName() == "Thread 1") {
				try {
					Thread.sleep(new Random().nextInt((200 - 10) + 1) + 10);
					r.setValue(val+1);	
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (Thread.currentThread().getName() == "Thread 2"){
				try {
					
					r.setValue(val-1);
					Thread.sleep(new Random().nextInt((200 - 10) + 1) + 10);
					
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
		}
	}
}
class Resource3 {
	private volatile int value;
	
	public synchronized int getValue(){
		return value;
	}
	// Value should only be changed by 1 
	
	public synchronized void setValue(int v){
		
		if (Math.abs(value-v) != 1) {
			System.out.println("Mismatch: "+value+" "+v);
		}
		
		value = v;
	}
}

/***
 * with both of the methods synchronized, only one thread can touch them at a time.
 * This stops the threads from stepping on each other and manipulating the data at
 * the same time. This would make it so there would not be an instance of one thread
 * data being changed by another while it's being used.
 */
