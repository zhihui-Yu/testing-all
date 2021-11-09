package org.example.service;

import org.example.domain.User;
import org.example.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author simple
 */
@Service
public class UserService {
    @Resource
    UserMapper userMapper;

    public void insertUser(User user){
        userMapper.insertUser(user);
    }

    public User user(String name){
        return userMapper.user(name);
    }
}
