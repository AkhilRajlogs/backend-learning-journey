package collections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListPolymorphismDemo {


    public static void processList(List<Integer> list){
        // This method works with any List implementation.
        // Demonstrates programming to interface (List)
        // and not to a specific implementation like ArrayList or LinkedList.

        list.add(50);
        list.add(0, 100);
        list.remove(1);

    }

    private static void printList(List<Integer> list) {
        for (Integer num : list) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
    // Same method behaves correctly for different List implementations.
    // This is runtime polymorphism.


        List<Integer> list = new ArrayList<>();
        list.add(15);
        list.add(25);
        list.add(30);
        list.add(35);

        System.out.println("ArrayList");        
        showPolymorphism(list);

        list = new LinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);


        System.out.println("LinkedList");        
        showPolymorphism(list);
    }

    private static void showPolymorphism(List<Integer> list){
        System.out.println("List before operations: ");

        printList(list);

        processList(list);

        System.out.println("List after operations: ");

        printList(list);
    }

}
