/* From: Anton Kiselev, Adam Babbit
 * To:  Stansbury,Richard S.
 * Subject: Programming assignment 1 
 * Date: 10/7/2019
 * 
 * Description: in this assignment we practiced two sorting algorithms, Bubble sort and Quick Sort.
 * We create 3 different types of arrays, two for each type such as, two are Random arrays, two descending and two ascending array
 * We had to create 6 arrays in total since after Bubble sort all arrays will be sorted and quick sort execution time will be less than expected.
 * 
 * We tried 10, 100, 1000 and 10000 iterations to see by how much time of execution changes. 
 * Code for algorithms was taken from professors notes
 * 
 * To change array size, change the value for constant
 * to execute the program, run the compiler. 
 */
public class Programming1 {
       
	 public final static int ARRAY_SIZE = 1000; //constant for array size
	 
	
       public static void main(String[] arg){
    	   
       //initializing arrays 
       int[] InitialRandomArray = createRandomArray(ARRAY_SIZE);   
       int[] InitialRandomArray2 = InitialRandomArray;       //copying random array for quick sort
       
       int[] InitialDescendingArray = createDescendingArray(ARRAY_SIZE);
       int[] InitialDescendingArray2 = createDescendingArray(ARRAY_SIZE); //descending array for quick sort
         
       int[] InitialAscendingArray = createAscendingArray(ARRAY_SIZE);
       int[] InitialAscendingArray2 =createAscendingArray(ARRAY_SIZE);   //ascending array for quick sort
	
       
       //pointers for quick sort
	   int p1 = 0;  
	   int p2 = ARRAY_SIZE-1;
		
	   //printing initial arrays before sorting
	   printArrays(InitialRandomArray, InitialDescendingArray, InitialAscendingArray, InitialRandomArray2 ,  InitialDescendingArray2 ,  InitialAscendingArray2 ,  ARRAY_SIZE); 
             
       //THIS BLOCK IS CAPTURING START AND END TIME FOR BUBBLE SORT ALGORITHM FOR THREE ARRAYS
       //Capturing time before and after bubble sort algorithm for random array 
       long RandomArrayStartTime = System.nanoTime() ;   
       bubbleSort(InitialRandomArray);
       long RandomArrayEndTime = System.nanoTime() ; 
       
       //Capturing time before and after bubble sort algorithm for descending array
       long DescendingArrayStartTime = System.nanoTime(); 
       bubbleSort(InitialDescendingArray);
       long DescendingArrayEndTime = System.nanoTime() ; 
       
       //Capturing time before and after bubble sort algorithm for ascending array
       long AscendingArrayStartTime = System.nanoTime() ; 
       bubbleSort(InitialAscendingArray);
       long AscendingArrayEndTime = System.nanoTime() ; 
       
       
       //THIS BLOCK IS CAPTURING START AND END TIME FOR QUICK SORT ALGORITHM FOR THREE ARRAYS
       //Capturing time before and after quick sort algorithm for random array 
       long RandomArrayStartTime2 = System.nanoTime(); 
       quickSort(InitialRandomArray2, p1, p2);
       long RandomArrayEndTime2 = System.nanoTime() ; 
       
       //Capturing time before and after quick sort algorithm for random array 
       long DescendingArrayStartTime2 = System.nanoTime(); 
       quickSort(InitialDescendingArray2, p1, p2);
       long DescendingArrayEndTime2 = System.nanoTime() ; 
       
       //Capturing time before and after quick sort algorithm for random array 
       long AscendingArrayStartTime2 = System.nanoTime() ; 
       quickSort(InitialAscendingArray2, p1, p2);
       long AscendingArrayEndTime2 = System.nanoTime() ; 
       
       
       //printing sorted arrays 
       printArrays(InitialRandomArray, InitialDescendingArray, InitialAscendingArray, InitialRandomArray2 ,  InitialDescendingArray2 ,  InitialAscendingArray2 ,  ARRAY_SIZE);    
      
       //We chose to use nanoseconds since on lower amount of iterations, execution time is very low.
       
       //printing elapsed time for each array in bubble sort algorithm
       System.out.println("\nELAPSED TIME FOR BUBBLE SORT IN NANOSECONDS FOR RANDOM ARRAY : " + calculateElapsedTime(RandomArrayStartTime, RandomArrayEndTime ));
       System.out.println("\nELAPSED TIME FOR BUBBLE SORT IN NANOSECONDS FOR DESCENDING ARRAY : " + calculateElapsedTime(DescendingArrayStartTime, DescendingArrayEndTime ));
       System.out.println("\nELAPSED TIME FOR BUBBLE SORT IN NANOSECONDS FOR ASCENDING ARRAY : " + calculateElapsedTime(AscendingArrayStartTime, AscendingArrayEndTime ) + "\n");
       
       
       //printing elapsed time for each array in quick sort algorithm
       System.out.println("\nELAPSED TIME FOR QUICK SORT IN NANOSECONDS FOR RANDOM ARRAY : " + calculateElapsedTime(RandomArrayStartTime2, RandomArrayEndTime2 ));
       System.out.println("\nELAPSED TIME FOR QUICK SORT IN NANOSECONDS FOR DESCENDING ARRAY : " + calculateElapsedTime(DescendingArrayStartTime2, DescendingArrayEndTime2 ));
       System.out.println("\nELAPSED TIME FOR QUICK SORT IN NANOSECONDS FOR ASCENDING ARRAY : " + calculateElapsedTime(AscendingArrayStartTime2, AscendingArrayEndTime2 ) + "\n");
       }
       
       
       //Methods to generate arrays : random array, descending array and ascending array
       private static int[] createRandomArray(int size) {    
           int[] InitialRandomArray = new int[size];        
           for(int i =0; i < size; i++) {
        	   InitialRandomArray[i] = (int)(Math.random() * 2000 + 1);       	  
           }
           return InitialRandomArray;
       }
       
       private static int[]  createDescendingArray(int size) {
    	   int[] InitialDescendingArray = new int[size];        
           for(int i =0; i < size; i++) {
        	   InitialDescendingArray[i] = size - i;       	  
           }
           return InitialDescendingArray;           
       }
       
       private static int[] createAscendingArray(int size) {
    	   int[] InitialAscendingArray = new int[size];        
           for(int i =0; i < size; i++) {
        	   InitialAscendingArray[i] = i;        	   
           }
           return InitialAscendingArray;
       }
       
       
       //Code is used from professor's notes 
       public static void bubbleSort(int[] array ) {
    	   boolean swapped; 
    	   for( int i=0; i < array.length; i++) {
    		   swapped = false;
    		   for( int j = array.length -1; j>i; j--) {
    			   if (array[j] < array [j - 1] ) {
    				   swap(array, j , j-1);
    				   swapped = true;
    			   }
    		   }
    		   if(!swapped) return;
    	   }
       }
       
	
	private static void quickSort(int[] array, int p1, int p2) {
		if (p2 -1 <= p1) return;
		int boundary = partition(array, p1,p2);
		quickSort(array, p1, boundary);
		quickSort(array, boundary+1, p2);
	}
	
	private static int partition(int[] array, int p1, int p2) {
		int pivot = array[p1];
		int lh = p1 +1;
		int rh = p2-1;
		
		while (true) {
			while (lh < rh && array[rh] >= pivot) rh--;
			while (lh < rh && array[lh] < pivot) lh++;			
			if (lh == rh) break;
			swap(array, lh, rh);
			}
		if(array[lh] >= pivot) return p1; 
		array[p1] = array[lh];
		array[lh] = pivot;
		return lh;
		}
	

	//method to print out sorted arrays
	private static void printArrays( int Random[], int Descending[] , int Ascending[], int Random2[], int Descending2[] , int Ascending2[],int size) {
		System.out.println( "Bubble sort"+ "\t\t\t\t\t" + "Quick sort"); // header to separate data for Bubble and quick sort algorithm 
		System.out.println( "Random1:" +"\t" + "Descending1:" +"\t" + "Ascending1:" +"\t" + "Random2:" +"\t" + "Descending2:" +"\t" + "Ascending2:");  // printing a header for table
		for(int i =0; i < size; i++) {
	    	   System.out.println(Random[i] + "\t\t" +Descending[i]+ "\t\t" + Ascending[i]+ "\t\t" +  Random2[i] + "\t\t" +Descending2[i]+ "\t\t" + Ascending2[i] );
	           }
		System.out.println("\n\n");

	}
	
	//method to calculate elapsed time for each array
	private static int calculateElapsedTime(long randomArrayStartTime, long randomArrayEndTime) {
		return (int) (randomArrayEndTime - randomArrayStartTime); // total time elapsed
	}
	
	//method to swap values
	private static void swap( int[] array, int x , int y) {
		int temp = array[x];   //Swapping part of the algorithm
		   array[x] = array[y];
		   array[y] = temp;
	}
	
	}
