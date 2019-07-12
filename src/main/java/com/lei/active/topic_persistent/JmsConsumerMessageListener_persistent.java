package com.lei.active.topic_persistent;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @Author: leiyunlong
 * @Date: 2019/7/12 9:59
 * @Version 1.0
 */
public class JmsConsumerMessageListener_persistent {
    public static final String ACTIVEMQ_URL = "tcp://39.96.27.148:61616";
    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws JMSException {
        System.out.println("*******z3**********");
        ActiveMQConnectionFactory activemqConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection = activemqConnectionFactory.createConnection();
        connection.setClientID("z3");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "remake...");
        connection.start();
        Message message = topicSubscriber.receive();
        System.out.println("message是："+message);
        while (null != message) {
            TextMessage textmessage = (TextMessage) message;
            System.out.println(textmessage.getText());
            message = topicSubscriber.receive(1000L);
            System.out.println("while里面的message是："+message);

        }
        session.close();
        connection.close();
    }
}
