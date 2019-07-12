package com.lei.active.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Author: leiyunlong
 * @Date: 2019/7/5 9:32
 * @Version 1.0
 */
public class JmsConsumer {
    public static final String ACTIVEMQ_URL = "tcp://39.96.27.148:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) {

        ActiveMQConnectionFactory activemqConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        try {
            Connection connection = activemqConnectionFactory.createConnection();
            connection.start();
            //3.创建会话session 第一个参数叫事务，第二个参数叫签收
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            System.out.println(session);
            Queue queue = session.createQueue(QUEUE_NAME);
            MessageConsumer consumer = session.createConsumer(queue);
            //创建消费者，用消费者的receive()方法，监听消息队列中的消息（也可以设着超时时间）
            while (true) {
                TextMessage textMessage = (TextMessage) consumer.receive();
                if (null != textMessage) {
                    System.out.println("消费者接收到的消息是：" + textMessage.getText());
                } else {
                    break;
                }
            }
            consumer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
