package com.dev.rest.message;

import java.util.List;

import lombok.Data;

@Data
public class SearchBookResponse {
	List<CommonBookInfo> bookInfos;
	PageResponse page;
}
