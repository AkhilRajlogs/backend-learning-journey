# Java Collections Framework  
  
## Why Collections?  
  
Arrays are fixed size.  
Collections provide dynamic resizing and common data structures.  
  
---  
  
## Collection Hierarchy  
  
Collection interface:  
- List  
- Set  
- Queue  
  
Map is a separate hierarchy.  
  
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
  
Programming to an interface means referring to objects by their interface type
rather than their concrete implementation.  
  
Example:  
List<Integer> list = new ArrayList<>();  
  
Benefits:  
- Improves flexibility  
- Makes switching implementations easier  
- Supports Dependency Inversion Principle  
  