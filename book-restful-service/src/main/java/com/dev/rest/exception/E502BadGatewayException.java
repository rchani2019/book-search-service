package com.dev.rest.exception;

import org.springframework.http.HttpStatus;

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
