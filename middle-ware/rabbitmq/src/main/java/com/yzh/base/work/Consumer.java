package com.yzh.base.work;

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

    private final static String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws Exception {

        //TODO 获取连接
        Connection connect = ConnectionUtil.getConnect();

        //TODO 创建通道
        Channel channel = connect.createChannel();

        //TODO 声明队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);


        //TODO 设置回调函数 异步监听队列事件
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            //TODO 获取消息
            String message = new String(delivery.getBody(),"UTF-8");
            //TODO 睡 1秒 ：假装是复杂的任务
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("处理消息："+message);
        };

        //TODO 告诉RabbitMQ 当消费者处理完在分配任务
        channel.basicQos(1);

        //TODO 通道绑定队列
        /**
         * 第一参数：队列名称
         * 第二参数：监听队列，false表示手动返回完成状态，true表示自动
         * 第三参数：绑定消费者
         * 第四参数：错误时返回
         */

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag->{
            System.out.println("eror");
        });


    }

}
