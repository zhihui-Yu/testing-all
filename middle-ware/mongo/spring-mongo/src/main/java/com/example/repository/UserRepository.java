package com.example.repository;

import com.example.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author simple
 */
public interface UserRepository extends CrudRepository<User, Long> {

}
