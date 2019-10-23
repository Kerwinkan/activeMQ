package com.atguigu.activemq;

import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSConsumer {
  public static final String DEFAULT_BROKER_BIND_URL="tcp://192.168.48.188:61616";
  public static final String QUEUE_NAME="queue1";
  public static void main(String[] args) throws JMSException {
	//1、创建连接工厂，使用默认用户名和密码，编码不再体现  //broker 中间人。
	  ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(DEFAULT_BROKER_BIND_URL);
	//2、获取连接并启动
	  Connection connection = activeMQConnectionFactory.createConnection("kanwen110", "kanwen120");
	  connection.start();
	//3、创建会话，此步骤有两个蚕食，第一个是是否以事务的方式提交，第二个是默认的签收方式。
	  Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
	//4、创建队列(通过session创建队列)
	  Queue queue = session.createQueue(QUEUE_NAME);
	//5、创建消费者 (通过session创建消费者)
	  MessageConsumer messageConsumer = session.createConsumer(queue);
	  /*异步非阻塞方式（监听器onMessage()）
	   * 订阅者或接收者通过MessageConsumer的setMessageListener（MessageListener listener）注册一个消息监听器，
	   * 当消息到达之后，系统自动调用监听器MessageListener的onMessage(Message message)方法
	   * */
	 /* messageConsumer.setMessageListener(new MessageListener() {
		
		@Override
		public void onMessage(Message message) {
			if (message!=null && message instanceof TextMessage) {
				TextMessage textMessage=(TextMessage)message;
				try {
					System.out.println("消费者接受到消息："+textMessage.getText());
				} catch (JMSException e) {
 					e.printStackTrace();
				}
			}
		}
	});*/
	  //监听器模式下不需要关闭资源。
   messageConsumer.setMessageListener(message->{
		  if (message!=null && message instanceof TextMessage) {
				TextMessage textMessage=(TextMessage)message;
				try {
				    textMessage.acknowledge();
					System.out.println("消费者接受到消息："+textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
	  }); 
	 /* while(true) {
		  //6、接受生产者提供的对应的消息
		  TextMessage textMessage = (TextMessage)messageConsumer.receive();
		 // TextMessage textMessage = (TextMessage)messageConsumer.receive(4*1000);
		  //7、如果消息不如空打印，为空中断while循环。
		  if(textMessage!=null) {
			System.out.println("消费者接受到的消息是："+textMessage.getText());
		}else {
			break;
		}
	  }*/
       //暂停毫秒
   		try {
			TimeUnit.MICROSECONDS.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   		System.out.println("----end MessageConsumer");
	  //8、关闭连接  //监听器模式下不需要关闭资源。
	 //messageConsumer.close();
	 // session.close();
	 //connection.close();
	   
  }
}
