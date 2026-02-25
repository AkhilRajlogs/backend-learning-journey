package collections;

import java.util.ArrayList;

public class ArrayListDemo {

    public static void main(String[] args) {
            
        ArrayList<Integer> list = new ArrayList<>(5);

        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);

        printArrayList(list);

        list.remove(2);

        printArrayList(list);

        list.remove(0);

        printArrayList(list);


    }

    private static void printArrayList(ArrayList<Integer> list) {
        for (int i = 0; i < list.size(); i ++) {
            System.out.print( list.get(i) + " " );
        }

        System.out.println();
    }
}
