// https://www.geeksforgeeks.org/multithreading-in-java/
// Java code for thread creation by implementing 
// the Runnable Interface 

class MulthreadingDemo implements Runnable {

    public void run() {
        try {
            // Displaying the thread that is running 
            System.out.println("Thread "
                    + Thread.currentThread().getId()
                    + " is running");

        } catch (Exception e) {
            // Throwing an exception 
            System.out.println("Exception is caught");
        }
    }
}

// Main Class 
class EjemploRunnable {

    public static void main(String[] args) {
        int n = 8; // Number of threads 
        for (int i = 0; i < n; i++) {
            Thread object = new Thread(new MulthreadingDemo());
            object.start();
        }
    }
}
