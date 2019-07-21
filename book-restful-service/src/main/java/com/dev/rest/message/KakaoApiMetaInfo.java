package com.dev.rest.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakaoApiMetaInfo {
	/*
	 * 전체 건수 
	 */
	private Integer total_count;
	
	/*
	 * 페이징 가능한 전체 건수 
	 */
	private Integer pageable_count;
	
	/*
	 * 마지막 여부 
	 */
	private Boolean is_end;
}
