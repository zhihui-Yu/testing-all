package com.example.service;

import com.example.domain.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author simple
 */
@Service
public class UserService {
    @Resource
    MongoTemplate mongoTemplate;
    @Resource
    MongoRepository<User, Long> mongoRepository;

    public User user(String id) {
//        return mongoTemplate.
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.find(query, User.class).get(0);
    }

    public void setUser() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName("test " + ThreadLocalRandom.current().nextInt());
        user.setCreatedTime(LocalDate.now());
//        mongoRepository.insert(user);
        mongoTemplate.save(user, "user");
    }
}
