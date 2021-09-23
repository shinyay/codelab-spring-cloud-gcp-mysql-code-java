SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS department;
CREATE TABLE department
(
    department_id   integer     NOT NULL AUTO_INCREMENT,
    department_name varchar(32) NOT NULL UNIQUE,
    PRIMARY KEY (department_id)
);

DROP TABLE IF EXISTS employee;
CREATE TABLE employee
(
    employee_id   integer     NOT NULL AUTO_INCREMENT,
    department_id integer NOT NULL,
    employee_name varchar(64) NOT NULL UNIQUE,
    role          varchar(32) DEFAULT NULL,
    PRIMARY KEY (employee_id)
);

ALTER TABLE employee
    ADD FOREIGN KEY (department_id) REFERENCES department (department_id);
SET FOREIGN_KEY_CHECKS = 1;
