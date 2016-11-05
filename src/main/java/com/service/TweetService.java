package com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Credentials;
import com.entity.Tag;
import com.entity.Tweet;
import com.entity.TweetRequest;
import com.entity.User;
import com.repository.TagRepository;
import com.repository.TweetRepository;
import com.repository.UserRepository;

@Service
public class TweetService {
	
	@Autowired
	private TweetRepository tweetRepo;
	private UserRepository userRepo;
	private TagRepository tagRepo;

	public TweetService(TweetRepository tweetRepo, UserRepository userRepo, TagRepository tagRepo) {
		this.tweetRepo = tweetRepo;
		this.userRepo = userRepo;
		this.tagRepo = tagRepo;
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
			
			tweetRepo.saveAndFlush(newTweet);
			
			createTags(tweet.getContent(), newTweet.getId());
//			createMentions(tweet.getContent());
	
			return newTweet;
		} else {
			return null;
		}
	}
	
	public Tweet findByIdAndActiveTrue(long id) {
		return tweetRepo.findOne(id);
	}
	
	public Tweet deleteTweet(long id, Credentials credentials) throws Exception {
		Tweet tweet = findByIdAndActiveTrue(id);
		if (tweet.getAuthor().getCredentials().getPassword().equals(credentials.getPassword())) {
			tweet.setActive(false);
			return tweetRepo.saveAndFlush(tweet);
		} else {
			return null;
		}
	}
	
	public Tweet createLike(long id, Credentials credentials) {
		Tweet tweet = findByIdAndActiveTrue(id);
		User user = userRepo.findByCredentialsUsernameAndCredentialsPassword(credentials.getUsername(), credentials.getPassword());
		
		if (tweet != null && user != null) {
			tweet.getLikers().add(user);
			user.getLikedTweets().add(tweet);
			
			tweetRepo.saveAndFlush(tweet);
			userRepo.saveAndFlush(user);
			
			return tweet;
		} else {
			return null;
		}
	}
	
	public Set<Tag> getTags(long id) {
		Tweet tweet = tweetRepo.findByIdAndActiveTrue(id);
		if (tweet != null) {
			return tweet.getTags();
		} else {
			return null;
		}
	}
	
	public Set<User> getLikers(long id) {
		Tweet tweet = tweetRepo.findByIdAndActiveTrue(id);
		if (tweet != null) {
			return tweet.getLikers();
		} else {
			return null;
		}
	}
	
	public void createTags(String content, long id) {
		Pattern tagPattern = Pattern.compile("#(\\w+)");
		Matcher matcher = tagPattern.matcher(content);
		List<String> tags = new ArrayList<String>();
		while (matcher.find()) {
			tags.add(matcher.group(1));
		}
		
		for (String tag : tags) {
			Tag t = tagRepo.findByLabel(tag);
			Date date = new Date();
			Tweet tweet = tweetRepo.findByIdAndActiveTrue(id);
			if (t == null) {
				Set<Tweet> tweetsList = new HashSet<Tweet>();
				Tag newTag = new Tag(tag.toLowerCase(), date.getTime(), date.getTime(), tweetsList);
				newTag.getTweets().add(tweet);
				tagRepo.saveAndFlush(newTag);
			} else {
				t.setLastUsed(date.getTime());
				t.getTweets().add(tweet);
				tagRepo.saveAndFlush(t);
			}
		}
	}
	
}












