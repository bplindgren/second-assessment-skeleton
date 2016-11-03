package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.UserService;

@RestController
@RequestMapping("users")
public class UsersController {
	
	UserService userService;
	
//	@GetMapping
//	public List<User> getAll() {
//		return userService.getAll();
//	}

}
