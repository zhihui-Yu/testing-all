package com.example.cache.controller;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author simple
 */
@RestController
@CacheConfig(cacheNames = {"userCache"})
public class MainController {
    /**
     * value	缓存的名称，在 spring 配置文件中定义，必须指定至少一个 <br/>
     * 例如：<br/>
     * {@code @Cacheable(value=”mycache”)} 或者<br/>
     * {@code @Cacheable(value={”cache1”,”cache2”}}<br/>
     * <br/>
     * key    缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，<br/>
     * 如果不指定，则缺省按照方法的所有参数进行组合<br/>
     * 例如：<br/>
     * {@code @Cacheable(value=”testcache”,key=”#id”)}<br/>
     * <br/>
     * condition    缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，<br/>
     * 只有为 true 才进行缓存/清除缓存<br/>
     * 例如：<br/>
     * {@code @Cacheable(value=”testcache”,condition=”#userName.length()>2”)}<br/>
     * <br/>
     * unless	否定缓存。当条件结果为TRUE时，就不会缓存。<br/>
     * {@code @Cacheable(value=”testcache”,unless=”#userName.length()>2”)}<br/>
     * <br/>
     * allEntries<br/>
     * (@CacheEvict )	是否清空所有缓存内容，缺省为 false，如果指定为 true，<br/>
     * 则方法调用后将立即清空所有缓存<br/>
     * 例如：<br/>
     * {@code @CachEvict(value=”testcache”,allEntries=true)}<br/>
     * <br/>
     * beforeInvocation<br/>
     * (@CacheEvict)	是否在方法执行前就清空，缺省为 false，如果指定为 true，<br/>
     * 则在方法还没有执行的时候就清空缓存，缺省情况下，如果方法<br/>
     * 执行抛出异常，则不会清空缓存<br/>
     * 例如：<br/>
     * {@code @CachEvict(value=”testcache”，beforeInvocation=true)}<br/>
     */

    @Cacheable("QUERY") // @Cacheable是基于Spring AOP代理类，内部方法调用是不走代理的，@Cacheable是不起作用的
    @GetMapping("hello/{id}")
    public List<String> hello(@PathVariable String id) {
        return queryAll("1");
    }

    public List<String> queryAll(String uid) {
        return List.of(UUID.randomUUID().toString());
    }

    @CacheEvict("QUERY")
    @GetMapping("refresh/{id}")
    public void refresh(@PathVariable String id) {
        System.out.println("refresh cache");
    }

    @CachePut("QUERY")
    @GetMapping("put/{id}")
    public List<String> put(@PathVariable String id) {
        System.out.println("put cache");
        return queryAll("1");
    }

    /**
     * 不配置时候， redis中的key 默认是 #cacheName::#key
     */
}
