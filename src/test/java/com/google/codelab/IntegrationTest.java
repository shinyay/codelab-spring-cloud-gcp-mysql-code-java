package com.google.codelab;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.codelab.entity.Employee;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
    }

    @Test
    @Order(1)
    public void Given_Integration_When_findAllEmployees_Then_return_properties() throws Exception {

        mockMvc.perform(get("/api/v1/employees"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].employeeId").exists())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].role").exists())
                .andExpect(jsonPath("$[0].departmentId").exists());
    }

    @Test
    @Order(2)
    public void Given_Integration_When_findAllEmployees_Then_return_employee_name() throws Exception {

        mockMvc.perform(get("/api/v1/employees"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("shinyay"));
    }


    @Test
    @Order(3)
    public void Given_Integration_When_findOneEmployeeByEmployeeId_Then_return_employee() throws Exception {

        mockMvc.perform(get("/api/v1/employees/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("shinyay"))
                .andExpect(jsonPath("$.role").value("Tester"))
                .andExpect(jsonPath("$.departmentId").value(100));
    }

    @Test
    @Order(4)
    public void Given_Integration_When_findEmployeesByDepartmentId_Then_return_employee() throws Exception {

        mockMvc.perform(get("/api/v1/employees/department/100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("shinyay"))
                .andExpect(jsonPath("$[0].role").value("Tester"))
                .andExpect(jsonPath("$[0].employeeId").value(1));
    }

    @Test
    @Order(5)
    public void Given_Integration_When_addNewEmployee_Then_return_employee() throws Exception {

        var employee = new Employee();
        employee.setName("yanashin");
        employee.setRole("SRE");
        employee.setDepartmentId(40L);
        var json = objectMapper.writeValueAsString(employee);

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
        mockMvc.perform(get("/api/v1/employees/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("yanashin"))
                .andExpect(jsonPath("$.role").value("SRE"))
                .andExpect(jsonPath("$.departmentId").value(40));
    }

    @Test
    @Order(6)
    public void Given_Integration_When_updateEmployee_Then_return_employee() throws Exception {

        var employee = new Employee(1L, "shinyay", "Youtuber", 100L);
        var json = objectMapper.writeValueAsString(employee);

        mockMvc.perform(put("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/employees/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("Youtuber"))
                .andExpect(jsonPath("$.departmentId").value(100));
    }

    @Test
    @Order(7)
    public void Given_Integration_When_updateEmployee_Then_return_new_employee() throws Exception {

        var employee = new Employee(3L, "shinya", "Youtuber", 100L);
        var json = objectMapper.writeValueAsString(employee);

        mockMvc.perform(put("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/employees/3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value("3"))
                .andExpect(jsonPath("$.name").value("shinya"))
                .andExpect(jsonPath("$.role").value("Youtuber"))
                .andExpect(jsonPath("$.departmentId").value(100));
    }

    @Test
    @Order(8)
    public void Given_Integration_When_deleteEmployeeByEmployeeId_Then_return_employee_name() throws Exception {

        mockMvc.perform(delete("/api/v1/employees/1"))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/employees/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(9)
    public void Given_Integration_When_deleteAllEmployees_Then_return_204() throws Exception {

        mockMvc.perform(delete("/api/v1/employees"))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/employees"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
