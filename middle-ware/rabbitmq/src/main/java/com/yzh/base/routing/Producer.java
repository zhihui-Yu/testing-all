package com.yzh.base.routing;

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
    private final  static String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        //TODO: 获取连接
        Connection connect = ConnectionUtil.getConnect();

        //TODO：创建通道
        Channel channel = connect.createChannel();

        //TODO：声明交换
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");

        //TODO: 发送消息
        String msg = "hello log";
        for (int i = 0; i < 10000; i++ ) {
            channel.basicPublish(EXCHANGE_NAME,i%2==0?"info":"warn",null, (msg + "-" + i).getBytes());
            System.out.println("发送消息"+msg+i);
        }

        //TODO: 关闭通道和连接
        channel.close();
        connect.close();


    }
}
