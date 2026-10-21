# JUnit Testing

## Introduction

JUnit is a popular, open-source testing framework for Java. It helps developers write and execute automated tests.

### Purpose

- **Unit Testing:** Tests individual components or units of code in isolation.

### Features

- **Annotations:** Annotations such as `@Test`, `@Before`, and `@After` define test methods and setup/teardown actions.
- **Assertions:** Methods such as `assertEquals()`, `assertTrue()`, and `assertNotNull()` verify expected outcomes.
- **Test Runners:** Control how tests are executed. Examples include `BlockJUnit4ClassRunner` and parameterized test runners.
- **Parameterized Tests:** Allow the same test logic to run with different input values.

### Test Lifecycle

- **Setup:** `@Before` runs before each test method.
- **Teardown:** `@After` runs after each test method.

JUnit helps organize and automate Java tests, supports reliable software development, and can be used as part of Test-Driven Development (TDD).

---

---

## JUnit 5 Annotations

JUnit 5 provides annotations to identify test methods, configure test execution, and manage setup and cleanup tasks.

### Core Annotations

| Annotation | Purpose |
|---|---|
| `@Test` | Marks a method as a test method. |
| `@DisplayName` | Provides a custom name for a test class or method in test reports. |
| `@Order` | Specifies the execution order of test methods when ordering is configured. |
| `@BeforeEach` | Runs before each test method. |
| `@AfterEach` | Runs after each test method. |
| `@BeforeAll` | Runs once before all test methods in the class. |
| `@AfterAll` | Runs once after all test methods in the class. |

### Example

```java
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SampleTest {

    @BeforeEach
    void setUp() {
        // Runs before each test
    }

    @Test
    @DisplayName("Check sample functionality")
    void testSample() {
        // Test logic
    }

    @AfterEach
    void tearDown() {
        // Runs after each test
    }
}
```

### Quick Recall

- `@Test` → identifies a test method.
- `@DisplayName` → improves readability in test reports.
- `@BeforeEach` / `@AfterEach` → run around every test method.
- `@BeforeAll` / `@AfterAll` → run once before or after all tests in the class.
- `@Order` → controls test execution order when used with an appropriate test method orderer.