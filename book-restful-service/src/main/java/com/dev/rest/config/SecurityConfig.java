package com.dev.rest.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.dev.rest.security.ExtendedAuthenticationEntryPoint;
import com.dev.rest.security.ExtendedBasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;
	
	private String realmName = "BookAPI";
	
	/*
	 * Security Excepton 처리 
	 */
	@Bean
    public ExtendedAuthenticationEntryPoint extendedAuthenticationEntryPoint() throws Exception {
        ExtendedAuthenticationEntryPoint extendedBasicAuthenticationEntryPoint = new ExtendedAuthenticationEntryPoint();
        extendedBasicAuthenticationEntryPoint.setRealmName(realmName);
        return extendedBasicAuthenticationEntryPoint;
    }

    @Bean
    public ExceptionTranslationFilter exceptionTranslationFilter() throws Exception {
        ExceptionTranslationFilter exceptionTranslationFilter = new ExceptionTranslationFilter(extendedAuthenticationEntryPoint());
        exceptionTranslationFilter.setAccessDeniedHandler(extendedAuthenticationEntryPoint());
        return exceptionTranslationFilter;
    }
    
    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        List<AuthenticationProvider> providerList = new ArrayList<AuthenticationProvider>();
        providerList.add(daoAuthenticationProvider);
        ProviderManager providerManager = new ProviderManager(providerList);
        // Spring security가 인증을 마치면 User Bean에 비밀번호를 기본적으로 지우려고 하는데, Cache내의 비밀번호도 지우는 증상이 있어서, 일단 false
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }
    
    /**
     * ExtendedBasicAuthenticationFilter Bean
     * - 사용자가 Basic Auth를 요청했을 때, 처리하는 Filter이다.
     */
    @Bean
    ExtendedBasicAuthenticationFilter extendedBasicAuthenticationFilter() throws Exception {
        ExtendedBasicAuthenticationFilter extendedBasicAuthenticationFilter = new ExtendedBasicAuthenticationFilter(authenticationManager(), extendedAuthenticationEntryPoint());
        return extendedBasicAuthenticationFilter;
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http
		// Request Matchers
        .authorizeRequests()
        .antMatchers("/api/user/join", "/user/join", "/api/user/join**", "/user/join**", "/db-console/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .addFilterBefore(exceptionTranslationFilter(), LogoutFilter.class)
        .addFilterAfter(extendedBasicAuthenticationFilter(), ExceptionTranslationFilter.class)
		.csrf().disable()
        .requestCache().disable()
        .anonymous()
        .and()
        .exceptionHandling().disable()
        .headers().cacheControl().disable().httpStrictTransportSecurity().disable()
        .and()
        .logout().logoutSuccessUrl("/login")
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).sessionFixation().none();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
//	    web.ignoring()
//	    .antMatchers("/db-console/*", "/index.html", "/user/join");
		web.ignoring()
		.antMatchers("/api/user/join", "/user/join", "/api/user/join**", "/user/join**", "/db-console/**");
	}
}
