package com.dev.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class RestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApplication.class, args);
	}

}
