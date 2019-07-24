package com.dev.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dev.rest.data.KeywordRank;
import com.dev.rest.service.KeywordRankService;

@RestController
@Transactional
public class KeywordRankController {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	KeywordRankService keywordRankService;
	
	/**
	 * 키워드 랭크 10 조회 
	 * @return
	 */
	@Secured({ "IS_AUTHENTICATED_FULLY" })
	@RequestMapping(path = "/book/rank", method = RequestMethod.GET)
	@ResponseBody
	public Page<KeywordRank> getTop10Keyword(){
		return keywordRankService.getTop10Keyword();
	}
}
