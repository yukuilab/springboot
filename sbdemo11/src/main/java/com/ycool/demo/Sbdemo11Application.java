package com.ycool.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ycool"})
public class Sbdemo11Application {

    public static void main(String[] args) {
        SpringApplication.run(Sbdemo11Application.class, args);
    }

}
