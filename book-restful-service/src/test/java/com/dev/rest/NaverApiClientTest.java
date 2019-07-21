package com.dev.rest;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dev.rest.message.NaverApiRequest;
import com.dev.rest.message.NaverApiResponse;
import com.dev.rest.service.NaverClient;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NaverApiClientTest {

	@Autowired
	NaverClient naverClient;

	private static String targetIsbnNo;

	@BeforeClass
	public static void setup() {
		targetIsbnNo = null;
	}

	@Test
	public void A_searchBookByKeywordTest() {
		// given
		NaverApiRequest request = new NaverApiRequest("self", "sim", 1, 5);

		// when
		NaverApiResponse response = naverClient.searchBooksByKeyword(request);
		
		// then
		assertNotNull(response);
		assertNotNull(response.getItems());
		assertThat(response.getItems().size() == response.getDisplay());

//		targetIsbnNo = Arrays.stream(response.getItems().get(0).getIsbn().split(" ")).map(String::trim).findFirst();
//		targetIsbnNo = String.valueOf(response.getItems().get(0).getIsbn());
//		System.out.println("targetIsbnNo = " + targetIsbnNo);
	}

	@Test
	public void B_searchBookByIsbn() {
		/*
		// given
		assertNotNull(targetIsbnNo);
		NaverApiRequest request = new NaverApiRequest(targetIsbnNo, "sim", 1, 5);

		// when
		NaverApiResponse response = naverClient.searchBooksByKeyword(request);

		// then
		assertNotNull(response);
		assertNotNull(response.getItems());
		assertThat(response.getItems().size() == 1);
		*/
	}
}