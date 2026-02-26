package collections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PerformanceComparison {

    private static final int ELEMENTS = 100_000;

    public static void main(String[] args) {

        testArrayList();
        testLinkedList();
    }

    private static void testArrayList() {
        List<Integer> list = new ArrayList<>();

        long start = System.nanoTime();

        for (int i = 0; i < ELEMENTS; i++) {
            list.add(i);
        }

        for (int i = 0; i < ELEMENTS / 2; i++) {
            list.remove(0);
        }

        long end = System.nanoTime();

        System.out.println("ArrayList time: " + (end - start) / 1_000_000 + " ms");
    }

    private static void testLinkedList() {
        List<Integer> list = new LinkedList<>();

        long start = System.nanoTime();

        for (int i = 0; i < ELEMENTS; i++) {
            list.add(i);
        }

        for (int i = 0; i < ELEMENTS / 2; i++) {
            list.remove(0);
        }

        long end = System.nanoTime();

        System.out.println("LinkedList time: " + (end - start) / 1_000_000 + " ms");
    }
}

//output: 
// ArrayList time: 338 ms
// LinkedList time: 7 ms