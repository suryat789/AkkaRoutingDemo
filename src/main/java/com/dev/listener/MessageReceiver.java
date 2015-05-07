package com.dev.listener;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dev.akka.master.NameMaster;
import com.dev.bean.Person;

/**
 * {@code MessageReceiver} is a JMS message listening class.
 * @see MessageListener
 */
public class MessageReceiver implements MessageListener {

	public void onMessage(Message message) {
		Person person = null;
		
		try {
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				System.out.println("Recvd: " + textMessage.getText());
			}
			else if(message instanceof ObjectMessage){
				ObjectMessage objectMessage = (ObjectMessage) message;
				person = (Person) objectMessage.getObject();
				
				// Send to Akka for Routing
				if(person != null){
					NameMaster.sendToAkka(person);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load the {@code ApplicationContext} to start the receiver.
	 * @param args
	 */
	public static void main(String... args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("./spring/MessageReceiver.xml");
	}
}