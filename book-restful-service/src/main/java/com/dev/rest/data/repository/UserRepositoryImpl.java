package com.dev.rest.data.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;

import com.dev.rest.data.User;

public class UserRepositoryImpl implements UserRepositoryExtension<User> {
	
	@PersistenceContext(unitName="entityManagerFactory")
    private EntityManager entityManager;


//	@Autowired
//	private PasswordEncoder passwordEncoder;

	@Override
	public <S extends User> S save(S user) {
		if (StringUtils.isNotEmpty(user.getPassword())) {
//			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		entityManager.persist(user);
		return user;
	}

}
