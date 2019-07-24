package com.dev.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dev.rest.data.SearchHistory;
import com.dev.rest.message.PagingInfo;
import com.dev.rest.security.AuthInfo;
import com.dev.rest.service.SearchHistoryService;

@RestController
@Transactional
public class SearchHistoryController {
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	SearchHistoryService searchHistoryService;
	
	@Secured({ "IS_AUTHENTICATED_FULLY" })
	@RequestMapping(path="/history", method=RequestMethod.GET)
	@ResponseBody
	public Page<SearchHistory> getMySearchHistory(@RequestBody PagingInfo pagingInfo, AuthInfo authInfo) {
		Pageable pageable = new PageRequest(pagingInfo.getPage(), pagingInfo.getSize(), Sort.Direction.DESC, "createdDate");
		return searchHistoryService.getSearchHistoryByUser(authInfo.getUser(), pageable);
	}
}
