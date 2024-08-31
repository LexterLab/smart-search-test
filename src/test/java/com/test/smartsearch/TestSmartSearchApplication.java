package com.test.smartsearch;

import org.springframework.boot.SpringApplication;

public class TestSmartSearchApplication {

    public static void main(String[] args) {
        SpringApplication.from(SmartSearchApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
