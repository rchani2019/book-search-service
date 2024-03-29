package com.dev.rest.exception;

import org.springframework.http.HttpStatus;
/**
 * 외부 연동 에러 
 * @author gomunjeong
 *
 */
@SuppressWarnings("serial")
public class E502BadGatewayException extends EcodeException implements RestAPIException {

    public E502BadGatewayException() {
        super();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_GATEWAY;
    }

}
