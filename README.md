# book-search-service

# Things to do
## DB 설계

	TB_USER
		userNo
		userId
		password
		name
		createdDate
		updatedDate

	TB_SEARCH_HISTORY
		historyNo
		userNo
		keyword
		createdDate

	TB_SEARCH_KEYWORD_RANK
		rankNo
		keyword
		sum
		createdDate
		appliedDate

## APIs
- 회원가입 API : POST /user
- 로그인 API : GET /login
- 책 검색 API : GET /book/search/{keyword}
- 책 상세보기 API : GET /book/{bookID}
- 내 검색 히스토리 조회 API : GET /history/{userNo}
- 인기 키워드 목록 조회 API : GET /keywork/rank/{Count}

## 랭크 생성
- Event 작성
- EventListener 작성
- 비동기 처리
- 테이블에 락 걸기

## 카카오 API 연동 Client
## 네이버 API 연동 Client

