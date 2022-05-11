package com.example.controller;

import com.example.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author simple
 */
@RestController
public class UserController {
    @Resource
    UserService userService;

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable("id") String id) {
        return userService.user(id).toString();
    }

    @PostConstruct
    public void setUser(){
        userService.setUser();
    }
}
