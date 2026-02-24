# OOPS Principles  
  
## 1. Encapsulation  
  
Definition:    
Encapsulation means restricting direct access to object data and exposing it through controlled methods.  
  
Why it matters:  
Prevents accidental modification and protects internal state.  
  
Example:  
Private fields with public getters/setters.  
  
  
## 2. Abstraction  
  
Definition:  
Abstraction hides internal implementation details and exposes only essential behavior.  
  
Why it matters:  
Reduces complexity and improves flexibility.  
  
Example:  
Using interfaces to define behavior without exposing implementation.  
  
  
## 3. Inheritance  
  
Definition:  
Inheritance allows one class to acquire properties and behavior of another class.  
  
Why it matters:  
Promotes code reuse and logical hierarchy.  
  
Example:  
class WalletPayment extends BasePayment  
  
  
## 4. Polymorphism  
  
Definition:  
Polymorphism allows objects to be treated as their parent type while behaving differently.  
  
Types:  
- Compile-time (method overloading)  
- Runtime (method overriding)  
  
Why it matters:  
Enables flexible and extensible systems.  

Example:  
CreditCardPayment, UpiPayment, and WalletPayment override the processPayment() method defined in PaymentMethod.  
  
  
## 5. SOLID Principles  
SOLID principles help design maintainable, extensible, and scalable object-oriented systems.  
  
### Single Responsibility Principle (SRP)  
A class should have only one reason to change.  
  
### Open/Closed Principle (OCP)  
Software entities should be open for extension but closed for modification.  
Applied in project:  
New payment types (e.g., NetBankingPayment) can be added without modifying existing payment handling logic.  
  
### Liskov Substitution Principle (LSP)  
Subtypes must be substitutable for their base types.  
  
### Interface Segregation Principle (ISP)  
Clients should not depend on methods they do not use.  
  
### Dependency Inversion Principle (DIP)  
High-level modules should not depend on low-level modules.  
Both should depend on abstractions.  
  
Example:  
PaymentService depends on the PaymentMethod interface rather than concrete payment classes.  
  