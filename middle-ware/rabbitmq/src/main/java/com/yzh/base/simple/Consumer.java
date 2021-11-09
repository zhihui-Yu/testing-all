package com.yzh.base.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.DeliverCallback;
import com.yzh.base.ConnectionUtil;

/**
 * @Author yzh
 * @Date 2020/5/22 20:51
 * @Version 1.0
 * @description 消息的消费者
 */
public class Consumer {

    private final static String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws Exception {

        //TODO 获取连接
        Connection connect = ConnectionUtil.getConnect();

        //TODO 创建通道
        Channel channel = connect.createChannel();

        //TODO 声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);


        //TODO 设置回调函数 异步监听队列事件
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            //TODO 获取消息
            String message = new String(delivery.getBody(),"UTF-8");
            System.out.println("message："+message);
        };
        //TODO 通道绑定队列 开启监听 绑定消费者
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,consumerTag->{});


    }

}
