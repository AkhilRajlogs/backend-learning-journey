package collections;

import java.util.LinkedList;
import java.util.Queue;

public class LinkedListQueueDemo {

    public static void main(String[] args) {

        // LinkedList used as Queue implementation
        Queue<String> queue = new LinkedList<>();

        // Adding elements
        queue.offer("Order 1");
        queue.offer("Order 2");
        queue.offer("Order 3");

        System.out.println("Initial Queue:");
        System.out.println(queue);

        // View head element
        System.out.println("\nNext order to process: " + queue.peek());

        // Process queue
        while (!queue.isEmpty()) {
            String order = queue.poll();
            System.out.println("Processing " + order);
        }

        System.out.println("\nQueue after processing:");
        System.out.println(queue);
    }
}

/*

    // Sample Output: 
    
        Initial Queue:
        [Order 1, Order 2, Order 3]

        Next order to process: Order 1
        Processing Order 1
        Processing Order 2
        Processing Order 3

        Queue after processing:
        []
*/