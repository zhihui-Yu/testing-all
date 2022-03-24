package com.example.cache.controller;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author simple
 */
@RestController
@CacheConfig(cacheNames = {"userCache"})
public class MainController {
    @Cacheable("QUERY") // @Cacheable是基于Spring AOP代理类，内部方法调用是不走代理的，@Cacheable是不起作用的
    @GetMapping("hello")
    public List<String> hello() {
        return queryAll("1");
    }

    public List<String> queryAll(String uid) {
        return List.of(UUID.randomUUID().toString());
    }
}
