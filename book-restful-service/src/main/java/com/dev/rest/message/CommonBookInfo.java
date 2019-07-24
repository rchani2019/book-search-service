package com.dev.rest.message;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Kakao & Naver에서 제공한 공통된 책 정보  
 * @author gomunjeong
 *
 */
@Data
@NoArgsConstructor
public class CommonBookInfo {
	// 제목, 도서 썸네일, 소개, ISBN, 저자, 출판사, 출판일, 정가, 판매가
	/*
	 * 도서 제목
	 */
	private String title;
	/*
	 * 도서 표지 썸네일 URL
	 */
	private String thumbnail;
	/*
	 * 도서 소개
	 */
	private String contents;
	/*
	 * 국제 표준 도서번호(ISBN10 ISBN13) (ISBN10,ISBN13 중 하나 이상 존재하며, ' '(공백)을 구분자로 출력됩니다)
	 */
	private String isbn;
	/*
	 * 도서 출판날짜. ISO 8601. [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
	 */
	private String datetime;
	/*
	 * 도서 저자 리스트
	 */
	private List<String> authors;
	/*
	 * 도서 출판사
	 */
	private String publisher;
	/*
	 * 도서 정가
	 */
	private Integer price;
	/*
	 * 도서 판매가
	 */
	private Integer sale_price;
	
}
