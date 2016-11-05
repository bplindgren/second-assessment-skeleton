package com.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Credentials;
import com.entity.Tweet;
import com.entity.TweetRequest;
import com.entity.User;
import com.repository.TweetRepository;
import com.repository.UserRepository;

@Service
public class TweetService {
	
	@Autowired
	private TweetRepository tweetRepo;
	private UserRepository userRepo;

	public TweetService(TweetRepository tweetRepo, UserRepository userRepo) {
		this.tweetRepo = tweetRepo;
		this.userRepo = userRepo;
	}
	
	public TweetRepository getTweetRepo() {
		return tweetRepo;
	}

	public void setTweetRepo(TweetRepository tweetRepo) {
		this.tweetRepo = tweetRepo;
	}

	public List<Tweet> getTweets() {
		return tweetRepo.findAll();
	}
	
	public Tweet createTweet(TweetRequest tweet) {
		Credentials credentials = tweet.getCredentials();
		User author = userRepo.findByUsername(credentials.getUsername());
				
		if (author != null) {
			Date date = new Date();
			
			Tweet newTweet = new Tweet();
			newTweet.setAuthor(author);
			newTweet.setContent(tweet.getContent());
			newTweet.setPosted(date.getTime());
			newTweet.setActive(true);
	
			return tweetRepo.saveAndFlush(newTweet);
		} else {
			return null;
		}
	}
	
	public Tweet findById(long id) {
		return tweetRepo.findOne(id);
	}
	
	public Tweet deleteTweet(long id, Credentials credentials) throws Exception {
		Tweet tweet = findById(id);
		if (tweet.getAuthor().getCredentials().getPassword().equals(credentials.getPassword())) {
			tweet.setActive(false);
			return tweetRepo.saveAndFlush(tweet);
		} else {
			return null;
		}
	}
}
