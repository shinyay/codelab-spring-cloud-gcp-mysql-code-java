CREATE TABLE department
(
    department_id   integer     NOT NULL AUTO_INCREMENT,
    department_name varchar(32) NOT NULL UNIQUE,
    PRIMARY KEY (department_id)
);