package com.dev.rest.event;

import org.springframework.context.ApplicationEvent;

import com.dev.rest.data.SearchHistory;

import lombok.Getter;
import lombok.Setter;

/**
 * 키워드 검색시 랭킹을 갱신한다.
 * 
 * @author gomunjeong
 *
 */
@Getter
@Setter
public class SearchHistoryEvent extends ApplicationEvent {

	private static final long serialVersionUID = 4875298782654227980L;

	private int type;
	private SearchHistory searchHistory;

	public static final int EVENT_TYPE_SEARCH_ENTER = 0;

	public SearchHistoryEvent(Object source, int type, SearchHistory searchHistory) {
		super(source);
		this.type = type;
		this.searchHistory = searchHistory;
	}

}
