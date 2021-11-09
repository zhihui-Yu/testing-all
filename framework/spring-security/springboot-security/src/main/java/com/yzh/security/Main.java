package com.yzh.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author simple
 */
@SpringBootApplication
@MapperScan("com.yzh.security.mapper")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
