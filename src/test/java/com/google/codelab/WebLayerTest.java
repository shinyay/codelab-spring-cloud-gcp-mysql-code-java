package com.google.codelab;

import com.google.codelab.controller.EmployeeController;
import com.google.codelab.entity.Employee;
import com.google.codelab.service.EmployeeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService service;

    private List<Employee> employeeList;

    @BeforeAll
    public void init() {
        Employee sample = new Employee();
        sample.setEmpId(999L);
        sample.setDepartment_id(10L);
        sample.setName("JoneDoe");
        sample.setRole("User");
        employeeList = Arrays.asList(sample);
    }

    @Test
    public void Given_EmployeeController_When_findAllEmployees_Then_return_200() throws Exception {


        Mockito.when(service.findAllEmployees()).thenReturn(employeeList);

        mockMvc.perform(get("/api/v1/employees"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
