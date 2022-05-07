package com.example;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.math.BigDecimal;

@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * rocketmq 的 topic-queue 就像 kafka的 topic-partition, 用来保证消费的顺序性。
     * 不同的是，rocketmq，可以在producer和customer实现queue的自定义选择器。 或者配置服务发送消息和消费消息是同步的
     * 如果使用 syncSendOrderly，就是走hash key的方式发送到相对的queue中
     */
    public void run(String... args) {
        //send message synchronously
        rocketMQTemplate.convertAndSend("test-topic-1", "Hello, World!");
        //send spring message
        rocketMQTemplate.send("test-topic-1", MessageBuilder.withPayload("Hello, World! I'm from spring message").build());
        //send messgae asynchronously
        rocketMQTemplate.asyncSend("test-topic-2", new OrderPaidEvent("T_001", new BigDecimal("88.00")), new SendCallback() {
            @Override
            public void onSuccess(SendResult var1) {
                System.out.printf("async onSucess SendResult=%s %n", var1);
            }

            @Override
            public void onException(Throwable var1) {
                System.out.printf("async onException Throwable=%s %n", var1);
            }

        });
        //Send messages orderly
        rocketMQTemplate.syncSendOrderly("orderly_topic", MessageBuilder.withPayload("Hello, World").build(), "hashkey");

        // test order
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            new Thread(() -> {
                rocketMQTemplate.syncSendOrderly("orderly_topic", MessageBuilder.withPayload(String.format("Hello, World-%s", finalI)).build(), String.format("orderly_topic-%s", finalI));
            }).start();
        }

        //rocketMQTemplate.destroy(); // notes:  once rocketMQTemplate be destroyed, you can not send any message again with this rocketMQTemplate
    }
}