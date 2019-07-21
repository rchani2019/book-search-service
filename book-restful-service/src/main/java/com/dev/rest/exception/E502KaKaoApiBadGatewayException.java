package com.dev.rest.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class E502KaKaoApiBadGatewayException extends EcodeException implements RestAPIException {

    public E502KaKaoApiBadGatewayException() {
        super();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_GATEWAY;
    }

}
