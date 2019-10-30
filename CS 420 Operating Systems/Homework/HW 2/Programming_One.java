/***
 * CS 420 Operating Systems Homework 1
 * 
 * @author Adam Babbit
 * @date 9/28/19
 *
 * Creates and runs three threads that print integers between 1-4000. Each thread 
 * Prints an A, B, or C respectively before the integer. It accomplishes multi-threading
 * 
 * Explanation at bottom of script
 */


public class Programming_One extends Thread {

	static int[] times = new int[3];
	static 	int i = -1;
	static long start = System.currentTimeMillis();
	
	public static void main(String[] args) {

		times[0] = 500;
		times[1] = 500;
		times[2] = 1300;
		
		Thread thread1 = new Programming_One();
		Thread thread2 = new Programming_One();
		Thread thread3 = new Programming_One();
		
		thread1.setName("Thread 1");
		thread2.setName("Thread 2");
		thread3.setName("Thread 3");
		
		thread1.start();
		thread2.start();
		thread3.start();
		

		System.out.println(Thread.currentThread().getName() +" " + (System.currentTimeMillis() - start));
	}
	
	public void run() {
		
		try {
			Thread.sleep(times[++i]);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(Thread.currentThread().getName() +" " + (System.currentTimeMillis() - start));
	}
}

/***
 * All the threads vary by less than 10 milliseconds from the time stated in the sleep statement
 * This is probably the time it takes to start up the thread, and print the time, the majority 
 * of the time the elapsed time will very closely match the sleep time as this purposefully delays 
 * the running of the thread
 */
