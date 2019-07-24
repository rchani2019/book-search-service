package com.dev.rest.message;

import com.dev.rest.security.AuthInfo;

import lombok.Data;

@Data
public class LoginResponse {
	private Integer userNo;
	private String userId;
	private String userName;
	
	public LoginResponse(AuthInfo authInfo) {
		super();
		this.userNo = authInfo.getUser().getUserNo();
		this.userId = authInfo.getUser().getUserId();
		this.userName = authInfo.getUser().getName();
	}
	
	
}
