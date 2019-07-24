package com.dev.rest.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.rest.data.User;

public interface UserRepository extends UserRepositoryExtension<User>, JpaRepository<User, Integer> {

	public User findByUserId(String userId);
}
