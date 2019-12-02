import java.io.*;
import java.text.ParseException;
import java.util.Scanner;


public class VaccineDistribution {

	//Define your variables here
	int batchSize;
	int priority;	
	MinHeap<Integer,Integer> heap;
    BinarySearchTree<Integer,Patient> tree = new BinarySearchTree<Integer,Patient>();
	
    public VaccineDistribution(int size)
	{
		this.batchSize = size;
		//Initialize your data structures here
		
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
			
			//ADD YOUR CODE HERE!
			
			tree.insert(p.patientID, p);
			if(p.isPregnant()) {
				priority = 0;
			}else if(p.age < 10) {
				priority = 1;
			}else if(p.age >65){
				priority = 1;	
			}else{
				priority = p.age;
			}
			heap.enqueue(priority, p.patientID);
			
		}
	
	}
	
		
	/**
	 * This method goes patient-by-patient assigning a vaccine batch number
	 * based on the patient's priority within the min heap.
	 */
	@SuppressWarnings("null")
	public void processVacines()
	{
		int batchId = 0;
		Patient patient = null;
		while(!heap.isEmpty()) {
			for(int i =0; i < batchSize; i++) {
				
				Integer patientID = heap.dequeue();
				if(patientID == null) break;
				 //set batch
				 patient = tree.search(patientID);
				patient.setVacineBatchNumber(batchId);
			}
			batchId++;
		}
		
		
		
	}
	
	/**
	 * Prints the database of patient records.
	 */
	public void printResults()
	{
		tree.inorderTraversal();
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
		Scanner in = new Scanner(System.in);
		System.out.print("Please enter the file name: ");
		String filename = in.nextLine();		
		
		System.out.print("Please enter the size of each batch: ");
		int size = Integer.parseInt(in.nextLine());		

		
		VaccineDistribution vd = new VaccineDistribution(size);
		vd.loadPatients(filename);
		vd.processVacines();
		vd.printResults();		
	}
	
}
