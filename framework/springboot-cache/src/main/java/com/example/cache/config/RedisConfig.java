package com.example.cache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author simple
 */
@Component
public class RedisConfig {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;//操作key-value都是字符串

    @Autowired
    private RedisTemplate redisTemplate;//操作key-value都是对象

    /**
     *  Redis常见的五大数据类型：
     *  stringRedisTemplate.opsForValue();[String(字符串)]
     *  stringRedisTemplate.opsForList();[List(列表)]
     *  stringRedisTemplate.opsForSet();[Set(集合)]
     *  stringRedisTemplate.opsForHash();[Hash(散列)]
     *  stringRedisTemplate.opsForZSet();[ZSet(有序集合)]
     */
    public void test(){
        stringRedisTemplate.opsForValue().append("msg","hello");
    }
}
