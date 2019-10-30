/***
 * CS 420 Operating Systems Homework 3
 * 
 * @author Adam Babbit
 * @date 10/14/2019
 * 
 * This file creates two threads that are designed to share the same resource with
 * the intent to display how the JVM scheduler handles the scheduling in this case
 * 
 */

import java.util.Random;

public class race1{
	
	public static void main(String[] args) {
		Resource r = new Resource();
		
		Thread t1 = new ResourceThread(r);
		Thread t2 = new ResourceThread(r);
		
		t1.setName("Thread 1");
		t2.setName("Thread 2");
		
		t1.start();
		t2.start();
	}
}

class ResourceThread extends Thread {
	public Resource r;
	public int count;
	
	public ResourceThread(Resource r) {
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

class Resource {
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

