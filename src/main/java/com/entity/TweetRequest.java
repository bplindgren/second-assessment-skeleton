package com.entity;

public class TweetRequest {

	private Credentials credentials;
	private String content;
	
	public TweetRequest() {
	}

	public TweetRequest(Credentials credentials, String content) {
		this.credentials = credentials;
		this.content = content;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
