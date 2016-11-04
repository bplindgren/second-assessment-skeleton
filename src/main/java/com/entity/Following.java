package com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "followings")
public class Following {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn
	@JsonIgnore
	private User follower;
	
	@ManyToOne
	@JoinColumn
	@JsonIgnore
	private User following;

	public Following(User follower, User following) {
		this.follower = follower;
		this.following = following;
	}
	
}
