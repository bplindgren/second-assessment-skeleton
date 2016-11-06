package com.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Credentials;
import com.entity.Tweet;
import com.entity.User;
import com.service.UserService;

@RestController
@RequestMapping("users")
public class UsersController {
	
	private UserService userService;

	public UsersController(UserService userService) {
		this.userService = userService;
	}

//	public UserService getUserService() {
//		return userService;
//	}
//
//	public void setUserService(UserService userService) {
//		this.userService = userService;
//	}
	
	@GetMapping
	public List<User> findAll() {
		return userService.findAll();
	}
	
	@PostMapping
	public User createUser(@RequestBody User newUser) throws Exception {
		return userService.createUser(newUser);
	}

	@GetMapping("/@{username}")
	public User findByUsername(@PathVariable String username) throws Exception {
		return userService.findByUsername(username);
	}
	
    @PatchMapping("/@{username}")
    public User updateUser(@PathVariable String username, @RequestBody User updatedUser) throws Exception {
    	return userService.updateUser(username, updatedUser);
    }
    
    @DeleteMapping("/@{username}")
    public User deleteUser(@PathVariable String username, @RequestBody Credentials credentials) throws Exception {
    	return userService.deleteUser(username, credentials);
    }

    @PostMapping("/@{username}/follow")
    public void createFollowing(@PathVariable String username, @RequestBody Credentials credentials) throws Exception {
    	userService.createFollowing(username, credentials);
    }
    
    @PostMapping("/@{username}/unfollow")
    public void deleteFollowing(@PathVariable String username, @RequestBody Credentials credentials) throws Exception {
    	userService.deleteFollowing(username, credentials);
    }
    
    @GetMapping("/@{username}/feed")
    public List<Tweet> getFeed(@PathVariable String username) throws Exception {
    	List<Tweet> feed = userService.getFeed(username);

    	// Get the tweets in the correct order
    	Comparator<Tweet> comparator = new Comparator<Tweet>() {
    		@Override public int compare(Tweet t1, Tweet t2) {
    			return (int) (t2.getPosted() - t1.getPosted());
    		}
    	};
    	
    	Collections.sort(feed, comparator);

    	return feed;
    }
    
    @GetMapping("/@{username}/tweets")
    public List<Tweet> getTweets(@PathVariable String username) throws Exception {
    	List<Tweet> tweets = userService.getTweets(username);
    	Collections.reverse(tweets);
    	return tweets;
    }
    
    @GetMapping("/@{username}/mentions")
    public Set<Tweet> getMentions(@PathVariable String username) throws Exception {
    	return userService.getMentions(username);
    }
    
    @GetMapping("/@{username}/followers")
    public Set<User> getFollowers(@PathVariable String username) throws Exception {
    	return userService.getFollowers(username);
    }
    
    @GetMapping("/@{username}/following")
    public Set<User> getFollowing(@PathVariable String username) throws Exception {
    	return userService.getFollowing(username);
    }
    
}
