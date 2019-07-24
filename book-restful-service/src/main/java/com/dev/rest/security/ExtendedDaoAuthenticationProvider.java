package com.dev.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ExtendedDaoAuthenticationProvider extends DaoAuthenticationProvider {
	private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void setAuthoritiesMapper(GrantedAuthoritiesMapper authoritiesMapper) {
		super.setAuthoritiesMapper(authoritiesMapper);
		this.authoritiesMapper = authoritiesMapper;
	}

	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
			UserDetails user) {
		UserInfo userInfo = (UserInfo) user;
		AuthInfo authInfo = new AuthInfo(principal, authentication.getCredentials(),
				authoritiesMapper.mapAuthorities(user.getAuthorities()));
		authInfo.setUser(userInfo.getUser());
		authInfo.setDetails(authentication.getDetails());
		return authInfo;
	}

	protected void doAfterPropertiesSet() throws Exception {
		setUserDetailsService(userDetailsService);
		setPasswordEncoder(passwordEncoder);
		super.doAfterPropertiesSet();
	}
}
