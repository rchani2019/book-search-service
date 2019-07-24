package com.dev.rest.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dev.rest.exception.E500Exception;
import com.dev.rest.exception.EcodeException;
import com.dev.rest.exception.RestAPIException;
import com.dev.rest.service.MessageService;
/**
 * 예외 처리
 * @author gomunjeong
 *
 */
@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    static Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @Autowired
    MessageService messageService;

    @ExceptionHandler(value = { EcodeException.class })
    @ResponseBody
    protected ResponseEntity<Object> hendleIrisExceptions(final RuntimeException exception, HttpServletResponse response) {
        logger.info("RestAPI Exception {}", exception.getClass().getName());
        final HttpHeaders headers = new HttpHeaders();

        if (!(exception instanceof RestAPIException)) {
            return new ResponseEntity<Object>("handleOpenAPIException Class Cast Error", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        RestAPIException restApiException = (RestAPIException) exception;
        String errorMessage = messageService.getErrorMessage(restApiException);

        String errorDescription;
        if (exception instanceof E500Exception && !StringUtils.isEmpty(exception.getMessage())) {
            errorDescription = exception.getMessage();
        } else {
            errorDescription = messageService.getErrorDescription(restApiException);

        }

        if (errorMessage == null || errorDescription == null) {
            return new ResponseEntity<Object>("handleRestApisExceptions Error Properties Not Found", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            headers.add("X-Status-Message", URLEncoder.encode(errorMessage, "UTF-8").replace("+", "%20"));
        } catch (UnsupportedEncodingException e) {
            return new ResponseEntity<Object>("handleRestApisExceptions UrlEncoder Error", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        headers.setContentType(new MediaType("text", "plain", Charset.defaultCharset()));
        logger.debug("restApi exception handler {} {}", restApiException.getHttpStatus(), exception.getMessage() == null ? "" : exception.getMessage(), exception);
        return new ResponseEntity<Object>(errorDescription, headers, restApiException.getHttpStatus());
    }

    @ExceptionHandler(value = { PersistenceException.class, NullPointerException.class })
    protected ResponseEntity<Object> handleRuntimeException(final Exception exception, HttpServletResponse response) {
        logger.error("PersistenceException or NullPointerException!", exception);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "plain", Charset.defaultCharset()));
        return new ResponseEntity<Object>(ExceptionUtils.getStackTrace(exception), headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Framework exception!", ex);
        try {
            headers.add("X-Status-Message", URLEncoder.encode(ex.getMessage(), "UTF-8").replace("+", "%20"));
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException", e);
        }
        if (body == null) {
            body = ex.getMessage();
        }
        headers.setContentType(new MediaType("text", "plain", Charset.defaultCharset()));
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn("Framework exception, MethodArgumentNotValid!", ex);
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            if (headers.get("X-Status-Message") == null) {
                try {
                    headers.add("X-Status-Message", URLEncoder.encode(fieldError.getDefaultMessage(), "UTF-8").replace("+", "%20"));
                } catch (UnsupportedEncodingException e) {
                    logger.error("UnsupportedEncodingException", e);
                }
            }
            stringBuilder.append("Argument:");
            stringBuilder.append(fieldError.getField());
            stringBuilder.append(",ErrorMessage:");
            stringBuilder.append(fieldError.getDefaultMessage());
            stringBuilder.append(System.lineSeparator());
        }
        headers.setContentType(new MediaType("text", "plain", Charset.defaultCharset()));
        String errorDescription = stringBuilder.toString();
        return super.handleExceptionInternal(ex, errorDescription, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn("Framework exception, BindException!", ex);
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            if (headers.get("X-Status-Message") == null) {
                try {
                    headers.add("X-Status-Message", URLEncoder.encode(fieldError.getDefaultMessage(), "UTF-8").replace("+", "%20"));
                } catch (UnsupportedEncodingException e) {
                    logger.error("UnsupportedEncodingException", e);
                }
            }
            stringBuilder.append("Argument:");
            stringBuilder.append(fieldError.getField());
            stringBuilder.append(",ErrorMessage:");
            stringBuilder.append(fieldError.getDefaultMessage());
            stringBuilder.append(System.lineSeparator());
        }
        headers.setContentType(new MediaType("text", "plain", Charset.defaultCharset()));
        String errorDescription = stringBuilder.toString();
        return super.handleExceptionInternal(ex, errorDescription, headers, status, request);
    }
}
