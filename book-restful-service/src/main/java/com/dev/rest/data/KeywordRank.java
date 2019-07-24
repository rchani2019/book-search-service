package com.dev.rest.data;


import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dev.rest.data.converter.AtomicIntConverter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="TB_SEARCH_KEYWORD_RANK")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class KeywordRank extends BaseSerializable {
	
	private static final long serialVersionUID = 7367699530512782694L;

	/*
     * 랭킹 번호
     */
    @Id
    @GeneratedValue
    @Column(columnDefinition = "INT UNSIGNED")	
	private Integer rankNo;
    
    /*
     * 사용자 아이디 
     */
    @Column(length = 50, nullable = false)
	private String keyword;
	
    /*
     * 비밀번호 
     */
    @Column(nullable = false)
    @Convert(converter = AtomicIntConverter.class)
	private AtomicInteger totalCount;

	public KeywordRank(String keyword, Integer totalCount) {
		super();
		this.keyword = keyword;
		this.totalCount = new AtomicInteger(totalCount);
	}
    
    public void incrementTotalCount() {
    		totalCount.incrementAndGet();
    }
}
