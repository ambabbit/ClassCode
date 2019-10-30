/***
 * @author Adam Babbit
 * @date 9/18/19
 * 
 * This class is an example of three threads competing for the same resource. 
 * t1, t2, and t3 all use the Runnable Programming_Three class for it's run method.
 * 
 * Explanation at bottom of script
 */
public class Programming_Two implements Runnable{
	
	public static void main(String[] args) {
		Programming_Two runnable = new Programming_Two();
		
		Thread t1 = new Thread(runnable);
		Thread t2 = new Thread(runnable);
		Thread t3 = new Thread(runnable);
		
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
	
	/**
	 * This performs similarly to the first task. The scheduler decides what
	 * thread will perform the next task randomly, and lets it run for a random
	 * amount of time
	 */
}
