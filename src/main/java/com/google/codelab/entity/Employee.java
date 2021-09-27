package com.google.codelab.entity;

import javax.persistence.*;

public class Employee {
    public Employee() {}

    public Employee(Long empId, String name, String role, Long depId) {
        this.empId = empId;
        this.name = name;
        this.role = role;
        this.depId = depId;
    }
    
    private Long empId;

    private String name;

    private String role;

    private Long depId;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmployeeId(Long employee_id) {
        this.empId = employee_id;
    }

    public Long getEmployeeId() {
        return empId;
    }

    public Long getDepartmentId() {
        return depId;
    }

    public void setDepartmentId(Long department_id) {
        this.depId = department_id;
    }
}