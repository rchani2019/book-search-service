package com.dev.rest.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.rest.data.SearchHistory;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Integer>{

}
