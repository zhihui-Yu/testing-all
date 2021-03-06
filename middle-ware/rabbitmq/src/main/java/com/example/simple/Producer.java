package com.example.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.example.ConnectionUtil;

/**
 * @Author yzh
 * @Date 2020/5/22 20:51
 * @Version 1.0
 * @description 消息的生产者
 */
public class Producer {
    private final  static String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws Exception {
        //TODO: 获取连接
        Connection connect = ConnectionUtil.getConnect();

        //TODO：创建通道
        Channel channel = connect.createChannel();

        //TODO：声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //TODO: 发送消息
        String msg = "hello mq";

        channel.basicPublish("",QUEUE_NAME,null, msg.getBytes());
        System.out.println("消息发送成功");

        //TODO: 关闭通道和连接
        channel.close();
        connect.close();


    }
}
