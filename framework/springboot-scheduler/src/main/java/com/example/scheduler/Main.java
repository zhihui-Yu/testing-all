package com.example.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * @author simple
 */
@EnableScheduling
@Async // 默认只有一个线程执行任务，该注解表示开启多线程执行
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    private final Logger logger = LoggerFactory.getLogger(Main.class);

    @Scheduled(cron = "0/5 * * * * *")
    public void task() {
        logger.info("使用cron---任务执行时间：{}  线程名称：{}", LocalDateTime.now(), Thread.currentThread().getName());
    }

    @Scheduled(fixedDelay = 5000) // 上一次执行完毕时间点之后 3 秒再执行
    public void task1() {
        logger.info("使用fixedDelay---任务执行时间：{}  线程名称：{}", LocalDateTime.now(), Thread.currentThread().getName());
    }

    @Scheduled(fixedRate = 10000) // 上一次执行点之后 3 秒再执行
    public void task2() {
        logger.info("使用fixedRate---任务执行时间：{}  线程名称：{}", LocalDateTime.now(), Thread.currentThread().getName());
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 20000)
    public void task3() {
        logger.info("使用initialDelay+fixedDelay---任务执行时间：{}  线程名称：{}", LocalDateTime.now(), Thread.currentThread().getName());
    }
}
