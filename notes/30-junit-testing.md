# JUnit Testing

## Introduction

JUnit is a popular, open-source testing framework for Java. It helps developers write and execute automated tests.

### Purpose

- **Unit Testing:** Tests individual components or units of code in isolation.

### Features

- **Annotations:** Identify test methods and configure setup/teardown.
  JUnit 4 uses `@Before` and `@After`; JUnit 5 uses `@BeforeEach` and `@AfterEach`.
- **Assertions:** Verify expected outcomes using methods such as
  `assertEquals()`, `assertTrue()`, and `assertNotNull()`.
- **Test Runners:** Control test execution. Examples include
  `BlockJUnit4ClassRunner` and parameterized test runners.
- **Parameterized Tests:** Run the same test logic with different input values.
- **Assertion Libraries:** JUnit assertions and AssertJ provide ways to verify
  expected results.

### Test Lifecycle

- **Setup:** JUnit 4 uses `@Before`; JUnit 5 uses `@BeforeEach`.
- **Teardown:** JUnit 4 uses `@After`; JUnit 5 uses `@AfterEach`.

JUnit supports automated testing and Test-Driven Development (TDD), helping
developers verify expected behaviour and identify failures.

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
| `@ActiveProfiles` | Activates the specified Spring profile for a test, such as `test`. |

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

**Ordered Tests:** To apply `@Order` to test methods, configure the class with
`@TestMethodOrder(MethodOrderer.OrderAnnotation.class)`. Lower order values run
earlier. Tests should generally remain independent of execution order.
  
---

## JUnit 5 Assertions

Assertions are used to verify expected conditions in test methods.

| Assertion | Purpose |
|---|---|
| `assertNotNull()` | Verifies that an object is not `null`. |
| `assertNull()` | Verifies that an object is `null`. |
| `assertTrue()` | Verifies that a condition is `true`. |
| `assertFalse()` | Verifies that a condition is `false`. |
| `assertEquals(expected, actual)` | Verifies that the actual value equals the expected value. |

### Example

```java
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SampleTest {

    @Test
    void testAssertions() {
        String name = "Java";
        String value = null;
        boolean isActive = true;
        boolean isDisabled = false;

        assertNotNull(name);
        assertNull(value);
        assertTrue(isActive);
        assertFalse(isDisabled);
    }
}
```

### Quick Recall

- `assertNotNull()` — checks that a value is not `null`.
- `assertNull()` — checks that a value is `null`.
- `assertTrue()` — checks that a condition is true.
- `assertFalse()` — checks that a condition is false.

---

## AssertJ Assertions

AssertJ provides fluent assertions using `assertThat()`, allowing values to be checked with readable matcher methods.

### Example

```java
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class SampleTest {

    @Test
    void testAssertions() {
        String name = "Java";

        assertThat(name).isNotNull();
        assertThat(name).isEqualTo("Java");
        assertThat(name).startsWith("Ja");
    }
}
```

### Quick Recall

`assertThat(actual)` starts a fluent assertion chain.

| Category | Common methods | Purpose |
|---|---|---|
| Equality | `isEqualTo()`, `isNotEqualTo()` | Check whether values are equal or different. |
| Null | `isNull()`, `isNotNull()` | Check whether a value is null. |
| Boolean | `isTrue()`, `isFalse()` | Check a Boolean condition. |
| String | `contains()`, `startsWith()`, `endsWith()` | Check string content or prefix/suffix. |
| Numeric | `isGreaterThan()`, `isLessThan()` | Compare numeric values. |

JUnit assertions and AssertJ can be used in the same test. Prefer a consistent
style within a project unless a particular assertion makes another library more suitable.

---

## Mockito

Mockito is a Java framework used for mocking and stubbing in unit tests. It allows dependencies to be simulated so that a specific component can be tested in isolation.

### Basic Concepts

| Concept | Purpose |
|---|---|
| **Mocking** | Creates simulated objects that mimic the behaviour of real objects. |
| **Stubbing** | Configures a mock to return specific values when its methods are called. |
| **Verification** | Checks whether a method on a mock was called as expected. |

### Example

```java
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

interface Calculator {
    int add(int a, int b);
    int subtract(int a, int b);
}

class CalculatorService {
    private final Calculator calculator;

    CalculatorService(Calculator calculator) {
        this.calculator = calculator;
    }

    int addTwoNumbers(int a, int b) {
        return calculator.add(a, b);
    }

    int subtractTwoNumbers(int a, int b) {
        return calculator.subtract(a, b);
    }
}

class CalculatorServiceTest {

    @Test
    void testAddTwoNumbers() {
        // Create a mock
        Calculator calculatorMock = mock(Calculator.class);

        // Stub the method's behaviour
        when(calculatorMock.add(2, 3)).thenReturn(5);

        CalculatorService calculatorService =
                new CalculatorService(calculatorMock);

        // Execute the method being tested
        int result = calculatorService.addTwoNumbers(2, 3);

        // Verify the interaction and result
        verify(calculatorMock).add(2, 3);
        assertEquals(5, result);
    }
}
```

### Quick Recall

- `mock()` — creates a mock object.
- `when().thenReturn()` — defines the behaviour of a mock method.
- `verify()` — checks whether a mock method was called.
- Assertions verify whether the test result matches expectations.
   
---

## Controller Testing with @WebMvcTest

`@WebMvcTest` focuses testing on the Spring MVC layer, allowing controllers to be tested without loading the complete application context.

### Key Components

| Component | Purpose |
|---|---|
| `@WebMvcTest` | Loads the MVC components needed to test a controller. |
| `MockMvc` | Simulates HTTP requests and checks responses without starting a real server. |
| `@MockBean` | Provides a mock service dependency for the test. |
| `ObjectMapper` | Converts Java objects into JSON and JSON into Java objects. |

### Example

*The example uses application-specific classes from the Student Management
System; imports and project setup may vary.*

```java
import com.example.StudentManagementSystem.model.Student;
import com.example.StudentManagementSystem.service.StudentsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = studentsController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentsService studentsService;

    @Test
    void shouldAddStudent() throws Exception {
        Student student = new Student(
                1,
                "Rakesh",
                19,
                "JUnit",
                "Aryabhatta Hostels",
                "rakesh@gmail.com",
                "09874562134"
        );

        Mockito.when(studentsService.addStudent(Mockito.any(Student.class)))
            .thenReturn(student);

        String json = objectMapper.writeValueAsString(student);

        mockMvc.perform(post("/students/addStudent")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());
    }
}
```

### Test Flow

1. `@WebMvcTest` configures the controller-focused test context.
2. `@MockBean` supplies a mocked service dependency.
3. Mockito stubbing defines the service's expected behaviour.
4. `ObjectMapper` converts the student object into JSON.
5. `MockMvc` sends a simulated POST request with that JSON.
6. `andExpect()` checks that the response status is HTTP 200 OK.

### Quick Recall

- `@WebMvcTest` — focuses on the MVC layer.
- `MockMvc` — performs simulated HTTP requests.
- `ObjectMapper` — handles JSON conversion.
- `@MockBean` — supplies a mocked dependency.
- `andExpect()` — verifies an expected response condition.   

---

## JPA Repository Testing with Testcontainers

`@DataJpaTest` loads JPA-related components for testing repository functionality. Testcontainers provides a containerized database for integration testing.

### Key Annotations and Components

| Annotation / Component | Purpose |
|---|---|
| `@DataJpaTest` | Configures a test context for JPA-related components. |
| `@Testcontainers` | Enables Testcontainers support for tests. |
| `@AutoConfigureTestDatabase` | Controls automatic test database replacement. |
| `MySQLContainer` | Configures a MySQL container for testing. |
| `@DynamicPropertySource` | Registers dynamic database connection properties. |

### Example

```java
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(
    replace = AutoConfigureTestDatabase.Replace.NONE
)
class StudentsDalTest {

    private static final MySQLContainer MY_SQL_CONTAINER =
        new MySQLContainer("mysql:latest")
            .withDatabaseName("student-test-db")
            .withUsername("testUser")
            .withPassword("password");

    static {
        MY_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void registerDatabaseProperties(
            DynamicPropertyRegistry registry) {
        registry.add(
            "spring.datasource.url",
            MY_SQL_CONTAINER::getJdbcUrl
        );
        registry.add(
            "spring.datasource.username",
            MY_SQL_CONTAINER::getUsername
        );
        registry.add(
            "spring.datasource.password",
            MY_SQL_CONTAINER::getPassword
        );
    }

    @Autowired
    StudentsDal studentsDal;

    @Test
    @Order(1)
    void shouldSaveStudent() {
        Student student = new Student(
            1,
            "Rakesh",
            19,
            "JUnit",
            "Aryabhatta Hostels",
            "rakesh@gmail.com",
            "09874562134"
        );

        Student resultStudent = studentsDal.save(student);

        assertThat(resultStudent)
            .usingRecursiveComparison()
            .ignoringFields("sid")
            .isEqualTo(student);
    }
}
```

*This example starts the container explicitly in a static initializer and
registers its connection properties through `@DynamicPropertySource`.*

### Test Flow

1. Configure the test for JPA and Testcontainers.
2. Start a MySQL container with the required database credentials.
3. Register the container's connection properties with Spring.
4. Inject the repository being tested.
5. Save a student and compare the returned object with the input, ignoring the `sid` field.

### Quick Recall

- `@DataJpaTest` — focuses on JPA components.
- Testcontainers — provides a containerized database.
- `@DynamicPropertySource` — registers dynamic configuration properties.
- `@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)`
  — prevents Spring's test configuration from replacing the configured database.
- AssertJ recursive comparison — compares object fields while allowing specified fields to be ignored.

---

## Integration Testing with @SpringBootTest

`@SpringBootTest` loads the complete Spring application context for integration testing.

`@AutoConfigureMockMvc` configures a `MockMvc` instance so HTTP requests can be simulated without starting a real server.

Use `@SpringBootTest` when the test needs the full application context;
use `@WebMvcTest` when the focus is the MVC layer.

### Key Annotations

| Annotation | Purpose |
|---|---|
| `@SpringBootTest` | Loads the complete Spring application context. |
| `@AutoConfigureMockMvc` | Configures `MockMvc` for simulated HTTP requests. |

### What Integration Tests Can Verify

- Controller handling of HTTP requests.
- Expected response status codes and data.
- Interactions between controllers, services, and repositories.
- Whether different application layers work together correctly.

### Quick Recall

- `@WebMvcTest` — focuses on the MVC layer.
- `@DataJpaTest` — focuses on JPA components.
- `@SpringBootTest` — loads the complete application context.
- `@AutoConfigureMockMvc` — enables simulated HTTP requests in the test.

---

## Security-Aware Controller Testing

When testing a secured controller, `@WithMockUser` supplies a mock authenticated user for the test.

If the usual `MockMvc` setup does not load the required web dependencies, `MockMvcBuilders.webAppContextSetup()` can configure `MockMvc` using the `WebApplicationContext`.

### Example

*Illustrative example based on the course project. Imports, application classes,
and security configuration are omitted for brevity.*

```java
@WebMvcTest(controllers = UserController.class)
class UserControllerTests {

    @MockBean
    private UserService userService;

    @MockBean
    private JwtAuthenticationHelper jwtAuthenticationHelper;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .build();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldTestGetAllUsers() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("user1");

        Mockito.when(userService.getAllUsers())
            .thenReturn(List.of(user));

        mockMvc.perform(get("/user/all")
                .contentType("application/json"))
            .andExpect(status().isOk());
    }
}
```

### Quick Recall

- `@WithMockUser` — supplies a mock authenticated user for a test.
- `roles = "ADMIN"` — configures the mock user's role.
- `WebApplicationContext` — provides the web application context.
- `MockMvcBuilders.webAppContextSetup()` — builds `MockMvc` using that context.
- Mock service dependencies to control their behaviour during the test.