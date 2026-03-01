# Multithreading Foundations (Core Java)  
  
---  
  
## What is a Thread?  
  
A thread is an independent execution unit within a process.  
  
Threads within the same process share heap memory.  
  
Each thread has its own stack.  
  
Threads execute shared code independently.  
  
Code is passive.  
Thread is active execution context.  
  
---  
  
## Process vs Thread  
  
### Process:  
  
- Running instance of a program.  
  
- Has its own independent memory space.  
  
### Thread:  
  
- Lightweight execution path inside a process.  
  
- Shares heap with other threads of same process.  
  
- Has its own stack memory.  
  
---  
  
## Memory Structure in Multithreading  
  
### Inside a process:  
  
- Heap → Shared (objects, instance variables)  
  
- Method Area → Shared (class metadata, static variables)  
  
- Stack → One per thread (local variables, method calls)  
  
### Rule:  
 
Local variable → One copy per thread  

- Local primitive variable → Stored directly in stack
  
- Local object reference → Reference stored in stack,  
    object itself stored in heap (shared)  
  
- Instance variable → One copy per object  
  
- Static variable → One copy per class  

--- 
  
## Thread Lifecycle  
  
### States:  
  
- NEW → Thread object created  
  
- RUNNABLE → start() called, eligible for CPU  
  
- RUNNING → Actively executing  
  
- WAITING / BLOCKED → Temporarily paused  
  
- TERMINATED → run() completed  
  
#### Important:  
  
- Calling run() directly does NOT create new thread.  
- Calling start() creates new execution path.  
  
---  
  
## Race Condition  
  
A race condition occurs when:  
  
Multiple threads access shared mutable data simultaneously,  
and at least one thread modifies it,  
leading to unpredictable results.  

### Example:  
  
#### count++    
  
Not atomic.  
  
##### Internally:  
  
- Read  
  
- Modify  
  
- Write  
  
Two threads can overwrite each other’s updates.  
  
##### Result: Lost updates.  
  
---  
  
### Atomic Operation
  
Atomic means:  
  
An operation that happens completely in one indivisible step.  
  
No other thread can interrupt it midway.  
  
count++ is NOT atomic.  
  
---  
  

## synchronized Keyword  
  
### synchronized ensures:  
  
- Only one thread can enter the method/block at a time.  
  
- Creates a lock on the object.  
  
- Makes critical section atomic.  
  
- Fixes race condition.  
  
---  
  
## Thread Pool Concept (High-Level)  
  
Thread pool:  
  
- Manages worker threads.  
  
- Reuses threads instead of creating new ones.  
  
- Uses task queue.  
  
- Improves performance and scalability.  
  
- Used in Spring Boot to handle HTTP requests.  
  
---  
  
  
