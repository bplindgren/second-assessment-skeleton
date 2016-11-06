package com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Context;
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
			createMentions(tweet.getContent(), newTweet.getId());
	
			return newTweet;
		} else {
			return null;
		}
	}
	
	public Tweet findTweet(long id) {
		Tweet tweet = tweetRepo.findByIdAndActiveTrue(id);
		if (tweet == null) {
			throw new EmptyStackException();
		} else {
			return tweet;
		}
	}
	
	public Tweet deleteTweet(long id, Credentials credentials) throws Exception {
		Tweet tweet = findTweet(id);
		tweet.getAuthor().getCredentials().getPassword().equals(credentials.getPassword());
		tweet.setActive(false);
		return tweetRepo.saveAndFlush(tweet);

	}
	
	public Tweet createLike(long id, Credentials credentials) {
		Tweet tweet = findTweet(id);
		User user = userRepo.findByCredentialsUsernameAndCredentialsPassword(credentials.getUsername(), credentials.getPassword());
		
		if (user != null) {
			tweet.getLikers().add(user);
			user.getLikedTweets().add(tweet);
			
			tweetRepo.saveAndFlush(tweet);
			userRepo.saveAndFlush(user);
			
			return tweet;
		} else {
			return null;
		}
	}
	
	public Tweet createReply(long id, TweetRequest newTweet) {
		// Get the tweet being replied to
		Tweet tweetRepliedTo = findTweet(id);
		
		// Create the new tweet
		Tweet replyTweet = createTweet(newTweet);
		
		// Set the 'inReplyTo' value of the replyTweet to 'tweetRepliedTo'
		replyTweet.setInReplyTo(tweetRepliedTo);
		
		tweetRepo.saveAndFlush(replyTweet);
		
		return replyTweet;
	}
	
	public Tweet createRepost(long id, Credentials credentials) {
		// Get the tweet being reposted
		Tweet tweetReposted = findTweet(id);
		
		// Create a tweet request object and create the new tweet repost
		String content = tweetReposted.getContent();
		TweetRequest tweetRequest = new TweetRequest(credentials, content);
		Tweet repost = createTweet(tweetRequest);
		
		// Set the 'repostOf' value to the repost to 'tweetReposted'
		repost.setRepostOf(tweetReposted);
		
		tweetRepo.saveAndFlush(repost);
		
		return repost;
	}
	
	public Set<Tag> getTags(long id) {
		Tweet tweet = findTweet(id);
		return tweet.getTags();
	}
	
	public Set<User> getLikers(long id) {
		Tweet tweet = findTweet(id);
		return tweet.getLikers();
	}
	
	public Context getContext(long id) {
		Context context = new Context();
		Tweet tweet = tweetRepo.findByIdAndActiveTrue(id);
		if (tweet != null) {
			context.setTweet(tweet);
			
			// Get the before chain
			List<Tweet> before = new ArrayList<Tweet>();
			while (tweet.getInReplyTo() != null) {
				Tweet tweetRepliedTo = tweet.getInReplyTo();
				if (tweetRepliedTo.isActive()) {
					before.add(tweetRepliedTo);					
				}
				tweet = tweetRepliedTo;
			}
			
			context.setBefore(before);
			
			// Get the after chain
			List<Tweet> after = new ArrayList<Tweet>();
			for (Tweet t : tweet.getReplies()) {
				if (t.isActive()) {
					after.add(t);
				}
			}
			
			context.setAfter(after);
			
			return context;
		} else {
			return null;
		}
	}
	
	public List<Tweet> getReplies(long id) {
		Tweet tweet = findTweet(id);
		return tweet.getReplies();
	}
	
	public List<Tweet> getReposts(long id) {
		Tweet tweet = findTweet(id);
		return tweet.getReposts();
	}
	
	public Set<User> getMentions(long id) {
		Tweet tweet = findTweet(id);
		return tweet.getMentionedUsers();
	}
	
	
	// ========================= //
	
	public void createTags(String content, long id) {
		// set up pattern and empty list
		Pattern tagPattern = Pattern.compile("#(\\w+)");
		Matcher matcher = tagPattern.matcher(content);
		List<String> tags = new ArrayList<String>();
		
		// add tags to the list
		while (matcher.find()) {
			tags.add(matcher.group(1));
		}
		
		// for each tag...
		for (String tag : tags) {
			Tag t = tagRepo.findByLabel(tag);
			Date date = new Date();
			Tweet tweet = tweetRepo.findByIdAndActiveTrue(id);
			// if the tag is not already in the list, downcase it, add the tweet to its list of tweets, and save
			if (t == null) {
				Set<Tweet> tweetsList = new HashSet<Tweet>();
				Tag newTag = new Tag(tag.toLowerCase(), date.getTime(), date.getTime(), tweetsList);
				newTag.getTweets().add(tweet);
				tagRepo.saveAndFlush(newTag);
			} else { // otherwise, update lastUsed and save 
				t.setLastUsed(date.getTime());
				t.getTweets().add(tweet);
				tagRepo.saveAndFlush(t);
			}
		}
	}
	
	public void createMentions(String content, long id) {
		// create the pattern and empty list
		Pattern tagPattern = Pattern.compile("@(\\w+)");
		Matcher matcher = tagPattern.matcher(content);
		List<String> mentions = new ArrayList<String>();
		
		// add mentions to the list
		while (matcher.find()) {
			mentions.add(matcher.group(1));
		}
		
		// for each mention, get tweet and user, save necessary data
		for (String mention : mentions) {
			Tweet tweet = tweetRepo.getOne(id);
			User user = userRepo.findByUsername(mention);
			user.getMentions().add(tweet);
			userRepo.saveAndFlush(user);
		}
	}
	
}












