package com.atMq;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/mq.xml")
public class mqxml {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private ActiveMQQueue activeMQQueue;
    @Autowired
    private ActiveMQTopic activeMQTopic;
     @Test
    public void xmlmq() {
        jmsTemplate.send(activeMQQueue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("spring整合Mq发消息.....点对点模式");
            }
        });
    }


    @Test
    public void xmlmq1() {
        jmsTemplate.send(activeMQTopic, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("spring整合Mq发消息1111111111......订阅模式");
            }
        });
    }
}
