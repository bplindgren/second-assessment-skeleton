package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controller.ValidateController;
import com.entity.User;
import com.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	private ValidateController validateController;

	public UserService(UserRepository userRepo, ValidateController validateController) {
		this.userRepository = userRepo;
		this.validateController = validateController;
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
	
	public User createUser(User newUser) throws Exception {
		String username = newUser.getCredentials().getUsername();
		if (this.validateController.usernameAvailable(username) == false) {
			return userRepository.saveAndFlush(newUser);
		} else if (this.validateController.usernameExists(username) == true) {
			// update the user's active field to true
		} else {
			return newUser;
		}
	}
	
}
