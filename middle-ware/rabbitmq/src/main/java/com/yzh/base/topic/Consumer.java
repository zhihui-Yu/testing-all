package com.yzh.base.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.yzh.base.ConnectionUtil;

/**
 * @Author yzh
 * @Date 2020/5/22 20:51
 * @Version 1.0
 * @description 消息的消费者
 */
public class Consumer {

    private final  static String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {

        //TODO 获取连接
        Connection connect = ConnectionUtil.getConnect();

        //TODO 创建通道
        Channel channel = connect.createChannel();

        //TODO 声明交换类型
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");

        //TODO 获取随机队列
        String queue = channel.queueDeclare().getQueue();

        //TODO 将队列绑定交换类型和路由键  只接收路由键一致的消息
        //客户端一的路由键
        //String key = "*.rabbitmq";
        // 客户端二的路由键
        String key = "*.green";
        channel.queueBind(queue,EXCHANGE_NAME,key);

        //TODO 设置回调函数 异步监听队列事件
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            //TODO 获取消息
            String message = new String(delivery.getBody(),"UTF-8");
            System.out.println("处理消息："+message);
        };


        //TODO 通道绑定队列
        channel.basicConsume(queue, true, deliverCallback, consumerTag->{
            System.out.println("error");
        });


    }

}
