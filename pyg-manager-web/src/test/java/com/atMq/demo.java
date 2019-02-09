package com.atMq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import javax.jms.*;

public class demo {

    /**
     * 点对点模式
     * @throws Exception
     */
    @Test
    public void de() throws Exception {
        //创建连接工厂
        ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.66.128:61616");
        //创建连接
        Connection connection = cf.createConnection();
        //开启连接
        connection.start();
        //从连接中获取回话对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //  获取我的列队
        Queue myQueue = session.createQueue("OOO");
        //获得消息制作者
        MessageProducer producer = session.createProducer(myQueue);
        //创建消息对象

        ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText("大大的游戏");
        producer.send(message);
        session.close();
        producer.close();
        connection.close();

    }

    /**
     * 订阅模式
     * @throws Exception
     */

    @Test
    public void de11() throws Exception {
        //创建连接工厂
        ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.66.128:61616");
        //创建连接
        Connection connection = cf.createConnection();
        //开启连接
        connection.start();
        //从连接中获取回话对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //  获取我的列队

        Topic tttttt = session.createTopic("tttttt");

        //获得消息制作者

        MessageProducer producer = session.createProducer(tttttt);
        //创建消息对象

        ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText("大大的游戏");
        producer.send(message);
        session.close();
        producer.close();
        connection.close();

    }
}

