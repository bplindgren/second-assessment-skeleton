package com.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Credentials;
import com.entity.User;
import com.service.UserService;

@RestController
@RequestMapping("users")
public class UsersController {
	
	private UserService userService;

	public UsersController(UserService userService) {
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
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
    public User deleteUser(@PathVariable String username, @RequestBody Credentials credentials) {
    	return userService.deleteUser(username, credentials);
    }

}
