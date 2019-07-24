package com.dev.rest.message;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NaverApiRequest {

	/*
	 * 검색을 원하는 문자열로서 UTF-8로 인코딩한다.
	 */
	@NotNull
	@NotEmpty
	private String query;

	/*
	 * sim(기본값), 정렬 옵션: sim(유사도순), date(출간일순), count(판매량순)
	 */
	private String sort="sim";

	/*
	 * 검색 시작 위치
	 */
	@NotNull
	private Integer start;

	/*
	 * 검색 결과 출력 건수
	 */
	@NotNull
	private Integer display;
	
	public NaverApiRequest(String query, Integer start, Integer display) {
		this.query = query;
		this.start = start;
		this.display = display;
	}
}
