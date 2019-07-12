package com.lei.active.topic;

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
    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) {
        ActiveMQConnectionFactory activemqConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        try {
            Connection connection = activemqConnectionFactory.createConnection();
            connection.start();
            //3.创建会话session 第一个参数叫事务，第二个参数叫签收
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(TOPIC_NAME);
            /*
             * 异步非阻塞式方式：监听器 onMessage();
             * 订阅者或者接收者通过MessageConsumer的setMessageListener(MessageListener  listener)注册一个消息监听器，
             * 当消息到达后，系统会自动调用MessageListener的onMassage(Massges massage)方法；
             */
            MessageConsumer consumer = session.createConsumer(topic);
            //创建消费者之后，用消费者来监听消息队列
            consumer.setMessageListener(message -> {
                if (null != message && message instanceof TextMessage) {
                    TextMessage textmessage = (TextMessage) message;
                    try {
                        System.out.println("消费者接收到的消息是：" + textmessage.getText());
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
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            /* //这里是使用lamda表达式的方法，上面是使用普通方法
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
            });*/
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
