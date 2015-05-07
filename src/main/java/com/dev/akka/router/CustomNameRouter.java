package com.dev.akka.router;

import java.util.Arrays;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.dispatch.Dispatchers;
import akka.routing.CustomRoute;
import akka.routing.CustomRouterConfig;
import akka.routing.Destination;
import akka.routing.RouteeProvider;

import com.dev.akka.actors.Name1Actor;
import com.dev.akka.actors.Name2Actor;
import com.dev.akka.msg.PersonName;
import com.dev.bean.Person;

/**
 * {@code CustomNameRouter} class contains the actual routing logic. It routes messages to respective Actors of the message. 
 *
 */
public class CustomNameRouter extends CustomRouterConfig {

	public String routerDispatcher() {
		return Dispatchers.DefaultDispatcherId();
	}

	public SupervisorStrategy supervisorStrategy() {
		return SupervisorStrategy.defaultStrategy();
	}

	@Override
	public CustomRoute createCustomRoute(Props props, RouteeProvider routeeProvider) {
		// Declare Actors
		final ActorRef name1Actor = routeeProvider.context().actorOf(new Props(Name1Actor.class), "d");
		final ActorRef name2Actor = routeeProvider.context().actorOf(new Props(Name2Actor.class), "r");

		// Add Actors to Router's list
		List<ActorRef> routees = Arrays.asList(new ActorRef[] { name1Actor, name2Actor });

		// Register Actors to Router's list
		routeeProvider.registerRoutees(routees);

		return new CustomRoute() {
			// Define routing logic
			public Iterable<Destination> destinationsFor(ActorRef sender, Object msg) {
				Person person = (Person) msg;
				
				if(person.getName().equalsIgnoreCase(PersonName.Name1)){
					return Arrays.asList(new Destination[] { new Destination(sender, name1Actor) });
				}
				else if(person.getName().equalsIgnoreCase(PersonName.Name2)){
					return Arrays.asList(new Destination[] { new Destination(sender, name2Actor) });
				}
				else {
					throw new IllegalArgumentException("Unknown message: " + msg);
				}
			}
		};
	}
}
