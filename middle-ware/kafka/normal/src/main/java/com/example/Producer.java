package com.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Random;

/**
 * @author simple
 */
public class Producer {
    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");//kafka地址，多个地址用逗号分割
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        try (KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(props)) {
            while (true) {
                String msg = "Hello," + new Random().nextInt(100);
//                ProducerRecord<String, String> record = new ProducerRecord<>(Topics.TOPIC_TEST,  msg);
//                ProducerRecord<String, String> record1 = new ProducerRecord<>(Topics.TOPIC_TEST1,  msg);
//                ProducerRecord<String, String> record2 = new ProducerRecord<>(Topics.TOPIC_TEST2,  msg);
//                ProducerRecord<String, String> record3 = new ProducerRecord<>(Topics.TOPIC_TEST3,  msg);
//                ProducerRecord<String, String> record4 = new ProducerRecord<>(Topics.TOPIC_TEST4,  msg);
                ProducerRecord<String, String> record6 = new ProducerRecord<>(Topics.TOPIC_TEST6, 0, null, msg);
                kafkaProducer.send(record6);
//                System.out.println("消息发送成功:" + msg);
//                kafkaProducer.send(record1);
//                System.out.println("1-消息发送成功:" + msg);
//                kafkaProducer.send(record2);
//                System.out.println("2-消息发送成功:" + msg);
//                kafkaProducer.send(record3);
//                System.out.println("3-消息发送成功:" + msg);
//                kafkaProducer.send(record4);
//                System.out.println("4-消息发送成功:" + msg);
                Thread.sleep(100);
            }
        }

    }
}
