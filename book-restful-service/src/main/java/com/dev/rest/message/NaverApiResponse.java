package com.dev.rest.message;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NaverApiResponse {
	private String lastBuildDate;
	private Integer total;
	private Integer start;
	private Integer display;
	private List<NaverApiBookInfo> items = new ArrayList<>();
}
