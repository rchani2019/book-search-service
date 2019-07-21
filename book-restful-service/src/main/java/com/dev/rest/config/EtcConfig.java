package com.dev.rest.config;

import java.util.TimeZone;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class EtcConfig {
	@Bean
	public ObjectMapper mapper() {
		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.setTimeZone(TimeZone.getDefault());
		return objectMapper;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		// @formatter:off
		RestTemplate restTemplate = restTemplateBuilder.setConnectTimeout(3000).setReadTimeout(3000)
				.errorHandler(new HttpErrorHandler()).build();
		return restTemplate;
		// @formatter:on
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:message");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public MessageSourceAccessor messageSourceAccessor() {
		MessageSourceAccessor messageSourceAccessor = new MessageSourceAccessor(messageSource());
		return messageSourceAccessor;
	}
}
