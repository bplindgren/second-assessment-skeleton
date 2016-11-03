package com.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tweet")
public class Tweet {

	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User author;
	
	private Date posted;
	
	@Column(nullable = true)
	private String content;
	
	@OneToOne
	private Tweet inReplyTo;
	
	@OneToOne
	private Tweet repostOf;
	
	private boolean active;
	
	@ManyToMany(targetEntity = Tag.class, cascade = { CascadeType.ALL })
	@JoinTable(name = "tags_tweets", 
				joinColumns = { @JoinColumn(name = "tweet_id") }, 
				inverseJoinColumns = { @JoinColumn(name = "tag_id") })
	private Set<Tag> tags;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public Date getPosted() {
		return posted;
	}
	
	public void setPosted(Date posted) {
		this.posted = posted;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Tweet getInReplyTo() {
		return inReplyTo;
	}

	public void setInReplyTo(Tweet inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	public Tweet getRepostOf() {
		return repostOf;
	}

	public void setRepostOf(Tweet repostOf) {
		this.repostOf = repostOf;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
