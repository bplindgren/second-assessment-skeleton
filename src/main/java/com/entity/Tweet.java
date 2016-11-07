package com.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tweet")
public class Tweet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(nullable=false)
	private User author;
	
	private long posted;
	
	private String content;
	
	@ManyToOne
	private Tweet inReplyTo;
	
	@ManyToOne
	private Tweet repostOf;
	
	private boolean active;
	
	@ManyToMany(mappedBy="tweets")
	private Set<Tag> tags;

	@ManyToMany(mappedBy="likedTweets")
	private Set<User> likers;
	
	@ManyToMany(mappedBy="mentions")
	private Set<User> mentionedUsers;
	
	@OneToMany(mappedBy="inReplyTo")
	private List<Tweet> replies;
	
	@OneToMany(mappedBy="repostOf")
	private List<Tweet> reposts;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPosted() {
		return posted;
	}

	public void setPosted(long l) {
		this.posted = l;
	}

	@JsonIgnore
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

	@JsonIgnore
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
	
	@JsonIgnore
	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	
	@JsonIgnore
	public Set<User> getLikers() {
		return likers;
	}

	public void setLikers(Set<User> likers) {
		this.likers = likers;
	}

	@JsonIgnore
	public Set<User> getMentionedUsers() {
		return mentionedUsers;
	}

	public void setMentionedUsers(Set<User> mentionedUsers) {
		this.mentionedUsers = mentionedUsers;
	}

	@JsonIgnore
	public List<Tweet> getReplies() {
		return replies;
	}

	public void setReplies(List<Tweet> replies) {
		this.replies = replies;
	}

	@JsonIgnore
	public List<Tweet> getReposts() {
		return reposts;
	}

	public void setReposts(List<Tweet> reposts) {
		this.reposts = reposts;
	}
	
}
