package com.dev.bean;

import java.io.Serializable;

/**
 * A normal {@code Person} bean.
 * @author surtiwar
 *
 */
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String address;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
