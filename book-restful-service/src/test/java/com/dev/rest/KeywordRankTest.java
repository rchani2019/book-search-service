package com.dev.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import com.dev.rest.data.KeywordRank;
import com.dev.rest.data.repository.KeywordRankRepository;
import com.dev.rest.service.KeywordRankService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KeywordRankTest {
	
	@Autowired
	KeywordRankRepository keywordRankRepository;
	
	@Autowired
	KeywordRankService keywordRankService;
	
	@Test
	public void getTop10KeywordTest() {
		// given
		keywordRankRepository.deleteAll();
		IntStream.rangeClosed(1, 30).forEach(x -> {
			keywordRankRepository.save(new KeywordRank("keyword" + String.valueOf(x), x));
		});
		
		Iterator<KeywordRank> iterator = keywordRankRepository.findAll().iterator();
		while(iterator.hasNext()) {
			KeywordRank keywordRank = iterator.next();
			System.out.println(keywordRank.getKeyword() + "::" + keywordRank.getTotalCount());
		}
		
		// when
		Page<KeywordRank> top10keywords = keywordRankService.getTop10Keyword();
		
		// then
		assertThat(top10keywords).isNotNull().hasSize(10);
		assertEquals("keyword30", top10keywords.getContent().get(0).getKeyword());
		assertEquals("keyword21", top10keywords.getContent().get(9).getKeyword());
		
	}
}
