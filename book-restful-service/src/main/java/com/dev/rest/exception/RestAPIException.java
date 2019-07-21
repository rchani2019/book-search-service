package com.dev.rest.exception;

import org.springframework.http.HttpStatus;

public interface RestAPIException {
    HttpStatus getHttpStatus();
}
