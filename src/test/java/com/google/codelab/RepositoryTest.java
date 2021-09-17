package com.google.codelab;

import com.google.codelab.entity.Employee;
import com.google.codelab.repository.EmployeeRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Before
    public void setUpStream() {
        System.setOut(new PrintStream(outputStream));
    }

    @After
    public void restoreStream() {
        System.setOut(standardOut);
    }


    @Autowired
    private EmployeeRepository repository;

    @Container
    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:5.7.34")
            .withStartupTimeout(Duration.ofSeconds(90)) // Default 60s Timeout
            .withDatabaseName("test-db")
            .withUsername("scott")
            .withPassword("tiger");

    @DynamicPropertySource
    static void mySqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
//        registry.add("spring.jpa.hibernate.ddl-auto", ()->"create-drop");
        registry.add("spring.jpa.hibernate.ddl-auto", ()->"none");
    }

    @Test
    @Order(1)
    public void Given_employee_When_findAll_Then_countInitialValue() {
        // Given
        //  V5__Insert_Employee.sql

        // When
        List<Employee> result = repository.findAll();
        System.out.println("**************************************************");
        System.out.println("[Test Case] findAllEmployees()");
        result.stream()
                .forEach(i -> {
                    System.out.println("[Employee ID]   " + i.getEmpId());
                    System.out.println("[Employee NAME] " + i.getName());
                    System.out.println("[Employee ROLE] " + i.getRole());
                    System.out.println("[Department ID] " + i.getDepartment_id());
                });
        System.out.println("**************************************************");

        // Then
        assertThat(result.stream().count()).isEqualTo(1);
    }

    @Test
    public void Given_employee_When_find_by_id_Then_querySpecificEmployee() {
        // Given
        //  V5__Insert_Employee.sql

        // When
        Optional<Employee> result = repository.findById(1L);

        // Then
        assertAll(
                () -> assertThat(result.orElseThrow().getName()).isEqualTo("shinyay"),
                () -> assertThat(result.orElseThrow().getRole()).isEqualTo("Tester")
        );
    }

    @Test
    public void Given_employee_When_find_by_departmentId_Then_querySpecificEmployee() {
        // Given
        //  V5__Insert_Employee.sql

        // When
        List<Employee> result = repository.findByDepId(100L);

        // Then
        assertAll(
                () -> assertThat(result.get(0).getName()).isEqualTo("shinyay"),
                () -> assertThat(result.get(0).getRole()).isEqualTo("Tester")
        );
    }
}
