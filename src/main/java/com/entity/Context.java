package com.entity;

import java.util.List;

public class Context {
	
	private Tweet tweet;
	private List<Tweet> before;
	private List<Tweet> after;
	
	public Context() {
	}

	public Context(Tweet tweet, List<Tweet> before, List<Tweet> after) {
		this.tweet = tweet;
		this.before = before;
		this.after = after;
	}

	public Tweet getTweet() {
		return tweet;
	}

	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}

	public List<Tweet> getBefore() {
		return before;
	}

	public void setBefore(List<Tweet> before) {
		this.before = before;
	}

	public List<Tweet> getAfter() {
		return after;
	}

	public void setAfter(List<Tweet> after) {
		this.after = after;
	}

}
