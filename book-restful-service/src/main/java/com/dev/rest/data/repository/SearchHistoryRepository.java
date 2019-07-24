package com.dev.rest.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.dev.rest.data.SearchHistory;
import com.dev.rest.data.User;

public interface SearchHistoryRepository extends PagingAndSortingRepository<SearchHistory, Integer>{
	
	public Page<SearchHistory> findByUser(User user, Pageable pageable);
}
