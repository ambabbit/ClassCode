/***
 * CS 420 Operating Systems Homework 3
 * 
 * @author Adam Babbit
 * @date 10/14/2019
 * 
 * This file creates two threads that are designed to share the same resource with
 * the intent to display how the JVM scheduler handles the scheduling in this case
 * 
 * Explanation at Bottom
 */

import java.util.Random;

public class race4{
	
	public static void main(String[] args) {
		Resource4 r = new Resource4();
		
		Thread t1 = new ResourceThread4(r);
		Thread t2 = new ResourceThread4(r);
		
		t1.setName("Thread 1");
		t2.setName("Thread 2");
		
		t1.start();
		t2.start();
	}
}

class ResourceThread4 extends Thread {
	public Resource4 r;
	public int count;
	
	public ResourceThread4(Resource4 r) {
		this.r = r;
	}
	
	public void run() {
		while (count++ < 100) {
			if (Thread.currentThread().getName() == "Thread 1") {
				try {
					Thread.sleep(new Random().nextInt((200 - 10) + 1) + 10);
					r.setValue(r.getValue()+ 1);	
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (Thread.currentThread().getName() == "Thread 2"){
				try {
					
					r.setValue(r.getValue()-1);
					Thread.sleep(new Random().nextInt((200 - 10) + 1) + 10);
					
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
		}
	}
}

class Resource4 {
	private volatile int value;
	
	public int getValue(){
		return value;
	}
	// Value should only be changed by 1 
	
	public synchronized void setValue(int v){

		int val = getValue();
		
		if (Math.abs(value-(v)) != 1) {
			System.out.println("Mismatch: "+value+" "+(v));
		}
		
		value = (v);
	}
}

/***
 * to fix the problem, the setValue method needs to be synchronized, and the value
 * is not cached into the running class, it is called when passed into the setValue method.
 * This blocks the other threads from messing with the values while another one is, and 
 * keeps the stored value current.
 */
