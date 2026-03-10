package collections;

import java.util.TreeSet;

class Employee implements Comparable<Employee> {

    String name;
    int salary;

    Employee(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    @Override
    public int compareTo(Employee other) {
        return this.salary - other.salary;
    }

    @Override
    public String toString() {
        return name + " : " + salary;
    }
}

public class ComparableDemo {

    public static void main(String[] args) {

        TreeSet<Employee> employees = new TreeSet<>();

        employees.add(new Employee("Alice", 50000));
        employees.add(new Employee("Bob", 70000));
        employees.add(new Employee("Charlie", 60000));

        System.out.println(employees);
    }
}

/*
Sample Output

[Alice : 50000, Charlie : 60000, Bob : 70000]

Employees are automatically sorted by salary.
*/