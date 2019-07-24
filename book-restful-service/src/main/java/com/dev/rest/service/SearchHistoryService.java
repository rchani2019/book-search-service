package com.dev.rest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dev.rest.data.SearchHistory;
import com.dev.rest.data.User;
import com.dev.rest.data.repository.SearchHistoryRepository;

@Service
public class SearchHistoryService {

	@Autowired
	private SearchHistoryRepository searchHistoryRepository;
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	public SearchHistory saveSearchHistory(User user, String keyword) {
		return searchHistoryRepository.save(new SearchHistory(keyword, user));
	}
	
	public Page<SearchHistory> getSearchHistoryByUser(User user, Pageable pageable){
		return searchHistoryRepository.findByUser(user, pageable);
	}
	
}
