package com.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	@Column(unique=true, nullable=false)
	private String username;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Profile profile;

	@Column(nullable = false)
	private long joined;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Credentials credentials;
	
	@Column(nullable = false)
	private boolean active;
	
	@OneToMany(mappedBy="author")  
	private List<Tweet> tweets;
	
	@ManyToMany
	@JoinTable(name = "followings", 
	 			joinColumns = { @JoinColumn(name = "follower_id") }, 
	 			inverseJoinColumns = { @JoinColumn(name = "following_id") })
	private Set<User> following;
	
	@ManyToMany(mappedBy="following")
	private Set<User> followers;
	
	@ManyToMany
	@JoinTable(name = "likes", 
	 			joinColumns = { @JoinColumn(name = "liker_id") }, 
	 			inverseJoinColumns = { @JoinColumn(name = "tweet_id") })
	private Set<Tweet> likedTweets;
	
	@ManyToMany
	@JoinTable(name = "mentions", 
	 			joinColumns = { @JoinColumn(name = "mentioned_id") }, 
	 			inverseJoinColumns = { @JoinColumn(name = "tweet_id") })
	private Set<Tweet> mentions;

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
	
	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}

	@JsonIgnore
	public Set<User> getFollowing() {
		return following;
	}

	public void setFollowing(Set<User> following) {
		this.following = following;
	}

	@JsonIgnore
	public Set<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}

	@JsonIgnore
	public Set<Tweet> getLikedTweets() {
		return likedTweets;
	}

	public void setLikedTweets(Set<Tweet> likedTweets) {
		this.likedTweets = likedTweets;
	}

	@JsonIgnore
	public Set<Tweet> getMentions() {
		return mentions;
	}

	public void setMentions(Set<Tweet> mentions) {
		this.mentions = mentions;
	}
	
	
}
