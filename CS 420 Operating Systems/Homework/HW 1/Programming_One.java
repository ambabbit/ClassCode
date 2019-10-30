/***
 * CS 420 Operating Systems Homework 1
 * 
 * @author Adam Babbit
 * @date 9/16/19
 *
 * Creates and runs three threads that print integers between 1-4000. Each thread 
 * Prints an A, B, or C respectively before the integer. It accomplishes multi-threading
 * 
 * Explanation at bottom of script
 */


public class Programming_One extends Thread {
	
	public static void main(String[] args) {
		Thread t1 = new Programming_One();
		Thread t2 = new Programming_One();
		Thread t3 = new Programming_One();
		
		t1.setName("A");
		t2.setName("B");
		t3.setName("C");
		
		t1.start();
		t2.start();
		t3.start();
		
	}
	
	public void run() {
		for (int i = 1; i<=4000; i++) {
			System.out.println(Thread.currentThread().getName() + i);
		}
	}
}

/***
 * The scheduler randomly choose what thread gets to run next. This results in
 * a high mix of the three threads taking turns randomly. 
 */
