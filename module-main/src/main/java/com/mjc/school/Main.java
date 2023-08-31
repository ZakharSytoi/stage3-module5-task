package com.mjc.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan({"com.mjc.school.repository", "com.mjc.school.service", "com.mjc.school.controller"})
public class Main {


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


}
