package com.dev.akka.actors;

import com.dev.bean.Person;

import akka.actor.UntypedActor;

/**
 * A normal Actor to print the name of the Person.
 */
public class Name1Actor extends UntypedActor {

	@Override
	public void onReceive(Object object) throws Exception {
		
		if(object != null){
			System.out.println("Routed from Name1Actor :: " + ((Person)object).getName());
		}
	}
}
