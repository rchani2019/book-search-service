## book-search-service


#### Jar File Link
	https://drive.google.com/drive/folders/1KOmCOEisLwjaAXcjgYTUfQmjeq4Xnz41

#### PostMan Import File Link
	https://github.com/rchani2019/book-search-service/blob/master/Book-RESTful-Service.postman_collection.json

#### 인증
	HTTP Basic 인증를 사용한다. 아래와 같이 Header의 Authorization 값에 인증 정보가 전달된다.
	Authorization Basic Base64Encode[UserId:Password]
	ex) Authorization Basic cmNoYW5pOmtha2FvMTIz
	
#### API 목록
	1. 회원 가입
		POST /api/user/join
	2. 로그인
		GET /api/login
	3. 책 검색/조회
		POST /api/books
	4. 책 상세 정보 조회
		GET /api/books/{isbn}
	5. 나의 검색 히스토리 조회
		GET /api/history
	6. 검색 키워드 탑 10 조회
		GET /api/book/rank

#### DB 설계

	TB_USER
		userNo
		userId
		password
		name
		createdDate

	TB_SEARCH_HISTORY
		historyNo
		userNo
		keyword
		createdDate

	TB_SEARCH_KEYWORD_RANK
		rankNo
		keyword
		totalCount
		createdDate
