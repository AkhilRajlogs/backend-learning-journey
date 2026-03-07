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
  
Programming to an interface means referring to objects by their interface type
rather than their concrete implementation.  
  
Example:  
List<Integer> list = new ArrayList<>();  
  
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
- Backed by a hash table (array of buckets).  
  
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
  