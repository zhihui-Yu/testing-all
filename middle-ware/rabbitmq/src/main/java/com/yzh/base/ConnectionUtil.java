package com.yzh.base;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Author yzh
 * @Date 2020/5/22 20:46
 * @Version 1.0
 */
public class ConnectionUtil {
    public static Connection getConnect() throws Exception
    {
        // TODO：定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        // TODO: 设置服务地址
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");

        //通过工厂获取连接
        return factory.newConnection();
    }
}
