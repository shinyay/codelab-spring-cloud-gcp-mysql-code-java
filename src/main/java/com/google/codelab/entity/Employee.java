package com.google.codelab.entity;

import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {
    public Employee() {}

    public Employee(Long empId, String name, String role, Long depId) {
        this.empId = empId;
        this.name = name;
        this.role = role;
        this.depId = depId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long empId;

    @Column(name = "employee_name", nullable = false, length = 64)
    private String name;

    private String role;

    @Column(name = "department_id")
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
