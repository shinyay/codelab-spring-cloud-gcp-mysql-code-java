ALTER TABLE employee
    ADD department_id integer NOT NULL;
ALTER TABLE employee
    ADD FOREIGN KEY (department_id) REFERENCES department (department_id);