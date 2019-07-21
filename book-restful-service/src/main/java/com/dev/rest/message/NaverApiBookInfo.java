package com.dev.rest.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NaverApiBookInfo {
	/*
	 * 도서 제목
	 */
	private String title;

	/*
	 * 도서 소개
	 */
	private String description;

	/*
	 * 도서 상세 URL
	 */
	private String link;

	private String image;
	/*
	 * 도서 저자, "|"로 구분되어 있음
	 */
	private String author;
	/*
	 * 도서 정가
	 */
	private Integer price;

	/*
	 * 도서 할인가
	 */
	private Integer discount;
	/*
	 * 도서 출판사
	 */
	private String publisher;
	/*
	 * 도서 출판날짜
	 */
	private String pubdate;
	/*
	 * 국제 표준 도서번호(ISBN10 ISBN13) (ISBN10,ISBN13 중 하나 이상 존재하며, ' '(공백)을 구분자로 출력됩니다)
	 */
	private String isbn;
}
