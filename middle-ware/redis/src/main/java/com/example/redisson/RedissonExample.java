package com.example.redisson;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * @Author yzh
 * @Date 2020/5/15 21:55
 * @Version 1.0
 */
public class RedissonExample {

    private static RedissonClient redisson;

    public static void main(String[] args) {
        init();

        //System.out.println(redisson.getConfig().toYAML().toString());
        //创建一个key为 test 的数据
//        doBucket();

        doGetLock();
        redisson.shutdown();

    }

    private static void doGetLock() {
        //创建锁对象，getLock中不能是redis中已经存在的值
        RLock lock = redisson.getLock("key:lock");
        try {
            lock.tryLock(2, 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("lock");

        lock.unlock();
    }

    /**
     * 初始化连接
     */
    private static void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        redisson = org.redisson.Redisson.create(config);
    }

    private static void doBucket() {
        RBucket<String> bucket = redisson.getBucket("test");
        bucket.set("123");
        System.out.println(bucket.get());
        boolean isUpdated = bucket.compareAndSet("123", "4934");
        System.out.println("compareAndSet" + bucket.get());
        String prevObject = bucket.getAndSet("321");
        System.out.println("getAndSet" + bucket.get());
        boolean isSet = bucket.trySet("901");
        System.out.println("trySet:" + bucket.get());
        long objectSize = bucket.size();
        System.out.println("size:" + bucket.get());
        // set with expiration
        bucket.set("value", 10, TimeUnit.SECONDS);
        boolean isNewSet = bucket.trySet("nextValue", 10, TimeUnit.SECONDS);
        System.out.println(isNewSet);
    }

}
