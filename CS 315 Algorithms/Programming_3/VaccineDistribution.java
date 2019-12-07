import java.io.*;
import java.text.ParseException;
import java.util.Scanner;

// import org.graalvm.compiler.graph.spi.Canonicalizable.Binary;


public class VaccineDistribution {

	//Define your variables here
	int batchSize;
	MinHeap<Integer, Integer> queue;
	BinarySearchTree<Integer, Patient> patientList;
	final int NUM_PATIENTS = 60;
		
	
	public VaccineDistribution(int size)
	{
		this.batchSize = size;
		//Initialize your data structures here
		
		queue = new MinHeap<Integer, Integer>(NUM_PATIENTS);
		patientList = new BinarySearchTree<Integer, Patient>();

	}
	
	/**
	 * Reads a file of patient records and stores the records in a binary search tree.
	 * For each patient ID, the patient ID is stored in a priority queue (min heap) given 
	 * some pre-defined criteria.
	 * 
	 * @param file
	 * @throws IOException
	 * @throws ParseException
	 */
	public void loadPatients(String file) throws IOException, ParseException
	{
		
		BufferedReader in = new BufferedReader(new FileReader(file));
		
		String patientStr;
		Patient p;
		while((patientStr = in.readLine()) != null) {
			p = new Patient(patientStr);
			int priority = 999;
		
			//ADD YOUR CODE HERE!
			patientList.insert(p.getID(), p);

			// determining priority
			if (p.isPregnant()) {
				priority = 0;
			} else if (p.getAge() < 10 || p.getAge() > 65)  {
				priority = 1;
			} else {
				priority = p.getAge();
			}

			// queueing into heap
			queue.enqueue(priority, p.getID());
			
		}
		in.close();
		
	}
	
		
	/**
	 * This method goes patient-by-patient assigning a vaccine batch number
	 * based on the patient's priority within the min heap.
	 */
	public void processVacines()
	{
		int batchNum = 0;
		Patient curPat = null;
		
		// looping till queue is empty
		while (!queue.isEmpty()) {
			// looping through batch size
			for (int i = 0; i < batchSize; i++) {
				int curPatNum = queue.dequeue();
				curPat = patientList.search(curPatNum);
				curPat.setVacineBatchNumber(batchNum);
			}
			batchNum++;
		}
	}
	
	/**
	 * Prints the database of patient records.
	 */
	public void printResults()
	{
		patientList.inorderTraversal();
	}
	
	/**
	 * This is your main.
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(String [] args) throws IOException, ParseException {
		
		//Get file name and parse the file to get all characters.
		boolean validFile = false;
		VaccineDistribution vd;
		
		Scanner in = new Scanner(System.in);
		System.out.print("Please enter the file name: ");
		String filename = in.nextLine();		
		
		System.out.print("Please enter the size of each batch: ");
		int size = Integer.parseInt(in.nextLine());	
		in.close();	

		
		vd = new VaccineDistribution(size);
		vd.loadPatients(filename);
		validFile = true;
		vd.processVacines();
		vd.printResults();		
		
	}
	
}
