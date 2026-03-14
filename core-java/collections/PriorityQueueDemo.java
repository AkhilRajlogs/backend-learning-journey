package collections;

import java.util.PriorityQueue;

public class PriorityQueueDemo {

    public static void main(String[] args) {

        // PriorityQueue with natural ordering (min-heap)
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        // Adding elements
        queue.offer(30);
        queue.offer(10);
        queue.offer(20);
        queue.offer(40);

        System.out.println("PriorityQueue contents:");
        System.out.println(queue);

        System.out.println("\nProcessing elements based on priority:");

        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }
}

/*
    PriorityQueue contents:
    [10, 30, 20, 40]

    Processing elements based on priority:
    10
    20
    30
    40

    Reason:

PriorityQueue is implemented using a binary heap.
The smallest element always comes out first.

PriorityQueue does NOT maintain sorted order internally.

If you print the queue directly:
System.out.println(queue);

It may show something like:
[10, 30, 20, 40]

But removal order is still priority-based.
*/