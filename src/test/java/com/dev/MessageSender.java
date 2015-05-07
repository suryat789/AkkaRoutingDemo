package com.dev;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.dev.akka.msg.PersonName;
import com.dev.bean.Person;

/**
 * {@code MessageSender} is class which posts series of messages on the queue. 
 * Number of messages to be sent can be defined in {@code NO_OF_MSGS} variable in class.
 *
 */
public class MessageSender {
	private static final int NO_OF_MSGS = 500;
	private final JmsTemplate jmsTemplate;
	private static Random random = new Random();
	private static List<String> nums = new ArrayList<String>();

	public MessageSender(final JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public static void main (String... args) {
		int index = 0;
		ApplicationContext context = new ClassPathXmlApplicationContext("./spring/MessageSender.xml");
		MessageSender testMessageSender = (MessageSender) context.getBean("messageSender");
		Person person = new Person();
		String strName = null;
		loadDummyData();

		for(int i=1; i<=NO_OF_MSGS; i++){
			index = random.nextInt(nums.size());
			strName = nums.get(index);
			person.setName(strName);
			testMessageSender.send(person);
		}
		System.exit(1);
	}

	/**
	 * send method to send a {@code String} message
	 * @param personName
	 */
	public void send(final String personName) {
		//jmsTemplate.convertAndSend(personName);
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage(personName);
				return message;
			}
		});
	}

	/**
	 * send method to send {@code Person} object.
	 * @param person
	 */
	public void send(final Person person) {
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage message = session.createObjectMessage();
				message.setObject(person);
				return message;
			}
		});
	}
	/**
	 * Load dummy data.
	 */
	public static void loadDummyData() {
		nums.add(PersonName.Name1);
		nums.add(PersonName.Name2);
	}
}