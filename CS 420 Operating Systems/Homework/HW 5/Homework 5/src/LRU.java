class LRU extends ReplacementAlgorithm {
    
    private int[] frameList;
    private int[] recentFrame;

    public LRU(int pageFrameCount) {
        super(pageFrameCount);

        frameList = new int[pageFrameCount];
        recentFrame = new int[pageFrameCount];
    }

    public void insert(int pageNumber) {
        
        boolean isContained = false;

        for (int k = 0; k < frameList.length; k++) {
            recentFrame[k]++;
        }

        for (int i = 0; i<frameList.length; i++) {
            if (frameList[i] == pageNumber) {
                isContained = true;
                recentFrame[i] = 0;
                break;
            }
        }
        
        if (!isContained) {
            int maxVal = Integer.MIN_VALUE;
            int minPos = 0;
            
            for (int l = 0; l < frameList.length; l++) {
                if (maxVal > recentFrame[l]) {
                    maxVal = recentFrame[l];
                    minPos = l;
                }
            }

            System.out.println("LRU Insert " + pageNumber);
            frameList[minPos] = pageNumber;
            recentFrame[minPos] =0;
            pageFaultCount++;
        }

    }

}