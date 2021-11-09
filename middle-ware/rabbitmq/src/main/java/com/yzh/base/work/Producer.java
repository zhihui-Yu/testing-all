package com.yzh.base.work;

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
    private final  static String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws Exception {
        //TODO: 获取连接
        Connection connect = ConnectionUtil.getConnect();

        //TODO：创建通道
        Channel channel = connect.createChannel();

        //TODO：声明队列  第二参数：是否持久化
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);

        //TODO: 发送消息
        String msg = "hello work-queue";

        for (int i = 0; i < 10000; i++ ) {
            channel.basicPublish("",QUEUE_NAME,null, (msg + "-" + i).getBytes());
            System.out.println("发送消息"+msg+i);
        }

        //TODO: 关闭通道和连接
        channel.close();
        connect.close();


    }
}
