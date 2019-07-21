package com.dev.rest.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

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
import com.dev.rest.message.NaverApiRequest;
import com.dev.rest.message.NaverApiResponse;

import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "api.search.naver")
@Setter
public class NaverClient {

	private String url;
	private String clientId;
	private String clientSecret;

	@Autowired
	RestTemplate restTemplate;
	
	Logger log = LoggerFactory.getLogger(getClass());

	public NaverApiResponse searchBooksByKeyword(NaverApiRequest request) {
		NaverApiResponse responseData = new NaverApiResponse();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType(MediaType.APPLICATION_JSON, Charset.defaultCharset()));
			headers.add("X-Naver-Client-Id", clientId);
			headers.add("X-Naver-Client-Secret", clientSecret);

			/*
			try {
				String encodedQuery = new String(request.getQuery().getBytes(), "UTF-8");
				System.out.println("encodedQuery = " + encodedQuery);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			*/
			
			UriComponents builder = null;
				builder = UriComponentsBuilder.fromHttpUrl(url)
						.queryParam("query", URLEncoder.encode(request.getQuery(), "UTF-8"))
						.queryParam("sort", request.getSort())
						.queryParam("start", request.getStart())
						.queryParam("display", request.getDisplay()).build(false);

			log.debug("URL : {}", builder.toUriString());
			
			ResponseEntity<NaverApiResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
					new HttpEntity<String>(headers), NaverApiResponse.class);
			responseData = response.getBody();
		} catch (RestClientException e) {
			log.error("fail to call naver api. request=" + request.toString(), e);
			throw new E502BadGatewayException();
		} catch (UnsupportedEncodingException e) {
			log.error("fail to encode keyword. request=" + request.toString(), e);
		}

		return responseData;
	}
}
