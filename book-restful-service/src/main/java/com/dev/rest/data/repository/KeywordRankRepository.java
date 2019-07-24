package com.dev.rest.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.dev.rest.data.KeywordRank;

public interface KeywordRankRepository extends PagingAndSortingRepository<KeywordRank, Integer>{
	
	public KeywordRank findOneByKeyword(String keyword); 

}
