package com.atguigu.activemq;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSConsumer_Topic {
	public static final String DEFAULT_BROKER_BIND_URL="tcp://192.168.48.188:61616";
	  public static final String TOPIC_NAME="topic1";
	public static void main(String[] args) throws JMSException {
		
		//1、创建连接工厂，使用默认用户名和密码，编码不再体现  //broker 中间人。
		  ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(DEFAULT_BROKER_BIND_URL);
		//2、获取连接并启动
		  Connection connection = activeMQConnectionFactory.createConnection("kanwen110", "kanwen120");
		  connection.start();
		//3、创建会话，此步骤有两个蚕食，第一个是是否以事务的方式提交，第二个是默认的签收方式。
		  Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//4、创建队列(通过session创建队列)
		  Topic topic = session.createTopic(TOPIC_NAME);
		//5、创建消费者 (通过session创建消费者)
		  MessageConsumer messageConsumer = session.createConsumer(topic);
		  messageConsumer.setMessageListener(message->{
			  if (null!=message&&message instanceof TextMessage) {
				TextMessage textMessage=(TextMessage)message;
				try {
					System.out.println("---收到Topic"+textMessage.getText()+"\t"+textMessage.getStringProperty("zhangsan"));
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		  });
	}
}
