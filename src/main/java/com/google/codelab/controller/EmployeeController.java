package com.google.codelab.controller;

import com.google.codelab.entity.Employee;
import com.google.codelab.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> findAllEmployees() {
        var employees = service.findAllEmployees();
        if(employees.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(employees, HttpStatus.OK);
        }
    }

    @GetMapping("/employee/department/{department_id}")
    public ResponseEntity<List<Employee>> findOneEmployeeById(@PathVariable("department_id") Long id) {
        var employee = service.findEmployeeByDepartmentId(id);
        if(employee.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(employee, HttpStatus.OK);
        }
    }
    
    @GetMapping("/e")
}
