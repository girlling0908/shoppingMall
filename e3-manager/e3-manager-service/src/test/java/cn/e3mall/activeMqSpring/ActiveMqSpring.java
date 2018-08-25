package cn.e3mall.activeMqSpring;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class ActiveMqSpring {

	
	@Test
	public void TestActiveMq() {
		//加载Spring 配置文件
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
	          
		//从容器中获得JMSTemplate对象
		JmsTemplate jmsTemplate = ac.getBean(JmsTemplate.class);
	    
		//从容器中获取Destination对象
		Destination destination=(Destination) ac.getBean("queueDestination");
	
	      //发送消息
		jmsTemplate.send( destination,new MessageCreator(){

			@Override
			public Message createMessage(Session session) throws JMSException {
                  return session.createTextMessage("send1Activemq1Message");
			}});
	}

}
