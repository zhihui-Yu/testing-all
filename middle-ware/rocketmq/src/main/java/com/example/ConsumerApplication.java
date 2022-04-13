package com.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class ConsumerApplication{

    public static void main(String[] args){
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Slf4j
    @Service
    @RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "my-consumer_test-topic-1")
    public static class MyConsumer1 implements RocketMQListener<String> {
        public void onMessage(String message) {
            log.info("received message from test-topic-1: {}", message);
        }
    }

    @Slf4j
    @Service
    @RocketMQMessageListener(topic = "test-topic-2", consumerGroup = "my-consumer_test-topic-2")
    public static class MyConsumer2 implements RocketMQListener<OrderPaidEvent>{
        public void onMessage(OrderPaidEvent orderPaidEvent) {
            log.info("received orderPaidEvent from test-topic-2 : {}", orderPaidEvent);
        }
    }

    @Slf4j
    @Service
    @RocketMQMessageListener(topic = "orderly_topic", consumerGroup = "my-consumer_test-topic-3")
    public static class MyConsumer3 implements RocketMQListener<String>{
        public void onMessage(String msg) {
            log.info("received msg form orderly_topic: {}", msg);
        }
    }
}