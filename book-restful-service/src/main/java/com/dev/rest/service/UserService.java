package com.dev.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dev.rest.data.User;
import com.dev.rest.data.repository.UserRepository;
import com.dev.rest.message.UserJoinRequest;

@Component
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	public void join(UserJoinRequest request) {
		User user = new User(request.getUserId(), request.getPassword(), request.getName());
		userRepository.save(user);
		
	}
	
	public List<User> getUsers(){
		return userRepository.findAll();
	}
}
