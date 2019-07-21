package com.dev.rest.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dev.rest.message.KakaoApiRequest;
import com.dev.rest.message.KakaoApiResponse;
import com.dev.rest.message.NaverApiRequest;
import com.dev.rest.message.NaverApiResponse;
import com.dev.rest.service.KaKaoClient;
import com.dev.rest.service.NaverClient;

@RestController
public class BookController {

	@Autowired
	KaKaoClient kakaoClient;

	@Autowired
	NaverClient naverClient;

	Logger log = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "/books/kakao", method = RequestMethod.POST)
	@ResponseBody
	public KakaoApiResponse searchBookByKeywordWithKakao(@RequestBody(required = true) @Valid KakaoApiRequest request) {
		log.info(request.toString());
		return kakaoClient.searchBooksByKeyword(request);
	}
	
	@RequestMapping(value = "/books/kakao", method = RequestMethod.POST)
	@ResponseBody
	public KakaoApiResponse searchBookByIsbnWithKakao(@RequestBody(required = true) @Valid KakaoApiRequest request) {
		log.info(request.toString());
		return kakaoClient.searchBooksByIsbn(request);
	}
	
	@RequestMapping(value = "/books/naver", method = RequestMethod.POST)
	@ResponseBody
	public NaverApiResponse searchBookByKeyword(@RequestBody(required = true) @Valid NaverApiRequest request) {
		log.info(request.toString());
		return naverClient.searchBooksByKeyword(request);
	}
}
