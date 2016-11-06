package com.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Credentials;
import com.entity.Tweet;
import com.entity.User;
import com.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public UserService(UserRepository userRepo) {
		this.userRepository = userRepo;
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public User findByUsername(String username) throws Exception {
		return userRepository.findByUsername(username);
	}
	
	@Transactional
	public User createUser(User newUser) throws Exception {
		// Get the credentials for the new user, see if they are an inactive user
		Credentials credentials = newUser.getCredentials();
		
		// If the user exists in the database but active = false
//		if (userRepository.findByCredentialsUsernameAndCredentialsPasswordAndActiveFalse(
//				credentials.getUsername(), credentials.getUsername()) != null) {
//			User user = userRepository.findByCredentialsUsernameAndCredentialsPassword(
//					credentials.getUsername(), credentials.getUsername());
//			user.setActive(true);
//			userRepository.save(user);
//			return user;
//		} else { // Create a new user
			String username = newUser.getCredentials().getUsername();
			
			Date date = new Date();
	
			newUser.setUsername(username);
			newUser.setActive(true);
			newUser.setJoined(date.getTime());
			
			return userRepository.saveAndFlush(newUser);
//		}
		
	}
	
	@Transactional
	public User updateUser(String username, User updatedUser) throws Exception {
		User user = findByUsername(username);
		
		user.setUsername(updatedUser.getCredentials().getUsername());
		user.setCredentials(updatedUser.getCredentials());
		user.setProfile(updatedUser.getProfile());
		return userRepository.saveAndFlush(user);
	}
	
	public User deleteUser(String username, Credentials credentials) throws Exception {
    	User user = findByUsername(username);
    	
    	if (user.getCredentials().getPassword().equals(credentials.getPassword())) {
    		user.setActive(false);
    		userRepository.saveAndFlush(user);
    		return user;
    	} 
    	return user;
	}
	
	@Transactional
	public void createFollowing(String username, Credentials credentials) throws Exception {
    	User user = this.findByUsername(username);
    	User userToFollow = this.findByUsername(credentials.getUsername());
    	
    	user.getFollowers().add(userToFollow);
    	userToFollow.getFollowing().add(user);
    	
    	userRepository.saveAndFlush(user);
    	userRepository.saveAndFlush(userToFollow);
    	
    }
	
	@Transactional
	public void deleteFollowing(String username, Credentials credentials) throws Exception {
		User user = this.findByUsername(username);
		User userToUnfollow = this.findByUsername(credentials.getUsername());
		
		user.getFollowers().remove(userToUnfollow);
		userToUnfollow.getFollowing().remove(user);
		
		userRepository.saveAndFlush(user);
		userRepository.saveAndFlush(userToUnfollow);
	}
	
	public List<Tweet> getFeed(String username) throws Exception {
		// Create a List to store the user's tweets and followings tweets
		List<Tweet> feed = new ArrayList<Tweet>();
		
		// Get the user's tweets and add them to the feed
		List<Tweet> userTweets = getTweets(username);
		for (Tweet tweet : userTweets) {
			feed.add(tweet);
		}
		
		// Get the users that the user follows
		Set<User> followings = getFollowing(username);
		for (User user : followings) {
			List<Tweet> followerTweets = getTweets(user.getUsername());
			for (Tweet tweet : followerTweets) {
				feed.add(tweet);
			}
		}
		
		return feed;		
	}
	
	public List<Tweet> getTweets(String username) throws Exception {
		return findByUsername(username).getTweets();
	}
	
	public Set<Tweet> getMentions(String username) throws Exception {
		return findByUsername(username).getMentions();
	}
	
	public Set<User> getFollowing(String username) throws Exception {
		return findByUsername(username).getFollowing();
	}
	
	public Set<User> getFollowers(String username) throws Exception {
		return findByUsername(username).getFollowers();
	}

}
















