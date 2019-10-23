package com.atguigu.activemq;

import java.util.UUID;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSProduce_Topic {
	public static final String DEFAULT_BROKER_BIND_URL="tcp://192.168.48.188:61616";
	  public static final String TOPIC_NAME="topic1";
	public static void main(String[] args) throws JMSException {
		//1、创建activeMQ连接工厂工厂
		  ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(DEFAULT_BROKER_BIND_URL);
		//2、创建连接，并开启连接
		  Connection connection = activeMQConnectionFactory.createConnection("kanwen110", "kanwen120");
		  connection.start();
		//3、创建session会话（起源点：属性（事务+确认）功能：创建destination（queue，topic），创建textmessage（））
		  Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		 //4、（session）创建队列(方式：产生生产者和消费者的方式)
		  Topic topic = session.createTopic(TOPIC_NAME);
		  //5、（通过队列）创建生产者
		  MessageProducer messageProducer = session.createProducer(topic);
		   //messageProducer.setPriority(5);
		  //messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		 // messageProducer.setDisableMessageID(true);
		  for (int i = 1; i <=3; i++)
	        {
	            TextMessage textMessage = session.createTextMessage("topicMsg--" + i);
	            textMessage.setStringProperty("zhangsan", "jiushizhangsan1");
	            messageProducer.send(textMessage);
	        }
		  //释放资源
	        messageProducer.close();
	        //开启事务，必提交。
	        session.commit();
	        session.close();
	       /* try {           //session会话异常，回滚会话，避免会话脏读。
				session.close();
			} catch (Exception e) {
				 
				e.printStackTrace();
			}finally {
				session.rollback();
			}*/
	        connection.close();

	        System.out.println("********主题消息发送到MQ完成");
	}
}
