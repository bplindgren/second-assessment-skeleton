package com.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		username = '@' + username;
		return userService.findByUsername(username);
	}
	

}

//{
//"credentials":{"username":"@mikesmith","password":"1234"},
//"profile":{"id":2,"firstName":"mike","lastName":"smith","email":"mikesmith@gmail.com","phone":"3128496320"}
//}
