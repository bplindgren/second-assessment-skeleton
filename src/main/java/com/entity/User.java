package com.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique=true)
	private String username;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Profile profile;

	private long joined;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Credentials credentials;
	
	private boolean active;
	
	@OneToMany(mappedBy="author")  
	private Set<Tweet> tweets;
	
	@OneToMany
	@JsonIgnore
	private Set<User> follows;
	
	@OneToMany
	@JsonIgnore
	private Set<User> followers;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}	
	
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	public long getJoined() {
		return joined;
	}

	public void setJoined(long l) {
		this.joined = l;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Set<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(Set<Tweet> tweets) {
		this.tweets = tweets;
	}

	public Set<User> getFollows() {
		return follows;
	}

	public void setFollows(Set<User> follows) {
		this.follows = follows;
	}

	public Set<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}

	
	
}
