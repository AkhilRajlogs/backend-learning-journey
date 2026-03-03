# ExecutorService Basics  
  
## 1. Why Thread Pools Exist  
  
Thread creation is expensive because it involves:  
- Stack memory allocation  
- OS-level scheduling  
- Context switching overhead  
  
Creating new threads repeatedly can lead to:  
- Performance overhead  
- Uncontrolled concurrency  
- Thread explosion  
  
Thread pools solve this by:  
- Reusing a fixed number of threads  
- Controlling concurrency  
- Managing task queues efficiently  
  
  
## 2. ExecutorService Lifecycle  
  
1. Creation → Thread pool is initialized.  
2. Running → Tasks are submitted and executed.  
3. Shutdown → No new tasks accepted.  
4. Terminated → All tasks finished and threads closed.  
  
Important:  
If shutdown() is not called, non-daemon worker threads remain alive and JVM will not exit.  
  
  
## 3. execute() vs submit()  
  
execute():  
- Accepts Runnable  
- Returns void  
- Exceptions are thrown to the thread's UncaughtExceptionHandler  
  
submit():  
- Accepts Runnable or Callable  
- Returns Future  
- Exceptions are captured inside Future  
- Allows tracking task completion  
- Future.get() rethrows captured exceptions wrapped in ExecutionException  
  
  
## 4. shutdown() vs shutdownNow()  
  
shutdown():  
- Stops accepting new tasks  
- Allows existing tasks to finish  
  
shutdownNow():  
- Attempts to stop running tasks  
- Interrupts worker threads  
- Returns list of pending tasks  
  
Note: shutdownNow() sends interrupt signal but does not guarantee task termination.  
  
  
## 5. JVM Shutdown & Non-Daemon Threads  
  
JVM exits only when all non-daemon threads terminate.  
  
Thread pool threads are non-daemon by default.  
Therefore, without shutdown(), the application may hang.  
   
  
## 6. Lambda Variable Capture Rule  
  
Variables used inside a lambda must be final or effectively final.  
  
Reason:  
Lambdas capture variables from surrounding scope.  
If those variables are mutable, it can cause inconsistent state due to mutation.  
  
  
Example (Will NOT Compile):  
  
for (int i = 0; i < 5; i++) {  
    executor.submit(() -> {  
        System.out.println(i);  
    });  
}  
  
Correct Version:  
  
for (int i = 0; i < 5; i++) {  
    int taskId = i;  
    executor.submit(() -> {  
        System.out.println(taskId);  
    });  
}  
  
  
## 7. Key Interview Takeaways  
  
- Thread pools prevent thread explosion.  
- submit() allows tracking task completion.  
- Non-daemon threads keep JVM alive.  
- shutdown() is mandatory for graceful termination.  
  