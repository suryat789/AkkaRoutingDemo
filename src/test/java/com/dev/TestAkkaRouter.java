package com.dev;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.dev.akka.msg.PersonName;
import com.dev.akka.router.CustomNameRouter;
import com.dev.bean.Person;

/**
 * {@code TestAkkaRouter} class can be used to test Akka routing independent of JMS.
 * Number of messages to be sent can be defined in {@code NO_OF_MSGS} variable in class.
 *
 */
public class TestAkkaRouter {
	private static final int NO_OF_MSGS = 1000;
	private static Random random = new Random();
	private static List<String> nums = new ArrayList<String>();
	
	public static void main(String[] args) {
		loadDummyData();
		int index = 0;
		String strName = null;
		
		ActorSystem system = ActorSystem.create("TestNameSystem");
		ActorRef routedActor = system.actorOf(new Props().withRouter(new CustomNameRouter()));
		
		Person person = new Person();
		
		for(int i=1; i<=NO_OF_MSGS; i++){
			index = random.nextInt(nums.size());
			strName = nums.get(index);
			person.setName(strName);
			routedActor.tell(person, null);
		}
		
	}
	
	/**
	 * Load dummy data.
	 */
	public static void loadDummyData() {
		nums.add(PersonName.Name1);
		nums.add(PersonName.Name2);
	}
}
