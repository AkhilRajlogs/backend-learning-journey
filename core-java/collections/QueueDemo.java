package collections;

import java.util.LinkedList;
import java.util.Queue;

public class QueueDemo {

    public static void main(String[] args) {

        // Queue using LinkedList implementation
        Queue<String> taskQueue = new LinkedList<>();

        // Adding tasks
        taskQueue.offer("Process payment");
        taskQueue.offer("Generate invoice");
        taskQueue.offer("Send email confirmation");

        System.out.println("Initial Queue:");
        System.out.println(taskQueue);

        // Peek (view head without removing)
        System.out.println("\nNext task to execute: " + taskQueue.peek());

        // Processing tasks (FIFO)
        while (!taskQueue.isEmpty()) {
            String task = taskQueue.poll();
            System.out.println("Processing task: " + task);
        }

        System.out.println("\nQueue after processing:");
        System.out.println(taskQueue);
    }
}

    /*
    Sample output:
        Initial Queue:
        [Process payment, Generate invoice, Send email confirmation]

        Next task to execute: Process payment
        Processing task: Process payment
        Processing task: Generate invoice
        Processing task: Send email confirmation

        Queue after processing:
        []
     */