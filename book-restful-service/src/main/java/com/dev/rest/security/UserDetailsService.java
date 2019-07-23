package com.dev.rest.security;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.dev.rest.data.User;
import com.dev.rest.data.repository.UserRepository;

@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	@Autowired
	UserRepository userRepository;

	Logger log = LoggerFactory.getLogger(getClass());
	protected final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserId(username);
		log.debug("{}", user);
		if (user == null) {
			throw new UsernameNotFoundException(
					messages.getMessage("JdbcDaoImpl.notFound", new Object[] { username }, "Username {0} not found"));
		}
		UserInfo authInfo = new UserInfo(user.getUserId(), user.getPassword(), Collections.singleton(createAuthority(user)));
		authInfo.setUser(user);
		return authInfo;
	}

	private GrantedAuthority createAuthority(User user) {
        return new SimpleGrantedAuthority("ROLE_USER");
    }

}
