# MiniBackoffice

미니 커머스 백오피스 토이 프로젝트입니다.  
Spring Boot + JPA + MySQL 기반으로 CRUD API를 구현하고,  
추후 React 연동 및 배포까지 진행하는 것이 목표입니다.

## Tech Stack
- Backend: Spring Boot, Spring Data JPA
- Database: MySQL
- Tools: VS Code, MySQL Workbench, Postman

## Run (Backend)
- Java 17
- MySQL 실행
- application.properties DB 설정
- BackendApplication 실행

## API (진행 중)

### Product
- POST /api/products : 상품 등록 (Create DTO 적용)
- GET /api/products : 상품 목록 조회 (Response DTO 적용)
- GET /api/products/{id} : 상품 단건 조회 (Response DTO 적용)
- PUT /api/products/{id} : 상품 수정 (Update DTO 적용)
- DELETE /api/products/{id} : 상품 삭제

- GET / PUT / DELETE 실패 시 404 Not Found 반환

---

### User
- POST /api/users : 사용자 생성

#### 요청 예시
```json
{
  "email": "test@test.com",
  "password": "1234",
  "name": "수민",
  "role": "USER",
  "status": "ACTIVE"
}
```
#### 요청 예시 (role / status 생략 시 기본값 적용)
```json
{
  "email": "test2@test.com",
  "password": "1234",
  "name": "수민"
}
```
- role 기본값: USER  
- status 기본값: ACTIVE  

#### 응답 예시 (성공)
```json
{
  "id": 1,
  "email": "test@test.com",
  "name": "수민",
  "role": "USER",
  "status": "ACTIVE"
}
```

#### 예외 처리
- 이메일 중복 시: 409 Conflict
- 잘못된 role / status 값 입력 시: 400 Bad Request
## Progress
- [x] Product CRUD API
- [x] User Create API
- [x] User Read API (list / detail)
- [ ] Validation & Exception Handling
- [ ] Password Encryption (BCrypt)
- [ ] Deployment

