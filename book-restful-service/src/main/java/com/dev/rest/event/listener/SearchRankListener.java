package com.dev.rest.event.listener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.dev.rest.data.KeywordRank;
import com.dev.rest.data.repository.KeywordRankRepository;
import com.dev.rest.event.SearchHistoryEvent;

@Component
public class SearchRankListener {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private KeywordRankRepository keywordRankRepository;
	
	public static final int EVENT_TYPE_SEARCH_ENTER = 0;
	
	Logger log = LoggerFactory.getLogger(getClass());

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution=true, classes=SearchHistoryEvent.class)
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@Async("searchRankCalThreadPoolTaskExecutor")
	public void onSearchHistoryEvent(SearchHistoryEvent event) throws InterruptedException {
		log.debug("onSearchHistoryEvent fired!!");
		if(event.getType() == EVENT_TYPE_SEARCH_ENTER) {
			onEnter(event);
		}
	}

	private synchronized void onEnter(SearchHistoryEvent event) throws InterruptedException {
		log.debug("search history entered!! {}", event.getSearchHistory().toString());
		// Write Lock 설정 
		Map<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.lock.timeout", 10000);
		
		String keyword = event.getSearchHistory().getKeyword();
		KeywordRank keywordRank = keywordRankRepository.findOneByKeyword(keyword);
		
		if(keywordRank == null) {
			entityManager.persist(new KeywordRank(keyword, 1));
		} else {
			entityManager.lock(keywordRank, LockModeType.PESSIMISTIC_WRITE);
//			keywordRank.setTotalCount(keywordRank.getTotalCount() + 1);
			keywordRank.incrementTotalCount();
			entityManager.merge(keywordRank);			
		}
		
		// 공백으로 나눈 각각의 키워드도 등록한다.
//		Arrays.stream(event.getSearchHistory().getKeyword().split(" "))
//		.map(String::trim)
//		.filter(k -> StringUtils.isEmpty(k))
//		.forEach(k -> {
//			KeywordRank partialKeyword = keywordRankRepository.findOneByKeyword(k);
//			if(partialKeyword == null) {
//				entityManager.persist(new KeywordRank(keyword, 1));
//			} else {
//				entityManager.lock(partialKeyword, LockModeType.PESSIMISTIC_WRITE);
//				keywordRank.setTotalCount(partialKeyword.getTotalCount() + 1);
//				entityManager.merge(partialKeyword);			
//			}	
//		});
	}
}
