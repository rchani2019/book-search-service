package com.dev.rest.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dev.rest.exception.E409UserIdDuplicateException;
import com.dev.rest.message.LoginResponse;
import com.dev.rest.message.UserJoinRequest;
import com.dev.rest.security.AuthInfo;
import com.dev.rest.service.UserService;

@RestController
@Transactional
public class UserController {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	UserService userService;

	/**
	 * 회원 가입
	 * 
	 * @param request
	 */
	@RequestMapping(path = "/user/join", method = RequestMethod.POST)
	@ResponseBody
	public void join(@RequestBody(required = true) @Valid UserJoinRequest request) {
		if (userService.isExistUserId(request.getUserId())) {
			log.info("E409UserIdDuplicateException Already UserId Used");
			throw new E409UserIdDuplicateException();
		}
		userService.join(request);
	}

	/**
	 * 로그인
	 * 
	 * @param authInfo
	 * @return
	 */
	@Secured({ "IS_AUTHENTICATED_FULLY" })
	@RequestMapping(path = "/login", method = RequestMethod.GET)
	@ResponseBody
	public LoginResponse login(AuthInfo authInfo) {
		return new LoginResponse(authInfo);
	}
}
