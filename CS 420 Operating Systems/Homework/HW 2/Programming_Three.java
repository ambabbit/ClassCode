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


public class Programming_Three extends Thread {

	static int[] times = new int[3];
	static 	int i = -1;
	static long start = System.currentTimeMillis();
	
	public static void main(String[] args) throws InterruptedException {

		times[0] = 500;
		times[1] = 500;
		times[2] = 1300;
		
		Thread thread1 = new Programming_Three();
		Thread thread2 = new Programming_Three();
		Thread thread3 = new Programming_Three();
		
		thread3.setPriority(Thread.MAX_PRIORITY);
		thread2.setPriority(Thread.MIN_PRIORITY);
		thread1.setPriority(Thread.NORM_PRIORITY);
		
		thread1.setName("Thread 1");
		thread2.setName("Thread 2");
		thread3.setName("Thread 3");
		

		thread2.start();
		thread3.start();
		thread1.start();

		thread2.join();
		thread3.join();
		thread1.join();

		System.out.println(Thread.currentThread().getName() +" " + (System.currentTimeMillis() - start));
	}
	
	public void run() {
		printTime();
	}
	
	public synchronized void printTime() {

		try {
			Thread.sleep(times[++i]);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() +" " + (System.currentTimeMillis() - start)
				+" priority " + Thread.currentThread().getPriority());
		
	}
}

/***
 * The join() command tells the main method not to run the print statement until the newly
 * started threads are finished. Then it prints it out.
 */
