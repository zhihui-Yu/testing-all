package org.example.controller;

import org.example.domain.User;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author simple
 */
@RestController
public class UserController {
    @Resource
    UserService userService;

    @GetMapping("create-user")
    public String createUser(){
        User user = new User();
        user.setId(1);
        user.setAge(10);
        user.setName("xx's xx");
        userService.insertUser(user);
        return "ok";
    }

    @GetMapping("user/{name}")
    public User user(@PathVariable String name){
        return userService.user(name);
    }
}
