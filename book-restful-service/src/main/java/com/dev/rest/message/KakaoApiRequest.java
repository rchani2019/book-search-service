package com.dev.rest.message;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KakaoApiRequest {

	/*
	 * 검색 키워드 
	 */
	@NotNull
	private String query;

	/*
	 * 정렬 옵션 : accuracy (정확도순) or latest (최신순)
	 */
	private String sort="accuracy";

	/*
	 * 결과 페이지 번호 (default=1) 
	 */
	@Min(1)
	@Max(100)
	@NotNull
	private Integer page;

	/*
	 * 한 페이지에 보여질 문서의 개수 (default=10)
	 */
	@Min(1)
	@Max(50)
	@NotNull
	private Integer size;

	public KakaoApiRequest(String query, Integer page, Integer size) {
		super();
		this.query = query;
		this.page = page;
		this.size = size;
	}
}
