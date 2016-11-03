package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.UserService;

@RestController
@RequestMapping("users")
public class UsersController {
	
	private UserService userService;

	public UsersController(UserService userService) {
		this.setUserService(userService);
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	
//	@GetMapping
//	public List<User> getAll() {
//		return userService.getAll();
//	}

}
