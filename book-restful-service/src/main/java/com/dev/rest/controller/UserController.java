package com.dev.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dev.rest.message.UserJoinRequest;
import com.dev.rest.data.User;
import com.dev.rest.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(path="/join", method=RequestMethod.POST)
	public void join(@RequestBody(required=true) @Valid UserJoinRequest request) {
		userService.join(request);
	}
	
	@RequestMapping(path="/users", method=RequestMethod.GET)
	@ResponseBody
	public List<User> getUser(){
		return userService.getUsers();
	}
}
