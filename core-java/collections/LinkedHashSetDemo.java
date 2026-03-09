package collections;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class LinkedHashSetDemo {

    public static void main(String[] args) {

        System.out.println("=== HashSet Example ===");

        Set<String> hashSet = new HashSet<>();

        hashSet.add("Java");
        hashSet.add("Python");
        hashSet.add("C++");
        hashSet.add("Java"); // duplicate

        System.out.println(hashSet);


        System.out.println("\n------------------------------");
        System.out.println("\n=== LinkedHashSet Example ===");

        Set<String> linkedHashSet = new LinkedHashSet<>();

        linkedHashSet.add("Java");
        linkedHashSet.add("Python");
        linkedHashSet.add("C++");
        linkedHashSet.add("Java"); // duplicate

        System.out.println(linkedHashSet);
    }
}

// Sample Output : 
    /*
        === HashSet Example ===
        [Java, C++, Python]     // Order not guaranteed (may vary between runs)

        ------------------------------

        === LinkedHashSet Example ===
        [Java, Python, C++]     // Order not guaranteed (may vary between runs)
    */