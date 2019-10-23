package com.atguigu.activemq;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSProduce {
 public  static final String DEFAULT_BROKER_BIND_URL="tcp://192.168.48.188:61616";
 public static final String QUEUE_NAME="queue1";
  public static void main(String[] args) throws JMSException {
	//1、创建activeMQ连接工厂工厂
	  ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(DEFAULT_BROKER_BIND_URL);
	//2、创建连接，并开启连接
	  Connection connection = activeMQConnectionFactory.createConnection("kanwen110", "kanwen120");
	  connection.start();
	//3、创建session会话（起源点：属性（事务+确认）功能：创建destination（queue，topic），创建textmessage（））
	  Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
	 //4、（session）创建队列(方式：产生生产者和消费者的方式)
	  Queue queue = session.createQueue(QUEUE_NAME);
	  //5、（通过队列）创建生产者
	  MessageProducer messageProducer = session.createProducer(queue);
	  for (int i = 1; i <=6; i++) {
		//6、（session）会话创建消息文本
		  TextMessage textMessage = session.createTextMessage("0615msg+\t"+i);
		  //7、通过消息生产者发布消息
		  messageProducer.send(textMessage);
	}
	  //8关闭资源
	  messageProducer.close();
	  //开启事务必须在关闭之前提交。
	  session.commit();
	  session.close();
	  connection.close();
	  System.out.println("*****生产者run");
  }
}
