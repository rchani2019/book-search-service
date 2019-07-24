package com.dev.rest.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dev.rest.data.SearchHistory;
import com.dev.rest.event.SearchHistoryEvent;
import com.dev.rest.message.CommonBookInfo;
import com.dev.rest.message.SearchBookRequest;
import com.dev.rest.message.SearchBookResponse;
import com.dev.rest.security.AuthInfo;
import com.dev.rest.service.BookService;
import com.dev.rest.service.KaKaoClient;
import com.dev.rest.service.NaverClient;
import com.dev.rest.service.SearchHistoryService;

@RestController
@Transactional
public class BookController {

	@Autowired
	KaKaoClient kakaoClient;

	@Autowired
	NaverClient naverClient;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	SearchHistoryService searchHistoryService;
	
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * 책 검색 API 
	 * @param request
	 * @param authInfo
	 * @return
	 */
	@RequestMapping(value = "/books", method = RequestMethod.POST)
	@ResponseBody
	@Secured({ "IS_AUTHENTICATED_FULLY" })
	public SearchBookResponse searchBooks(@RequestBody(required = true) @Valid SearchBookRequest request, AuthInfo authInfo) {
		log.debug(request.toString());
		SearchBookResponse response = bookService.searhBook(request);
		// 히스토리 저장 
		SearchHistory searchHistory = searchHistoryService.saveSearchHistory(authInfo.getUser(), request.getQuery());
		// 이벤트 발생 
		applicationEventPublisher.publishEvent(new SearchHistoryEvent(this, SearchHistoryEvent.EVENT_TYPE_SEARCH_ENTER, searchHistory));
		return response;
	}
	
	/**
	 * 책 상세 정보 조회 API 
	 * @param isbn
	 * @return
	 */
	@RequestMapping(value = "/books/{isbn}", method = RequestMethod.GET)
	@ResponseBody
	@Secured({ "IS_AUTHENTICATED_FULLY" })
	public CommonBookInfo getBookDetail(@PathVariable("isbn") String isbn) {
		return bookService.getBookDetail(isbn);
	}
}
