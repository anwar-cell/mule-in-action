package com.prancingdonkey.model.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Friend {
	private String name, surname;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	
}
