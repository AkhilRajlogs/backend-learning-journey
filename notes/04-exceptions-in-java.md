# Exceptions in Java  
  
An error in a Java program can be broadly categorized as either an Error or an Exception.  
  
## Errors  
  
Errors represent serious system-level problems that occur at runtime and are generally not meant to be handled by application code.  
  
Examples:  
- OutOfMemoryError  
- StackOverflowError  
  
Errors are usually caused by the JVM or hardware failure.  
  
---  
  
## Exceptions  
  
Exceptions occur when the program is syntactically correct, but an abnormal condition disrupts the normal flow of execution.  
  
Java provides many built-in exception classes to handle such situations.  
  
If an exception is not handled, the program will terminate abnormally.  
  
---  
  
## Types of Exceptions  
  
### 1. Checked Exceptions  
- Checked at compile-time.  
- Must be either handled using try-catch or declared using `throws`.  
- Examples:  
  - IOException  
  - SQLException  
  - ClassNotFoundException  
  
### 2. Unchecked Exceptions (Runtime Exceptions)  
- Occur at runtime.  
- Not checked at compile-time.  
- Usually caused by logical programming errors.  
- Examples:  
  - ArithmeticException  
  - NullPointerException  
  - ArrayIndexOutOfBoundsException  
  
---  
  
## Handling Exceptions  
  
In Java, exceptions are handled using:  
  
### try  
Contains code that might throw an exception.  
  
### catch  
Handles the exception thrown inside the try block.  
Multiple catch blocks can be used.  
  
### finally  
Executes regardless of whether an exception occurs.  
Typically used to release resources (files, database connections, etc.)  
  
---    

## throw vs throws  
  
- `throw` is used to explicitly throw an exception.  
- `throws` is used in method signature to declare that a method may pass an exception to the calling method.  
  
---  
  
## Exception Hierarchy and Important Methods  
  
All exceptions in Java extend from the `Throwable` class.  
  
Hierarchy (simplified):  
  
Object  
└── Throwable  
  ├── Error  
  └── Exception  
    ├── RuntimeException  
    └── Other Checked Exceptions  
  
Important methods available in exception objects:  
  
- `getMessage()` – returns the exception message.  
- `toString()` – returns class name + message.  
- `printStackTrace()` – prints detailed stack trace.  
- `getCause()` – returns the cause of exception.  
  
An exception object is created using `new`, but execution is interrupted only when `throw` is used.  
  