package com.lei.active.topicpersistent;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @Author: leiyunlong
 * @Date: 2019/7/12 9:59
 * @Version 1.0
 */
public class JmsConsumerMessageListenerPersistent {
    public static final String ACTIVEMQ_URL = "tcp://39.96.27.148:61616";
    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws JMSException {
        System.out.println("*******z3**********");
        ActiveMQConnectionFactory activemqConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activemqConnectionFactory.createConnection();
        // 创建客户端ID
        connection.setClientID("z3");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        // 去掉session.createConsumer(topic);
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "remake...");
        // 把连接的启动放到把session配置完成后
        connection.start();
        Message message = topicSubscriber.receive();
        while (null != message) {
            if (null != message && message instanceof TextMessage) {
                TextMessage textmessage = (TextMessage) message;
                try {
                    System.out.println("消费者接收到的消息是：" + textmessage.getText());
                    //用receive()方法获取下一个消息，并赋值给message，让循环直到为null
                    message = topicSubscriber.receive(1000L);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            //MapMessage示例
            if (null != message && message instanceof MapMessage) {
                try {
                    MapMessage mapMessage = (MapMessage) message;
                    System.out.println("消费者接收到的消息是：" + mapMessage.getString("k1"));
                    System.out.println("消费者接收到的map消息属性是：" + mapMessage.getStringProperty("v1"));
                    message = topicSubscriber.receive(1000L);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
        session.close();
        connection.close();
    }
}
