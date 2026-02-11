# MiniBackoffice

관리자 중심의 커머스 백오피스 API 프로젝트입니다.  
JWT 인증/인가를 적용하여 역할 기반 CRUD를 구현했습니다.

---

## 🌐 Live API
- https://minibackoffice.onrender.com
- Health Check: https://minibackoffice.onrender.com/api/health

---

## 🛠 Tech Stack
- Spring Boot
- Spring Data JPA
- JWT
- BCrypt
- H2 (dev)
- PostgreSQL (Render, prod)
- Render (배포)

---

## 🚀 주요 기능

### 🔐 인증
- 회원가입 / 로그인
- JWT 기반 인증
- BCrypt 비밀번호 해시

### 🛒 상품 관리
- 조회: 누구나
- 등록/수정/삭제: ADMIN 전용

### ⚙️ 공통 에러 처리
- GlobalExceptionHandler
- 공통 에러 응답 포맷 통일

---

## 🔐 권한 정책

- USER / ADMIN 역할 분리
- ADMIN 전용 API 보호
- 프론트 UX 제어 + 백엔드 실제 검증

---

## ❗ 배포 시 해결한 이슈

### 1️⃣ CORS 설정 추가
프론트(Vercel)에서 오는 요청 허용

### 2️⃣ Render 환경 DB 영구화
H2 → PostgreSQL 전환

---

## 🧪 Quick Test (Postman)

### ADMIN 로그인
POST /api/users/login

```json
{
  "email": "admin@test.com",
  "password": "1234"
}

---

## 📌 Project Status
- [x] JWT 인증/인가
- [x] Product CRUD
- [x] User 관리
- [x] PostgreSQL 전환
- [x] Render 배포
- [x] React 연동
- [x] CORS 해결
- [x] SPA 라우팅 대응