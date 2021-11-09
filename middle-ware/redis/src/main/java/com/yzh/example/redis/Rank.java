package com.yzh.example.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author yzh
 * @Date 2020/5/23 16:38
 * @Version 1.0
 * @description redis的zset
 */
public class Rank {

    private static Jedis redis;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(10);
        JedisPool pool = new JedisPool(config, "localhost", 6379);
        redis = pool.getResource();
    }

    private static final String SCORE_RANK = "score_rank";

    /**
     * 新增 zset内容
     */
    @Test
    public void batchAdd() {
        Map<String, Double> map = new HashMap<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            map.put("张三" + i, 1d + i);
        }
        System.out.println("循环时间:" + (System.currentTimeMillis() - start));
        Long num = redis.zadd(SCORE_RANK, map);
        System.out.println("批量新增时间:" + (System.currentTimeMillis() - start));
        System.out.println("受影响行数：" + num);

    }

    /**
     * 统计两个分数之间的人数
     */
    @Test
    public void count() {
        Long count = redis.zcount(SCORE_RANK, 8001, 9000);
        System.out.println("统计8001-9000之间的人数:" + count);
    }

    /**
     * 获取整个集合的基数(数量大小)
     */
    @Test
    public void zCard() {
        Long aLong = redis.zcard(SCORE_RANK);
        System.out.println("集合的基数为：" + aLong);
    }

    /**
     * 使用加法操作分数
     */
    @Test
    public void incrementScore() {
        Double score = redis.zincrby(SCORE_RANK, 1000, "李四");
        System.out.println("李四分数+1000后：" + score);
    }
}
