/***
 * CS 420 Operating Systems Homework 3
 * 
 * @author Adam Babbit
 * @date 10/14/2019
 * 
 * This file creates two threads that are designed to share the same resource with
 * the intent to display how the JVM scheduler handles the scheduling in this case
 * 
 * Explination at bottom of script
 */


public class race2{
	
	public static void main(String[] args) {
		Resource2 r = new Resource2();
		
		Thread t1 = new ResourceThread2(r);
		Thread t2 = new ResourceThread2(r);
		
		t1.setName("Thread 1");
		t2.setName("Thread 2");
		
		t1.start();
		t2.start();
	}
}

class ResourceThread2 extends Thread {
	public Resource2 r;
	public int count;
	
	public ResourceThread2(Resource2 r) {
		this.r = r;
	}
	
	public void run() {
		while (count++ < 1000) {
			int val = r.getValue();
			if (Thread.currentThread().getName() == "Thread 1") {
				
				r.setValue(val+1);
					
			} else if (Thread.currentThread().getName() == "Thread 2"){

				r.setValue(val-1);
			}
		}
	}
}

class Resource2 {
	private volatile int value;
	
	public int getValue(){
		return value;
	}
	// Value should only be changed by 1 
	
	public void setValue(int v){
		
		if (Math.abs(value-v) != 1) {
			System.out.println("Mismatch: "+value+" "+v);
		}
		
		value = v;
	}
}
/**
 * The JVM schedules one thread to run for much longer than the other when no enforced
 * sleeping is performed on the thread. With no enforced breaking, the JVM will allow 
 * one thread to run for much longer than the others, with seemingly random choices as to
 * which one runs and for how long.
 */
