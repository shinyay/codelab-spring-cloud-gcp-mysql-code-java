package com.google.codelab.repository;

import com.google.codelab.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmpId(Long employee_id);
    List<Employee> findByDepId(Long department_id);
}
