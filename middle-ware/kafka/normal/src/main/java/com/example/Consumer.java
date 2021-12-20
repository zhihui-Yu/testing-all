package com.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

/**
 * @author simple
 */
public class Consumer {
    public static void main(String[] args) {
        int size = 1;
        Thread[] threads = new Thread[size];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(Consumer::createConsumer);
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
    }

    private static void createConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "consume_topic_test1");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
        List<String> topicTest = List.of(Topics.TOPIC_TEST, Topics.TOPIC_TEST1, Topics.TOPIC_TEST2, Topics.TOPIC_TEST3, Topics.TOPIC_TEST4);
        kafkaConsumer.subscribe(topicTest);// 订阅消息
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(5));
            if (records.isEmpty()) System.out.println(Thread.currentThread().getName() + ": " + "records is empty.");
            for (ConsumerRecord<String, String> record : records) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println(Thread.currentThread().getName() + ": " + String.format("topic:%s, offset:%d, 消息:%s", record.topic(), record.offset(), record.value()));
            }
        }
    }
}
