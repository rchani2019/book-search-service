package com.dev.rest.message;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakaoApiBookInfo {
	/*
	 * 도서 제목
	 */
	private String title;
	/*
	 * 도서 소개
	 */
	private String contents;
	/*
	 *도서 상세 URL 
	 */
	private String url;
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
	 *  도서 번역자 리스트
	 */
	private List<String> translators;
	/*
	 * 도서 정가
	 */
	private Integer price;
	/*
	 * 도서 판매가
	 */
	private Integer sale_price;
	/*
	 * 도서 표지 썸네일 URL
	 */
	private String thumbnail;
	/*
	 * 도서 판매 상태 정보(정상, 품절, 절판 등)
	 */
	private String status;
}
