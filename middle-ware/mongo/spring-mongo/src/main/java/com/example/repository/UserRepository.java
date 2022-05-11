package com.example.repository;

import com.example.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author simple
 */
@Repository
public interface UserRepository extends MongoRepository<User, Long> {

}
