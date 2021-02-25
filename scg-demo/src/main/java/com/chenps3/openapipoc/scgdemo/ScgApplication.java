package com.chenps3.openapipoc.scgdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScgApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScgApplication.class, args);
    }
}


