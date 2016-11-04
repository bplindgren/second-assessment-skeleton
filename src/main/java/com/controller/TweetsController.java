package com.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Tweet;
import com.entity.TweetRequest;
import com.service.TweetService;

@RestController
@RequestMapping("tweets")
public class TweetsController {

	private TweetService tweetService;
	public TweetsController(TweetService tweetService) {
		this.tweetService = tweetService;
	}

	public TweetService getTweetService() {
		return tweetService;
	}

	public void setTweetService(TweetService tweetService) {
		this.tweetService = tweetService;
	}
	
	@GetMapping
	public List<Tweet> getTweets() {
		return tweetService.getTweets();
	}
	
	@PostMapping
	public Tweet createTweet(@RequestBody TweetRequest tweet) {
		return tweetService.createTweet(tweet);
	}
	
	@GetMapping("/{id}")
	public Tweet findById(@PathVariable long id) throws Exception {
		return tweetService.findById(id);
	}
		
}
