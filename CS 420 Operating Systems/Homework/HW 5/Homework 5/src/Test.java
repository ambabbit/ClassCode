/**
 * Test harness for LRU and FIFO page replacement algorithms
 *
 * Usage:
 *	java Test <reference string size> <number of page frames>
 */

public class Test
{
	public static void main(String[] args) {
		PageGenerator ref = new PageGenerator(40);

		int[] referenceString = ref.getReferenceString();

		/** Use either the FIFO or LRU algorithms */
		ReplacementAlgorithm fifo = new FIFO(5);
		ReplacementAlgorithm lru = new LRU(5);

		// output a message when inserting a page
		for (int i = 0; i < referenceString.length; i++) {
			//System.out.println("inserting " + referenceString[i]);
			lru.insert(referenceString[i]);
		}

		// output a message when inserting a page
		for (int i = 0; i < referenceString.length; i++) {
			//System.out.println("inserting " + referenceString[i]);
			fifo.insert(referenceString[i]);
		}

		// report the total number of page faults
		System.out.println("LRU faults = " + lru.getPageFaultCount());
		System.out.println("FIFO faults = " + fifo.getPageFaultCount());
	}
}
