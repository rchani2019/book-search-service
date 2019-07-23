package com.dev.rest.exception;

import org.springframework.http.HttpStatus;
/**
 * 사용중인 UserID 
 * @author gomunjeong
 *
 */
@SuppressWarnings("serial")
public class E409UserIdDuplicateException extends EcodeException implements RestAPIException {

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.CONFLICT;
	}

}
