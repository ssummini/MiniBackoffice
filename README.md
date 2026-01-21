# MiniBackoffice

미니 커머스 **백오피스 API** 토이 프로젝트입니다.  
JWT 인증/인가를 적용해 관리자 권한 기반 CRUD를 구현했고,  
Render에 배포해 **개발–배포–운영 흐름을 직접 경험**하는 것을 목표로 했습니다.

- **Live API**: https://minibackoffice.onrender.com
- **Health Check**: https://minibackoffice.onrender.com/health

---

## 🚀 Key Features
- 상품(Product) CRUD API
  - 조회: 누구나
  - 등록/수정/삭제: **ADMIN 전용**
- 사용자(User) 회원가입 / 로그인(JWT)
- BCrypt 비밀번호 해시
- 상태코드 처리(401/403/404/409/400) + Validation
- 배포 환경(prod)에서 관리자 Seed 자동 생성

---

## 🛠 Tech Stack
- Backend: Spring Boot, Spring Data JPA
- Database: (dev) H2 / (prod) PostgreSQL (Render)
  - H2 in-memory의 한계를 느껴, 배포 환경에서는 PostgreSQL로 전환하여 데이터 영구화 경험
- Auth: JWT, BCrypt
- Tools: VS Code, Postman

---

## 🧪 Quick Test (Postman)
### 1) ADMIN 로그인
POST /api/users/login
```json
{
  "email": "admin@test.com",
  "password": "1234"
}
```
### 2) 상품 등록 (ADMIN)
POST /api/products
Header: Authorization: Bearer {TOKEN}
```json
{
  "name": "배포서버 상품1",
  "price": 12000,
  "stockQuantity": 5,
  "status": "SELLING",
  "thumbnailUrl": "https://example.com/a.png"
}
```

---

## API Summary
### Product
- GET /api/products
- GET /api/products/{id}
- POST /api/products (ADMIN)
- PUT /api/products/{id} (ADMIN)
- DELETE /api/products/{id} (ADMIN)

### User
- POST /api/users (회원가입)
- GET /api/users (ADMIN)
- GET /api/users/{id} (ADMIN)
- PATCH /api/users/{id}/status (ADMIN)

---

## 📌 Progress
- [x] Product CRUD + 권한 분리
- [x] User 회원가입/로그인 + JWT
- [x] BCrypt 적용
- [x] Render 배포
- [x] DB 영구화(PostgreSQL)
- [ ] 리팩토링(권한 체크 공통화, 에러 포맷 통일)
- [ ] React 연동

> 현재 백엔드 기능 구현 및 배포를 완료한 상태이며,  
> 이후 React 기반 프론트엔드 연동을 통해 백오피스 UI까지 확장할 예정입니다..
