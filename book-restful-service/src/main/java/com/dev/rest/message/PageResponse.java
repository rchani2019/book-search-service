package com.dev.rest.message;

import lombok.Data;

@Data
public class PageResponse {
    private int size;
    private int totalElements;
    private int totalPages;
    private int number;
}
