package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "profile")
public class Profile {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = true)
	private String firstName;
	
	@Column(nullable = true)
	private String lastName;
	
	
	private String email;
	
	@Column(nullable = true)
	private int phone;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}
	
	

}
