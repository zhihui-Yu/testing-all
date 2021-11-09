package com.yzh.base.pubAndSub;

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

    private final static String EXCHANGE_NAME = "sub_queue";

    public static void main(String[] args) throws Exception {

        //TODO 获取连接
        Connection connect = ConnectionUtil.getConnect();

        //TODO 创建通道
        Channel channel = connect.createChannel();

        //TODO 声明队列
        //channel.queueDeclare(QUEUE_NAME,true,false,false,null);

        //TODO 配置
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        //TODO 随机生成queue，退出时自动删除
        String queueName = channel.queueDeclare().getQueue();

        //TODO 通道绑定queue 交换类型 路由键(fanout可以忽略路由键)
        channel.queueBind(queueName,EXCHANGE_NAME,"");

        //TODO 设置回调函数 异步监听队列事件
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            //TODO 获取消息
            String message = new String(delivery.getBody(),"UTF-8");
            System.out.println("处理消息："+message);
        };

        //TODO 通道绑定队列
        channel.basicConsume(queueName, true, deliverCallback, consumerTag->{
            System.out.println("eror");
        });


    }

}
