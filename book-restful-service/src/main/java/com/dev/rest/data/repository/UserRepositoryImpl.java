package com.dev.rest.data.repository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;

import com.dev.rest.data.User;

public class UserRepositoryImpl implements UserRepositoryExtension<User> {

	@Override
	public <S extends User> S save(S paramS) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Autowired
//	private PasswordEncoder passwordEncoder;
//
//	@Override
//	public <S extends User> S save(S user) {
//		if (StringUtils.isNotEmpty(user.getPassword())) {
//			user.setPassword(passwordEncoder.encode(user.getPassword()));
//		}
//		return user;
//	}

}
