package com.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
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
	private String firstUsed;
	private String lastUsed;
	
	@ManyToMany(targetEntity = Tweet.class, cascade = { CascadeType.ALL })
	@JoinTable(name = "tags_tweets", 
				joinColumns = { @JoinColumn(name = "tag_id") }, 
				inverseJoinColumns = { @JoinColumn(name = "tweet_id") })
	private Set<Tweet> tweets;

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

	public String getFirstUsed() {
		return firstUsed;
	}

	public void setFirstUsed(String firstUsed) {
		this.firstUsed = firstUsed;
	}

	public String getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(String lastUsed) {
		this.lastUsed = lastUsed;
	}

}
