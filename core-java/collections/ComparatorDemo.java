package collections;

import java.util.Comparator;
import java.util.TreeSet;

class Student {

    String name;
    int marks;

    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }

    @Override
    public String toString() {
        return name + " : " + marks;
    }
}

public class ComparatorDemo {

    public static void main(String[] args) {

        Comparator<Student> marksComparator = new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s1.marks - s2.marks;
            }
        };

        TreeSet<Student> students = new TreeSet<>(marksComparator);

        students.add(new Student("Alice", 85));
        students.add(new Student("Bob", 72));
        students.add(new Student("Charlie", 90));

        System.out.println(students);
    }
}

/*
Sample Output

[Bob : 72, Alice : 85, Charlie : 90]

Students sorted by marks using Comparator.
*/