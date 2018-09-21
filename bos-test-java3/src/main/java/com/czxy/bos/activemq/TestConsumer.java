package com.czxy.bos.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TestConsumer {
    public static void main(String[] args) throws Exception {
        //1 开始（工厂、连接、开始）
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        Connection connection = connectionFactory.createConnection();
        connection.start();

        //2 获得会话
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

        //3 获得消费者（必须确定消费方式，必须与生产者的生成方式匹配：queue、topic）
        Queue queue = session.createQueue("hello");
        MessageConsumer consumer = session.createConsumer(queue);

        //4 接收消息，并打印
        TextMessage message = (TextMessage) consumer.receive(10000);
        if(message != null){
            System.out.println(message.getText());
        } else {
            System.out.println("没有消息");
        }

        //5 释放资源
        session.commit();
        session.close();
        connection.close();


    }
}
