package com.dev.rest.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.dev.rest.data.User;

@SuppressWarnings("serial")
public class UserInfo extends org.springframework.security.core.userdetails.User {
	User user;
	
    public UserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
}
