package com.rest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author simple
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.com.rest.controller")
public class WebConfig {
}
