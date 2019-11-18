public class FIFO extends ReplacementAlgorithm{

    private int[] frameList;
    private int head = 0;

    public FIFO(int pageFrameCount) {
        super(pageFrameCount);

        frameList = new int[pageFrameCount];
    }

    public void insert(int pageNumber) {

        boolean exists = false;

        for (int i = 0; i< frameList.length; i++) {
            if (frameList[i] == pageNumber) {
                exists = true; 
                break;
            }
        }
        
        if (!exists) {
            System.out.println("FIFO Insert " + pageNumber );
            frameList[head ++] = pageNumber;
            head = head%frameList.length;
            pageFaultCount++;
        }

    }

}