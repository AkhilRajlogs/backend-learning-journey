package multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutorDemo {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(1);

        for (int i = 1; i <= 5; i++) {
            int taskNumber = i;

            executor.submit(() -> {
                System.out.println("Task " + taskNumber + " executed by " +
                        Thread.currentThread().getName());
            });
        }

        executor.shutdown();
    }
}