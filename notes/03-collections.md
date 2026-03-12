# Java Collections Framework  
  
## Overview  
  
The Java Collections Framework is a unified architecture for storing and manipulating groups of objects.  
  
It provides ready-to-use data structures and algorithms that simplify data management in Java applications.  
  
Most collection classes and interfaces are part of the java.util package.  
  
---  
    
## Why Collections?  
  
Arrays have a fixed size.  
  
Collections provide dynamic resizing and common data structures.  
   
---  
  
## Components of the Collections Framework  
  
The framework mainly consists of:  
  
### 1. Interfaces  
  
These define the core data structure types.  
  
Examples:  
  
- List  
- Set  
- Queue  
- Deque  
- Map (part of the framework but does not extend the Collection interface)  
  
### 2. Implementations (Classes)  
  
Concrete classes that implement these interfaces.  
  
Examples:  
  
- ArrayList  
- Vector  
- LinkedList  
- PriorityQueue  
- HashSet  
- LinkedHashSet  
- TreeSet  
- HashMap  
- LinkedHashMap  
- TreeMap  
  
### 3. Algorithms  
  
The **Collections utility class** provides algorithms for common operations such as:  
  
- Sorting  
- Searching  
- Reversing  
- Shuffling  
  
Examples:  
  
    Collections.sort()  
    Collections.binarySearch()  
  
---  
   
## Advantages of Using Collections  
  
- Reduces programming effort  
- Improves program performance and reliability  
- Provides reusable data structures  
- Includes many built-in methods for data manipulation  
  
---  
  
## Common Methods in Collection Interface  
  
Some frequently used methods include:  
  
- add()  
- remove()  
- size()  
- clear()  
- contains()  
- isEmpty()  
  
---  
  
## Iterable Relationship  
  
The **Collection interface extends the Iterable interface**.  
  
This allows collections to be used with the **for-each loop**:  
  
    for (String item : collection) {  
        System.out.println(item);  
    }  
  
---  
  
## Collection Hierarchy  
  
Collection interface:  
- List  
- Set  
- Queue  
  
Map is part of the Java Collections Framework but does not extend the Collection interface.  
  
---  
  
## List  
  
Characteristics:  
- Ordered  
- Allows duplicates  
- Index-based  
  
---  
  
## ArrayList  
  
Internal working:  
Backed by a dynamic array.  
Resizes when capacity is exceeded.  

Time Complexity:  
- get(): O(1)  
- add(): O(1) amortized  
- remove(index): O(n) due to element shifting  
  
---
  
## LinkedList  
  
Internal Structure:  
Doubly linked list (Node with next and prev references)  
  
Performance Comparison:  
- remove(0): LinkedList faster  
- get(index): ArrayList faster  
- append: ArrayList usually preferred  
  
Time Complexity:  
- get(index): O(n)  
- add(0): O(1)  
- remove(0): O(1)  
- add(end): O(1)  
    
---
  
## Programming to Interface  
  
Programming to an interface means referring to objects by their interface type rather than their concrete implementation.  
  
Example:  
  
```  
List<Integer> list = new ArrayList<>();  
```    
  
Benefits:  
- Improves flexibility  
- Makes switching implementations easier  
- Supports Dependency Inversion Principle    
  
---  
  
## HashMap  
  
Characteristics:  
- Stores key-value pairs  
- Keys are unique  
- Not ordered  
- Allows one null key (Java default)  
- Backed by a hash table (an array of buckets).  
  
Time Complexity:  
- put(): O(1) average  
- get(): O(1) average  
- Worst case: O(n)  
  
---
  
## Set  
  
A Set represents a collection that **does not allow duplicate elements**.  
  
Characteristics:  
  
- Stores unique elements  
- No index-based access  
- Different implementations provide different ordering guarantees  
  
Common implementations:  
  
- HashSet  
- LinkedHashSet  
- TreeSet  
  
---  
  
## HashSet  
  
HashSet is the most commonly used Set implementation.  
  
Characteristics:  
  
- Stores only unique elements  
- Does not maintain insertion order  
- Allows one null element  
- Backed internally by a HashMap  
  
### Time Complexity:  
  
- add(): O(1) average  
- remove(): O(1) average  
- contains(): O(1) average  
  
Internally, HashSet stores elements as **keys in a HashMap** with a dummy value.  
  
Detailed internal implementation explanation:  
  
See: `03.5-hashset.md`  
  
---    
  
## LinkedHashSet  
  
LinkedHashSet is a Set implementation that **maintains insertion order**.  
  
Characteristics:  
  
- Maintains insertion order of elements  
- Internally backed by **LinkedHashMap**  
- Slightly slower than HashSet due to maintaining a linked structure  
- Useful when removing duplicates while preserving the original order  
  
### Time Complexity:  
  
- add(): O(1) average    
- remove(): O(1) average  
- contains(): O(1) average  
  
---  
  
## TreeSet  
  
TreeSet is a Set implementation that **stores elements in sorted order**.  
  
Unlike HashSet and LinkedHashSet, TreeSet maintains elements according to their **natural ordering** or a **custom comparator**.  
  
Characteristics:  
  
- Stores unique elements  
- Maintains sorted order  
- Internally backed by a **Red-Black Tree**  
- Does not allow null elements because comparison is required  
- Slower than hash-based sets due to sorting operations  
  
### Time Complexity:  
  
- add(): O(log n)  
- remove(): O(log n)  
- contains(): O(log n)  
  
Detailed internal implementation explanation:  
  
See: `03.7-treeset.md`  
  
---  
  
## Queue  
  
A **Queue** represents a collection designed for **holding elements prior to processing**.  
  
Most queue implementations follow the **FIFO (First-In-First-Out)** principle.  
  
### Characteristics  
  
- Elements are processed in insertion order   
- Typically used for task scheduling and processing pipelines   
- Allows duplicate elements    
- Does not support index-based access  
  
### Common Implementations  
  
- LinkedList  
- PriorityQueue  
- ArrayDeque  
  
Detailed internal explanation:  
  
See: `03.10-queue.md`   
  
---  
   
Choosing the correct collection depends on ordering requirements,   
performance needs, and allowed operations.  
  
See: `03.14-choosing-the-right-collection.md`  
  
---  
   
## Sorting in Collections  
  
Java provides two mechanisms for sorting objects.  

### Comparable  
  
Comparable defines the **natural ordering of objects**.  
  
Classes implement Comparable and override:  
  
    compareTo()  
  
Example use cases:  
  
- TreeSet  
- TreeMap  
- Collections.sort()  
  
See:  
  
`03.8-comparable.md`  
  
---  
  
### Comparator  
  
Comparator allows **custom sorting logic outside the class**.  
  
It supports **multiple sorting strategies**.  
  
Used with:  
  
- TreeSet  
- Collections.sort()  
- Streams  
  
See:  
  
`03.9-comparator.md`    