/***
 * @author Adam Babbit
 * @date 9/18/19
 * 
 * This class is an example of three threads competing for the same resource. 
 * t1, t2, and t3 all use the Runnable Programming_Three class for it's run method.
 * It implements the Thread.Yield() method to allow each thread a chance to run
 * 
 * Explanation at bottom of script
 */


public class Programming_Three implements Runnable{

	static int count = 0;

	public static void main(String[] args) {
		Runnable runnable = new Programming_Three();
		
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
			count += 1;
			System.out.println(Thread.currentThread().getName() + i);
			if (count%10 == 0) {
				Thread.yield();
				System.out.println("Yield");
			}
		}

	}
}

/***
 * This Yield method makes the current thread pause temporarily, allowing other
 * threads a chance to function. This is useful in cases in which one thread is
 * performing a much less time consuming action than another. This gives that
 * less time consuming thread a chance to run if the larger one is already running
 */
