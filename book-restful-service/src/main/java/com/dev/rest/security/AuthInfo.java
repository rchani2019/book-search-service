package com.dev.rest.security;


import java.util.Collection;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.dev.rest.data.User;

/**
 * 인증 정보 및 사용자 정보 저장 
 * @author gomunjeong
 *
 */
@SuppressWarnings("serial")
public class AuthInfo extends UsernamePasswordAuthenticationToken {
	private User user;
	
	public AuthInfo(Object principal, Object credentials) {
        super(principal, credentials);
    }
	
	public AuthInfo(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		User newUser = new User();
		if(user != null) {
		try {
			BeanUtils.copyProperties(user, newUser);
		} catch (BeansException e) {
			e.printStackTrace();
		}
		this.user = newUser;
		} else {
			this.user = null;
		}
	}
}
