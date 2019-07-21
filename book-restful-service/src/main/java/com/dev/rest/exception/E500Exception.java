package com.dev.rest.exception;

import org.springframework.http.HttpStatus;

/**
 * 500 서버 오류 발생
 * @author gomunjeong
 *
 */
@SuppressWarnings("serial")
public class E500Exception extends EcodeException implements RestAPIException {
	
	public E500Exception(String message){
		super(message);
	}
	
	public E500Exception(){
		super();
	}
	
	@Override
    public HttpStatus getHttpStatus() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
	
	
}
