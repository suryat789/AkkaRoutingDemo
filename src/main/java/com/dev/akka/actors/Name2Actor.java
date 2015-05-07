package com.dev.akka.actors;

import akka.actor.UntypedActor;

import com.dev.bean.Person;

/**
 * A normal Actor to print the name of the Person.
 */
public class Name2Actor extends UntypedActor {

	@Override
	public void onReceive(Object object) throws Exception {
		
		if(object != null){
			System.out.println("Routed from Name2Actor :: " + ((Person)object).getName());
		}
	}

}
