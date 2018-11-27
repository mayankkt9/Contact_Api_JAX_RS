package com.mayank.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Contact {
	
	private String email;
	private String name;
	private String phone_number;
	
	
	public Contact()
	{
		this.email = "";
		this.name = "";
		this.phone_number = "";
	}
	
	public Contact(String email, String name, String phone_number) {
		super();
		this.email = email;
		this.name = name;
		this.phone_number = phone_number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	@Override
	public String toString() {
		return "Contact [email=" + email + ", name=" + name + ", phone_number=" + phone_number + "]";
	}
	
	

}
