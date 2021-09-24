package com.google.codelab;

import com.google.codelab.controller.EmployeeController;
import com.google.codelab.repository.EmployeeRepository;
import com.google.codelab.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TutorialApplicationTests {

    @Autowired
    private EmployeeController controller;

    @Autowired
    private EmployeeService service;

    @Autowired
    private EmployeeRepository repository;

    @Nested
    class ApplicationContextTests {

        @Test
        void controllerContextLoads() {
            Assertions.assertThat(controller).isNotNull();
        }

        @Test
        void serviceContextLoads() {
            Assertions.assertThat(service).isNotNull();
        }

        @Test
        void repositoryContextLoads() {
            Assertions.assertThat(repository).isNotNull();
        }

    }
}
