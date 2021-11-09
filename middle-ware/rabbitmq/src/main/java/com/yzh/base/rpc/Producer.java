package com.yzh.base.rpc;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yzh.base.ConnectionUtil;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

/**
 * @Author yzh
 * @Date 2020/5/22 20:51
 * @Version 1.0
 * @description 消息的生产者
 */
public class Producer implements AutoCloseable{

    private static Connection connection;
    private static Channel channel ;
    private static String requestQueueName = "rpc_queue";

    public static void main(String[] args) throws Exception {
        //TODO: 获取连接
        connection = ConnectionUtil.getConnect();
        channel = connection.createChannel();
        try (Producer fibonacciRpc = new Producer()) {
            for (int i = 0; i < 32; i++) {
                String i_str = Integer.toString(i);
                System.out.println(" [x] Requesting fib(" + i_str + ")");
                String response = fibonacciRpc.call(i_str);
                System.out.println(" [.] Got '" + response + "'");
            }
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    private String call(String message) throws Exception {
        final String corrid = UUID.randomUUID().toString();

        String replyQueueName = channel.queueDeclare().getQueue();

        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                                                    .correlationId(corrid)
                                                    .replyTo(replyQueueName)
                                                    .build();
        channel.basicPublish("",requestQueueName , properties, message.getBytes("UTF-8"));

        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        String ctag = channel.basicConsume(replyQueueName, true,  (consumerTag, delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(corrid)) {
                response.offer(new String(delivery.getBody(), "UTF-8"));
            }
        }, consumerTag -> {
        });

        String result = response.take();

        channel.basicCancel(ctag);
        return  result;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
