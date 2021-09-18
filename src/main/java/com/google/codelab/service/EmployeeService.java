package com.google.codelab.service;

import com.google.codelab.entity.Employee;
import com.google.codelab.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> findAllEmployees() {
        return repository.findAll();
    }

    public Optional<Employee> findEmployeeByEmployeeId(Long employeeId) {
        return repository.findById(employeeId);
    }

    public List<Employee> findEmployeeByDepartmentId(Long departmentId) {
        return repository.findByDepId(departmentId);
    }

    public Employee registerEmployee(Employee employee) {
        return repository.save(employee);
    }

    public Employee updateEmployee(Employee employee) {
        Optional<Employee> registeredEmployee = repository.findById(employee.getEmpId());
        if(registeredEmployee.isPresent()) {
            var registeredEmployeeEntity = registeredEmployee.get();
            registeredEmployeeEntity.setEmpId(employee.getEmpId());
            registeredEmployeeEntity.setName(employee.getName());
            registeredEmployeeEntity.setRole(employee.getRole());
            registeredEmployeeEntity.setDepartment_id(employee.getDepartment_id());
            return repository.save(registeredEmployeeEntity);
        } else {
            return repository.save(employee);
        }
    }

    public void deleteAllEmployees() {
        repository.deleteAll();
    }
    public void deleteEmployeeByEmployeeId(Long employeeId) {
        repository.deleteById(employeeId);
    }

}
