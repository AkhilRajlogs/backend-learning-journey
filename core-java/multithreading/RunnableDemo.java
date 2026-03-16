package multithreading;

class MyRunnable implements Runnable {

    @Override
    public void run() {

        for (int i = 1; i <= 5; i++) {
            System.out.println("Runnable Thread: " + i);
        }

    }
}

public class RunnableDemo {

    public static void main(String[] args) {

        Thread thread = new Thread(new MyRunnable());
        thread.start();

        for (int i = 1; i <= 5; i++) {
            System.out.println("Main Thread: " + i);
        }

    }

}

 /*
    Sample Output (order may vary):

    Main Thread: 1
    Main Thread: 2
    Main Thread: 3
    Main Thread: 4
    Main Thread: 5
    Runnable Thread: 1
    Runnable Thread: 2
    Runnable Thread: 3
    Runnable Thread: 4
    Runnable Thread: 5

    Note:
    The exact order of execution may vary because the JVM schedules
    threads independently. Both the main thread and the runnable thread
    execute concurrently.
*/