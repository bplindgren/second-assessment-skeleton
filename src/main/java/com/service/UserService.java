package com.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Credentials;
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
		
		String username = newUser.getCredentials().getUsername();
		Date date = new Date();

		newUser.setUsername(username);
		newUser.setActive(true);
		newUser.setJoined(date.getTime());
		
		return userRepository.saveAndFlush(newUser);
		
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
    	userToFollow.getFollows().add(user);
    	
    	userRepository.saveAndFlush(user);
    	userRepository.saveAndFlush(userToFollow);
    	
    }
	
	@Transactional
	public void deleteFollowing(String username, Credentials credentials) throws Exception {
		User user = this.findByUsername(username);
		User userToUnfollow = this.findByUsername(credentials.getUsername());
		
		user.getFollowers().remove(userToUnfollow);
		userToUnfollow.getFollows().remove(user);
		
		userRepository.saveAndFlush(user);
		userRepository.saveAndFlush(userToUnfollow);
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getFollowers(String username) throws Exception {
		return (List<User>) findByUsername(username).getFollowers();
	}

}
















