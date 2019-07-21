package com.dev.rest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.dev.rest.exception.E502BadGatewayException;
import com.dev.rest.message.KakaoApiRequest;
import com.dev.rest.message.KakaoApiResponse;

import lombok.Setter;

@Component
@ConfigurationProperties(prefix="api.search.kakao")
@Setter
public class KaKaoClient {

	private String url;
	private String apiKey;
	
	@Autowired
	RestTemplate restTemplate;
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	public KakaoApiResponse searchBooksByKeyword(KakaoApiRequest request) {
		KakaoApiResponse responseData = new KakaoApiResponse();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Authorization", apiKey);
			
			UriComponents builder = UriComponentsBuilder.fromHttpUrl(url + "?target=title")
			        .queryParam("query", request.getQuery())
			        .queryParam("sort", request.getSort())
			        .queryParam("page", request.getPage())
			        .queryParam("size", request.getSize())
			        .build(false);
			
			ResponseEntity<KakaoApiResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<String>(headers), KakaoApiResponse.class);
			responseData = response.getBody();
		} catch (RestClientException e) {
			log.error("fail to call kakao api. request=" + request.toString(), e);
			throw new E502BadGatewayException();
		}
		
        return responseData;
	}
	
	public KakaoApiResponse searchBooksByIsbn(KakaoApiRequest request) {
		KakaoApiResponse responseData = new KakaoApiResponse();
		
        try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Authorization", apiKey);
			
			UriComponents builder = UriComponentsBuilder.fromHttpUrl(url + "?target=isbn")
			        .queryParam("query", request.getQuery())
			        .queryParam("sort", request.getSort())
			        .queryParam("page", request.getPage())
			        .queryParam("size", request.getSize())
			        .build(false);
			
			ResponseEntity<KakaoApiResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<String>(headers), KakaoApiResponse.class);
			responseData = response.getBody();
		} catch (RestClientException e) {
			log.error("fail to call kakao api. request=" + request.toString(), e);
			throw new E502BadGatewayException();
		}
        
        return responseData;
	}
}
