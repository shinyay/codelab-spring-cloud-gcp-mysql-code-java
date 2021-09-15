ALTER TABLE employee
    ADD department_id integer NOT NULL
    AFTER employee_id;
ALTER TABLE employee
    ADD FOREIGN KEY (department_id) REFERENCES department (department_id);