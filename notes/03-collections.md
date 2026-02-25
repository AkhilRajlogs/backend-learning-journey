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
  