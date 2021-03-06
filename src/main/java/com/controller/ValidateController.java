package com.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.User;

@RestController
@RequestMapping("validate")
public class ValidateController {
	
	private TagsController tagsController;
	private UsersController usersController;
	
	public ValidateController(TagsController tagsController, UsersController usersController) {
		this.tagsController = tagsController;
		this.usersController = usersController;
	}

	@GetMapping("tag/exists/{label}")
	public boolean tagExists(@PathVariable String label) {
		return (this.tagsController.findByLabel(label) != null) ? true : false;
	}
	
	// Active == true
	@GetMapping("username/exists/@{username}")
	public boolean usernameExists(@PathVariable String username) throws Exception {
		User user = this.usersController.findByUsername(username);
		return (user != null && (user.isActive() == true)) ? true : false;
	}
	
	// Is the username available?
	@GetMapping("username/available/@{username}")
	public boolean usernameAvailable(@PathVariable String username) throws Exception {
		return (this.usersController.findByUsername(username) == null) ? true: false;
	}
}
