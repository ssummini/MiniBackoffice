package com.minibackoffice.backend.global.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AdminAuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        // ADMIN이 필요한 요청만 검사
        if (!isAdminRequired(request)) {
            return true;
        }

        Object role = request.getAttribute("role");

        if (role == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        if (!"ADMIN".equals(role.toString())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "관리자 권한이 필요합니다.");
        }

        return true;
    }

    private boolean isAdminRequired(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod();

        // Product: 등록/수정/삭제만 ADMIN
        if (uri.startsWith("/api/products")) {
            return "POST".equals(method) || "PUT".equals(method) || "DELETE".equals(method);
        }

        // User: 상태 변경만 ADMIN
        if (uri.matches("^/api/users/\\d+/status$") && "PATCH".equals(method)) {
            return true;
        }

        return false;
    }
}
