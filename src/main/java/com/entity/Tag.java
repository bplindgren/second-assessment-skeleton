package com.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tag")
public class Tag {

	@Id
	@GeneratedValue
	private long id;
	private String label;
	private long firstUsed;
	private long lastUsed;
	
	@ManyToMany
	@JoinTable(name = "tags_tweets", 
				joinColumns = { @JoinColumn(name = "tag_id") }, 
				inverseJoinColumns = { @JoinColumn(name = "tweet_id") })
	private Set<Tweet> tweets;

	public Tag() {
	}

	public Tag(String label, long firstUsed, long lastUsed, Set<Tweet> tweets) {
		this.label = label;
		this.firstUsed = firstUsed;
		this.lastUsed = lastUsed;
		this.tweets = tweets;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public long getFirstUsed() {
		return firstUsed;
	}

	public void setFirstUsed(long firstUsed) {
		this.firstUsed = firstUsed;
	}

	public long getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(long lastUsed) {
		this.lastUsed = lastUsed;
	}

	public Set<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(Set<Tweet> tweets) {
		this.tweets = tweets;
	}



}
