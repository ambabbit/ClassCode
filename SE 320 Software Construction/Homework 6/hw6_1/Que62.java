public class Que62 {
    public static void main(String[] args) {
        Thread firsto = new Thread(new Runnable() {
            public void run() { System.out.println("first"); };
        });
        firsto.start();
        new Thread(new Runnable() {
            public void run() { System.out.println("second"); };
        }).start();
        new Thread(new Runnable() {
            public void run() { System.out.println("third"); };
        });
        // there is a starting issue here
    }
}