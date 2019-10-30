public class QuickSort {

    public static void main(String[] args) {
        QuickSort quick = new QuickSort();

        int[] testArray = {2124, 232, 19203, 12, 1, 1321, 1231234, 12312};
        quick.quickSort(testArray, 0, 8);
        String arrayString = "";
        for (int e : testArray) {
            arrayString += e + ", ";
        }
        System.out.println(arrayString);
    }

    public void quickSort(int[] array, int p1, int p2) {
        if (p2 -1 <= p1) return;

        int bound  = seperate(array, p1, p2);

        quickSort(array, p1, bound);
        quickSort(array, bound+1, p2);

    }

    private int seperate(int[] array, int p1, int p2) {
        int lh = p1 +1;
        int rh = p2 -1;
        int pivot = array[p1];

        while (true){
            while (lh < rh && array[rh] >= pivot) rh --;
            while (lh < rh && array[lh] < pivot) lh ++;

            if (lh == rh) break;

            int temp = array[lh];
            array[lh] = array[rh];
            array[rh] = temp;
        }

        if (array[lh] >= pivot) return p1;

        array[p1] = array[lh];
        array[lh] = pivot;
        return lh;
    }
}