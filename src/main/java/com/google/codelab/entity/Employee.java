package com.google.codelab.entity;

import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employee_id;

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }

    public Long getEmployee_id() {
        return employee_id;
    }
}
