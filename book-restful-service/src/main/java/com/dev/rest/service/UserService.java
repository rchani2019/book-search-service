package com.dev.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.rest.data.User;
import com.dev.rest.data.repository.UserRepository;
import com.dev.rest.message.UserJoinRequest;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	public void join(UserJoinRequest request) {
		User user = new User(request.getUserId(), request.getPassword(), request.getName());
		userRepository.save(user);
	}
	
	public Boolean isExistUserId(String userId) {
		return userRepository.findByUserId(userId) != null ? true : false;
	}
	
	public List<User> getUsers(){
		return userRepository.findAll();
	}
}
