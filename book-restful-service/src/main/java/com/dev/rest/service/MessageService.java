package com.dev.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import com.dev.rest.exception.RestAPIException;
/**
 * 예외 메시지를 맵핑한다.
 * @author gomunjeong
 *
 */
@Component
public class MessageService {

	@Autowired
	MessageSourceAccessor messageSourceAccessor;

	private String getClassNameWithoutPackage(String className) {
		return className.substring(className.lastIndexOf(".") + 1);
	}

	public String getStringValue(String key) {
		return messageSourceAccessor.getMessage(key);
	}

	public String getErrorMessage(RestAPIException exception) {
		String className = getClassNameWithoutPackage(exception.getClass().getName());
		return getStringValue(className + ".Message");
	}

	public String getErrorDescription(RestAPIException exception) {
		String className = getClassNameWithoutPackage(exception.getClass().getName());
		return getStringValue(className + ".Description");
	}
}
