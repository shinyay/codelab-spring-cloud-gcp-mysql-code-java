package com.google.codelab.entity;

import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employee_id;

    @Column(name = "employee_name", nullable = false, length = 64)
    private String name;

    private String role;

    private Long department_id;

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

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }

    public Long getEmployee_id() {
        return employee_id;
    }

    public Long getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Long department_id) {
        this.department_id = department_id;
    }
}
