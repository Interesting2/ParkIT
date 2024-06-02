package com.G23.ParkIt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ParkItApplication {
    public static void main(String[] args) {
        SpringApplication.run(ParkItApplication.class, args);
    }
}