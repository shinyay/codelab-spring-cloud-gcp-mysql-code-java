package com.google.codelab;

import com.google.codelab.entity.Employee;
import com.google.codelab.repository.EmployeeRepository;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    @Container
    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:5.7.34")
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
    public void findAllEmployees() {
        List<Employee> result = repository.findAll();
        assertThat(result.stream().count()).isEqualTo(1);
    }

    @Test
    public void findEmployees() {
        Optional<Employee> result = repository.findById(1L);
        assertAll(
                () -> assertThat(result.get().getName()).isEqualTo("shinyay"),
                () -> assertThat(result.get().getRole()).isEqualTo("Tester")
        );
    }

    @Test
    public void findEmployeesByDepartmentId() {
        List<Employee> result = repository.findByDepId(100L);
        assertAll(
                () -> assertThat(result.get(0).getName()).isEqualTo("shinyay"),
                () -> assertThat(result.get(0).getRole()).isEqualTo("Tester")
        );
    }
}
