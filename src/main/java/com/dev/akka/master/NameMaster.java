package com.dev.akka.master;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.dev.akka.router.CustomNameRouter;
import com.dev.bean.Person;

/**
 * Master class which loads the Akka system. 
 * @author surtiwar
 *
 */
public class NameMaster {
	
	private static ActorSystem system; 
	private static ActorRef routedActor;
	
	private static void loadActorSystemAndRouter(){
		if(system == null || routedActor == null){
			system = ActorSystem.create("TestNameSystem");
			routedActor = system.actorOf(new Props().withRouter(new CustomNameRouter()));
		}
	}
	
	public static void sendToAkka(Person person){
		loadActorSystemAndRouter();
		
		routedActor.tell(person, null);
	}
}
