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
		user.setUsername(updatedUser.getUsername());
		user.setCredentials(updatedUser.getCredentials());
		user.setProfile(updatedUser.getProfile());
		return userRepository.save(user);
	}
	
	@Transactional
	public User deleteUser(String username, Credentials credentials) {
    	User user = userRepository.findByUsername(username);
    	
    	if (user.getCredentials().getPassword().equals(credentials.getPassword())) {
    		user.setActive(false);
    		userRepository.save(user);
    		return user;
    	} 
    	return user;
	}
	
	@Transactional
	public User createFollowing(String username, Credentials credentials) throws Exception {
    	User user = this.findByUsername(username);
    	System.out.println(credentials.getUsername());
    	User userToFollow = this.findByUsername(credentials.getUsername());
    	
    	System.out.println(user);
    	System.out.println(userToFollow);
    	
    	user.getFollowers().add(userToFollow);
    	userToFollow.getFollows().add(user);
    	
    	userRepository.saveAndFlush(userToFollow);
    	return userRepository.saveAndFlush(user);
    	
    }

}
