package com.dev.rest.data.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dev.rest.data.SearchHistory;
import com.dev.rest.service.SearchHistoryService;

@RestController
@Transactional
public class SearchHistoryController {
	Logger log = LoggerFactory.getLogger(getClass());
	

	@Autowired
	SearchHistoryService searchHistoryService;
	
	@RequestMapping(path="/history", method=RequestMethod.GET)
	public Page<SearchHistory> getMySearchHistory(
			@PageableDefault(page = 0, size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){
		//FIXME user 넣어주기 !!
//		searchHistoryService.getSearchHistoryByUser(user, pageable);
		return null;
	}

}
