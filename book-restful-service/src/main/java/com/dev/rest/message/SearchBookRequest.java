package com.dev.rest.message;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SearchBookRequest {
	/*
	 * 검색 키워드 
	 */
	@NotNull(message="검색 키워드를 입력해 주세요.")
	private String query;

	/*
	 * 결과 페이지 번호 (default=1) 
	 */
	@Min(value = 1, message="페이지 정보를 확인해주세요.")
	@Max(value = 100, message="페이지 정보를 확인해주세요.")
	@NotNull(message = "페이지 정보를 입력해 주세요.")
	private Integer page;

	/*
	 * 한 페이지에 보여질 문서의 개수 (default=10)
	 */
	@Min(value = 1, message="페이지 정보를 확인해주세요.")
	@Max(value = 50, message="페이지 정보를 확인해주세요.")
	@NotNull(message = "페이지 정보를 입력해 주세요.")
	private Integer size;
}
