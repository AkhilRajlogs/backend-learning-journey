package collections;

import java.util.Set;
import java.util.TreeSet;

public class TreeSetDemo {

    public static void main(String[] args) {

        System.out.println("=== TreeSet Example ===");

        Set<Integer> numbers = new TreeSet<>();

        numbers.add(50);
        numbers.add(10);
        numbers.add(30);
        numbers.add(20);
        numbers.add(10); // duplicate

        System.out.println(numbers);
    }
}

/*
Sample Output:

=== TreeSet Example ===
[10, 20, 30, 50]

Explanation:
TreeSet automatically sorts elements.
Duplicates are ignored.
*/