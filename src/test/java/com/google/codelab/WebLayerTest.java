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
import java.util.Optional;

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

    private Employee employeeData;
    private List<Employee> employeeList;

    @BeforeAll
    public void init() {
        employeeData = new Employee();
        employeeData.setEmpId(999L);
        employeeData.setDepartment_id(10L);
        employeeData.setName("JoneDoe");
        employeeData.setRole("User");
        employeeList = Arrays.asList(employeeData);
    }

    @Test
    public void Given_EmployeeController_When_findAllEmployees_Then_return_200() throws Exception {

        Mockito.when(service.findAllEmployees()).thenReturn(employeeList);

        mockMvc.perform(get("/api/v1/employees"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void Given_EmployeeController_When_findEmployeeByEmployeeId_Then_return_200() throws Exception {

        Mockito.when(service.findEmployeeByEmployeeId(999L)).thenReturn(Optional.of(employeeData));

        mockMvc.perform(get("/api/v1/employees/999"))
                .andDo(print())
                .andExpect(status().isOk());
    }



}
