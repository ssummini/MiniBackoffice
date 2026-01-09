# MiniBackoffice

미니 커머스 백오피스 토이 프로젝트입니다.  
Spring Boot + JPA + MySQL 기반으로 CRUD API를 구현하고,  
JWT 인증/인가를 적용해 관리자 권한 기반의 백오피스 기능을 구현하는 것이 목표입니다.  
(추후 React 연동 및 배포까지 진행 예정)

---

## Tech Stack
- Backend: Spring Boot, Spring Data JPA
- Database: MySQL
- Tools: VS Code, MySQL Workbench, Postman

---

## Run (Backend)
- Java 17
- MySQL 실행
- application.properties DB 설정
- BackendApplication 실행

---

## API

### Auth / Login (JWT)
- POST /api/users/login : 로그인 (JWT 토큰 발급)

#### 요청 예시
```json
{
  "email": "admin@test.com",
  "password": "1234"
}
```

#### 응답 예시 (성공)
```json
{
  "token": "JWT_TOKEN_HERE",
  "user": {
    "id": 1,
    "email": "admin@test.com",
    "name": "관리자",
    "role": "ADMIN",
    "status": "ACTIVE"
  }
}
```
#### 인증 헤더
- Authorization: Bearer {JWT_TOKEN}

---

## Product
- GET /api/products : 상품 목록 조회
- GET /api/products/{id} : 상품 단건 조회
- POST /api/products : 상품 등록 (ADMIN 전용)
- PUT /api/products/{id} : 상품 수정 (ADMIN 전용)
- DELETE /api/products/{id} : 상품 삭제 (ADMIN 전용)

#### 예외 / 상태 코드
- 401 Unauthorized : 토큰 없음 또는 유효하지 않음
- 403 Forbidden : 관리자 권한 없음
- 404 Not Found : 상품 없음
- 400 Bad Request : Validation 실패

---

## User
- POST /api/users : 사용자 생성(회원가입)
- GET /api/users : 사용자 목록 조회 (ADMIN 전용)
- GET /api/users/{id} : 사용자 단건 조회 (ADMIN 전용)
- PATCH /api/users/{id}/status : 사용자 상태 변경 (ADMIN 전용)

### 회원가입 요청 예시
```json
{
  "email": "test@test.com",
  "password": "1234",
  "name": "수민",
  "role": "USER",
  "status": "ACTIVE"
}
```
### 요청 예시 (role / status 생략 시 기본값 적용)
```json
{
  "email": "test2@test.com",
  "password": "1234",
  "name": "수민"
}
```
- role 기본값: USER
- status 기본값: ACTIVE

## 사용자 상태 변경 요청 예시
```json
{
  "status": "BLOCKED"
}
```
## 예외 / 상태 코드
- 409 Conflict : 이메일 중복
- 400 Bad Request : 잘못된 role/status 또는 Validation 실패
- 401 Unauthorized : 토큰 없음 또는 유효하지 않음
- 403 Forbidden : 관리자 권한 없음
- 404 Not Found : 사용자 없음

---

## Progress
- [x] Product CRUD API
- [x] Product Validation 적용
- [x] User Create / Read API
- [x] User Status 변경 API (PATCH)
- [x] Password Encryption (BCrypt)
- [x] JWT 로그인 및 ADMIN 권한 체크
- [ ] Deployment