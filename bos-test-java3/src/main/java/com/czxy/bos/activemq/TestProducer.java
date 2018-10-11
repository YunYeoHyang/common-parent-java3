package com.czxy.bos.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


public class TestProducer {
    public static void main(String[] args) throws Exception {
        //1 开始 （获得工厂、获得连接、开始）
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //2 获得会话 (自动模式)
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

        //3 获得生成者 (需要确定使用发送消息的方式：queue、topic)
        Queue queue = session.createQueue("hello");
        MessageProducer producer = session.createProducer(queue);

        //4 创建消息
        TextMessage textMessage = session.createTextMessage("333");

        //5 通过生产者发送消息
        producer.send(textMessage);

        //6 释放资源
        session.commit();
        ;
        session.close();
        connection.close();
        System.out.println("生产者已经生成完毕！");

    }
}
