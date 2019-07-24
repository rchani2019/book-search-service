package com.dev.rest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dev.rest.data.KeywordRank;
import com.dev.rest.data.repository.KeywordRankRepository;

@Service
public class KeywordRankService {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	KeywordRankRepository keywordRankRepository;

	/**
	 * 많이 검색된 키워드 10개를 조회한다. 
	 * @return
	 */
	public Page<KeywordRank> getTop10Keyword(){
		PageRequest pageRequest = new PageRequest(0, 10, Sort.Direction.DESC, "totalCount");
		return keywordRankRepository.findAll(pageRequest);
	}

}
