CREATE TABLE employee
(
    employee_id   integer     NOT NULL AUTO_INCREMENT,
    employee_name varchar(64) NOT NULL UNIQUE,
    role          varchar(32) DEFAULT NULL,
    PRIMARY KEY (employee_id)
);