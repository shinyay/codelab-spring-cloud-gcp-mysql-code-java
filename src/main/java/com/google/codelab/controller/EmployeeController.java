package com.google.codelab.controller;

import com.google.codelab.entity.Employee;
import com.google.codelab.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/employees/{employee_id}")
    public ResponseEntity<Optional<Employee>> findOneEmployeeByEmployeeId(@PathVariable("employee_id") Long id) {
        var employee = service.findEmployeeByEmployeeId(id);
        if(employee.isPresent()) {
            return new ResponseEntity(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/employees/department/{department_id}")
    public ResponseEntity<List<Employee>> findEmployeesByDepartmentId(@PathVariable("department_id") Long id) {
        var employee = service.findEmployeeByDepartmentId(id);
        if(employee.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(employee, HttpStatus.OK);
        }
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> addNewEmployee(@RequestBody Employee employee) {
        return new ResponseEntity(service.registerEmployee(employee), HttpStatus.OK);
    }

    @PutMapping("/employees")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        return new ResponseEntity(service.updateEmployee(employee), HttpStatus.OK);
    }

    @DeleteMapping("/employees")
    public ResponseEntity<Void> deleteAllEmployees() {
        if(service.findAllEmployees().isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            service.deleteAllEmployees();
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
