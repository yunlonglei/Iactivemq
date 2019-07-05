package com.lei.active.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @Author: leiyunlong
 * @Date: 2019/7/5 9:59
 * @Version 1.0
 */
public class JmsConsumerMessageListener {
    public static final String ACTIVEMQ_URL = "tcp://39.96.27.148:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) {

        ActiveMQConnectionFactory activemqConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        try {
            Connection connection = activemqConnectionFactory.createConnection();
            connection.start();
            //3.创建会话session 第一个参数叫事务，第二个参数叫签收
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(QUEUE_NAME);
            MessageConsumer consumer = session.createConsumer(queue);
            //创建消费者之后，用消费者来监听消息队列
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("消费者接收到的消息是：" + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            //持续打开控制台，以保证能读取到队列中的消息
            System.in.read();
            consumer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
