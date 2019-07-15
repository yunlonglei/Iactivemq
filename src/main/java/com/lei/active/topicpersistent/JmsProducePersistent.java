package com.lei.active.topicpersistent;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Author: leiyunlong
 * @Date: 2019/7/12 10:56
 * @Version 持久性V1.0
 */
public class JmsProducePersistent {
    public static final String ACTIVEMQ_URL = "tcp://39.96.27.148:61616";
    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) {
        //1.创建连接工厂，给定指定地URL，用默认的用户名密码（看源码）
        ActiveMQConnectionFactory activemqConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        try {
            //2.通过工厂获得的连接Connection，请启动访问
            Connection connection = activemqConnectionFactory.createConnection();
            //3.创建会话session 第一个参数叫事务，第二个参数叫签收
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //4.创建目的地，主题是queue还是topic
            Topic topic = session.createTopic(TOPIC_NAME);
            //5.创建消息生产者
            MessageProducer producer = session.createProducer(topic);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            connection.start();
            //6.通过producer生产3条消息发给MQ的queue
            int messageCount = 3;
            for (int i = 1; i <= messageCount; i++) {
                TextMessage textMessage = session.createTextMessage("topic_msg---" + i);
                producer.send(textMessage);
                System.out.println("topic_msg---" + i);
                //MapMessage示例
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("k1","v1");
                //对属性的配置
                mapMessage.setStringProperty("v1","vip");
                producer.send(mapMessage);
            }
            producer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            System.out.println("发送消息已完成");
        }
    }
}
