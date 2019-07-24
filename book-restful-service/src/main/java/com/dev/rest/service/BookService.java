package com.dev.rest.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.rest.exception.E502BadGatewayException;
import com.dev.rest.message.CommonBookInfo;
import com.dev.rest.message.KakaoApiMetaInfo;
import com.dev.rest.message.KakaoApiRequest;
import com.dev.rest.message.KakaoApiResponse;
import com.dev.rest.message.NaverApiRequest;
import com.dev.rest.message.NaverApiResponse;
import com.dev.rest.message.PageResponse;
import com.dev.rest.message.SearchBookRequest;
import com.dev.rest.message.SearchBookResponse;

@Service
public class BookService {
	@Autowired
	private KaKaoClient kaKaoClient;
	@Autowired
	private NaverClient naverClient;
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * 책 검색 API
	 * @param request
	 * @return
	 */
	public SearchBookResponse searhBook(SearchBookRequest request) {
		boolean isFailKakaoApi = false;
		SearchBookResponse resultResponse = new SearchBookResponse();
		try {
			KakaoApiResponse kakaoResponse = kaKaoClient.searchBooksByKeyword(new KakaoApiRequest(request.getQuery(), request.getPage(), request.getSize()));
			resultResponse = buildResponseWithKaKaoApi(request, kakaoResponse);
		} catch (Exception e) {
			log.error("kakao api failed.", e);
			isFailKakaoApi = true;
		}
		
		if(isFailKakaoApi) {
			log.info("retry with naver api");
			try {
				NaverApiResponse naverResponse = naverClient.searchBooksByKeyword(new NaverApiRequest(request.getQuery(), convertPageToStartIdx(request.getPage(), request.getSize()), request.getSize()));
				resultResponse = buildResponseWithNaver(request, naverResponse);
			} catch (Exception e) {
				log.error("naver api failed.", e);
				throw new E502BadGatewayException();
			}
		}
		
		return resultResponse;
	}
	
	/**
	 * 책 상세 정보 조회 API 
	 * @param isbn
	 * @return
	 */
	public CommonBookInfo getBookDetail(String isbn) {
		boolean isFailKakaoApi = false;
		
		CommonBookInfo bookInfo = new CommonBookInfo();
		try {
			KakaoApiResponse kakaoResponse = kaKaoClient.searchBooksByIsbn(new KakaoApiRequest(isbn, 1, 1));
			List<CommonBookInfo> books = getBookInfoWithKakaoApi(kakaoResponse);
			if(books != null && books.size() > 0) {
				bookInfo = books.get(0);
			}
		} catch (Exception e) {
			log.error("kakao api failed.", e);
			isFailKakaoApi = true;
		}
		
		if(isFailKakaoApi) {
			log.info("retry with naver api");
			try {
				NaverApiResponse naverResponse = naverClient.searchBooksByKeyword(new NaverApiRequest(isbn, 1, 1));
				List<CommonBookInfo> books = getBookInfoWithNaverApi(naverResponse);
				if(books != null && books.size() > 0) {
					bookInfo = books.get(0);
				}
			} catch (Exception e) {
				log.error("naver api failed.", e);
				throw new E502BadGatewayException();
			}
		}
		
		return bookInfo;
	}

	/**
	 * 페이지의 첫번째 인덱스 구하는 메서드 
	 * @param page
	 * @param size
	 * @return
	 */
	private Integer convertPageToStartIdx(Integer page, Integer size) {
		return (page - 1) * size + 1;
	}
	
	/**
	 * 전체 페이지 수 구하는 메서드 
	 * @param totalElements
	 * @param size
	 * @return
	 */
	private Integer getTotalPage(int totalElements, int size) {
		Integer totalPage = totalElements / size;
		if(totalElements % size > 0) {
			totalPage++;
		}
		return totalPage;
	}
	
	/**
	 * 카카오 API 결과를 파싱하여 응답 객체 생성 
	 * @param request
	 * @param kakaoResponse
	 * @return
	 */
	private SearchBookResponse buildResponseWithKaKaoApi(SearchBookRequest request, KakaoApiResponse kakaoResponse) {
		SearchBookResponse resultResponse = new SearchBookResponse();
		
		// 책 정보 셋팅 
		resultResponse.setBookInfos(getBookInfoWithKakaoApi(kakaoResponse));
		
		// 페이지 정보 셋팅 
		KakaoApiMetaInfo meta = kakaoResponse.getMeta();
		PageResponse pageResponse = new PageResponse();
		pageResponse.setNumber(request.getPage());
		pageResponse.setSize(request.getSize());
		pageResponse.setTotalElements(meta.getPageable_count());
		pageResponse.setTotalPages(getTotalPage(meta.getPageable_count(), request.getSize()));
		resultResponse.setPage(pageResponse);
		
		return resultResponse;
	}
	
	/**
	 * 카카오 API 결과를 파싱하여 응답 객체 생성 (책 상세 정보)
	 * @param kakaoResponse
	 * @return
	 */
	private List<CommonBookInfo> getBookInfoWithKakaoApi(KakaoApiResponse kakaoResponse) {
		List<CommonBookInfo> bookInfos = new ArrayList<CommonBookInfo>();
		kakaoResponse.getDocuments().stream()
		.forEach(book ->{
			CommonBookInfo bookInfo = new CommonBookInfo();
			BeanUtils.copyProperties(book, bookInfo);
			bookInfos.add(bookInfo);
		});
		
		return bookInfos;
	}
	
	/**
	 * 네이버 API 결과를 파싱하여 응답 객체 생성 
	 * @param request
	 * @param naverApiResponse
	 * @return
	 */
	private SearchBookResponse buildResponseWithNaver(SearchBookRequest request, NaverApiResponse naverApiResponse) {
		SearchBookResponse resultResponse = new SearchBookResponse();
		
		// 책 정보 셋팅 
		resultResponse.setBookInfos(getBookInfoWithNaverApi(naverApiResponse));
		
		// 페이지 정보 셋팅 
		PageResponse pageResponse = new PageResponse();
		pageResponse.setNumber(request.getPage());
		pageResponse.setSize(request.getSize());
		pageResponse.setTotalElements(naverApiResponse.getTotal());
		pageResponse.setTotalPages(getTotalPage(naverApiResponse.getTotal(), request.getSize()));
		resultResponse.setPage(pageResponse);
		return resultResponse;
	}
	
	/**
	 * 네이버 API 결과를 파싱하여 응답 객체 생성 (책 상세 정보)
	 * @param naverApiResponse
	 * @return
	 */
	private List<CommonBookInfo> getBookInfoWithNaverApi(NaverApiResponse naverApiResponse) {
		List<CommonBookInfo> bookInfos = new ArrayList<CommonBookInfo>();
		
		naverApiResponse.getItems().stream()
		.forEach(book -> {
			CommonBookInfo bookInfo = new CommonBookInfo();
			bookInfo.setTitle(book.getTitle());
			bookInfo.setContents(book.getDescription());
			bookInfo.setDatetime(book.getPubdate());
			bookInfo.setIsbn(book.getIsbn());
			bookInfo.setPrice(book.getPrice());
			bookInfo.setSale_price(book.getDiscount());
			bookInfo.setPublisher(book.getPublisher());
			bookInfo.setThumbnail(book.getImage());
			if(StringUtils.isNotBlank(book.getAuthor())) {
				bookInfo.setAuthors(
						Arrays.stream(book.getAuthor().split("|"))
						.map(String::trim)
						.collect(Collectors.toList()));
				bookInfos.add(bookInfo);
			}
		});
		
		return bookInfos;
	}
}
