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

import com.dev.rest.message.KakaoApiRequest;
import com.dev.rest.message.KakaoApiResponse;
import com.dev.rest.service.KaKaoClient;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class KaKaoApiClientTest {

	@Autowired
	KaKaoClient kakaoClient;

	private static Optional<String> targetIsbnNo;
	
	@BeforeClass
	public static void setup() {
		targetIsbnNo = null;
	}

	@Test
	public void A_searchBookByKeywordTest() {
		// given
		KakaoApiRequest request = new KakaoApiRequest("미움받을 용기", "accuracy", 1, 5);

		// when
		KakaoApiResponse response = kakaoClient.searchBooksByKeyword(request);

		// then
		assertNotNull(response.getMeta());
		assertNotNull(response.getDocuments());
		assertThat(response.getDocuments().size() == 5);

		targetIsbnNo = Arrays.stream(response.getDocuments().get(0).getIsbn().split(" "))
				  .map(String::trim)
				  .findFirst();
		System.out.println("targetIsbnNo = " + targetIsbnNo);
	}

	@Test
	public void B_searchBookByIsbn() {
		// given
		assertThat(targetIsbnNo.isPresent());
		KakaoApiRequest request = new KakaoApiRequest(targetIsbnNo.get(), "accuracy", 1, 5);

		// when
		KakaoApiResponse response = kakaoClient.searchBooksByIsbn(request);

		// then
		assertNotNull(response.getMeta());
		assertNotNull(response.getDocuments());
		assertThat(response.getDocuments().size() == 1);
	}
}