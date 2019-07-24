package com.dev.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dev.rest.data.User;
import com.dev.rest.data.repository.UserRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {

	private final UserRepository userRepository;

	@Autowired
	public DatabaseLoader(UserRepository repository) {
		this.userRepository = repository;
	}

	@Override
	public void run(String... strings) throws Exception {
		if(userRepository.findByUserId("rchani") == null) {
			userRepository.save(new User("rchani", "kakao123", "munjeong"));
		}
	}
}