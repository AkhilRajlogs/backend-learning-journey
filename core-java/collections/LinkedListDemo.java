package collections;

import java.util.LinkedList;
import java.util.List;

public class LinkedListDemo {

    public static void main(String[] args) {

        List<Integer> list = new LinkedList<>();
        
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);
        
        printList(list);

        list.remove(2);
        printList(list);

        list.remove(0);
        printList(list);
    }

    private static void printList(List<Integer> list) {
        for (Integer num : list) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

}
