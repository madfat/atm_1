package com.atm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.atm"})
public class Atm extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Atm.class, args);
    }
}
