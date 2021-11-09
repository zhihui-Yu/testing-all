package com.yzh.base.pubAndSub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yzh.base.ConnectionUtil;

/**
 * @Author yzh
 * @Date 2020/5/22 20:51
 * @Version 1.0
 * @description 消息的生产者
 */
public class Producer {
    private final  static String EXCHANGE_NAME = "sub_queue";

    public static void main(String[] args) throws Exception {
        //TODO: 获取连接
        Connection connect = ConnectionUtil.getConnect();

        //TODO：创建通道
        Channel channel = connect.createChannel();

        //TODO 配置交换类型
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        //TODO：声明队列  第二参数：是否持久化
        //channel.queueDeclare(QUEUE_NAME,true,false,false,null);

        //TODO: 发送消息
        String msg = "hello sub-queue";

        channel.basicPublish(EXCHANGE_NAME,"",null, (msg + "-").getBytes());
        System.out.println("发送消息："+msg);

        //TODO: 关闭通道和连接
        channel.close();
        connect.close();


    }
}
