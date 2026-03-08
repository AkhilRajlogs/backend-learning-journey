package collections;

import java.util.HashSet;
import java.util.Objects;

class Student {
    int id;
    String name;

    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Version 2

   /*
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Student)) return false;
            Student other = (Student) obj;
            return id == other.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    */

}

public class HashSetDemo {

    public static void main(String[] args) {

        System.out.println("=== HashSet with Strings ===");

        HashSet<String> set = new HashSet<>();

        set.add("Java");
        set.add("Python");
        set.add("Java"); // duplicate

        System.out.println(set);

        System.out.println("\n=== HashSet with Custom Objects (Without equals/hashCode) ===");

        HashSet<Student> students = new HashSet<>();

        students.add(new Student(1, "Alice"));
        students.add(new Student(1, "Alice")); // logically duplicate

        System.out.println("Size: " + students.size());

        for (Student s : students) {
            System.out.println(s.id + " " + s.name);
        }

      // Even though there are duplicates, without overriding the output is: 

      /*
        === HashSet with Strings ===
        [Java, Python]

        === HashSet with Custom Objects (Without equals/hashCode) ===
        Size: 2
        1 Alice
        1 Alice
      */



       // Now, we uncomment override in above Version 2 and run, then output will be :

       /*  
        === HashSet with Strings ===
        [Java, Python]

        === HashSet with Custom Objects (Without equals/hashCode) ===
        Size: 1
        1 Alice
      */



    }
}