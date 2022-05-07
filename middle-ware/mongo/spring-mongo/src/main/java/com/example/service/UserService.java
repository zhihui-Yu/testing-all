package com.example.service;

import com.example.domain.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author simple
 */
@Service
public class UserService {
    @Resource
    MongoTemplate mongoTemplate;

    public User user() {
//        return mongoTemplate.
        return mongoTemplate.getCollection("user").find(User.class).first();
    }
}
