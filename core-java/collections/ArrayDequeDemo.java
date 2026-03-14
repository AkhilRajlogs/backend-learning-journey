package collections;

import java.util.ArrayDeque;
import java.util.Queue;

public class ArrayDequeDemo {

    public static void main(String[] args) {

        // ArrayDeque used as a Queue
        Queue<String> queue = new ArrayDeque<>();

        // Adding elements
        queue.offer("Task A");
        queue.offer("Task B");
        queue.offer("Task C");

        System.out.println("Queue contents:");
        System.out.println(queue);

        // Peek at the head element
        System.out.println("\nNext task to process: " + queue.peek());

        // Processing tasks
        while (!queue.isEmpty()) {
            String task = queue.poll();
            System.out.println("Processing: " + task);
        }

        System.out.println("\nQueue after processing:");
        System.out.println(queue);
    }
}

/*
    // Sample output:

    Queue contents:
    [Task A, Task B, Task C]

    Next task to process: Task A
    Processing: Task A
    Processing: Task B
    Processing: Task C

    Queue after processing:
    []

// Why ArrayDeque is preferred:

    • Array-based implementation
    • no node allocations like LinkedList
    • faster for queue operations
    • resizable circular buffer

    Java documentation itself recommends ArrayDeque over LinkedList for queues.

*/