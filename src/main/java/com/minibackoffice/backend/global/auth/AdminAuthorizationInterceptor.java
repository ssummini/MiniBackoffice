package com.minibackoffice.backend.global.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminAuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        // 1) 컨트롤러 메서드가 아닌 요청이면 그냥 통과
        if (!(handler instanceof HandlerMethod hm)) {
            return true;
        }

        // 2) 이 요청이 @AdminOnly가 붙은 곳인지 확인
        boolean adminRequired =
                hm.hasMethodAnnotation(AdminOnly.class) ||
                hm.getBeanType().isAnnotationPresent(AdminOnly.class);

        // 3) @AdminOnly 없으면 관리자 검사 안 함
        if (!adminRequired) {
            return true;
        }

        // 4) @AdminOnly 있으면 role 검사
        Object role = request.getAttribute("role");

        if (role == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        if (!"ADMIN".equals(role.toString())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "관리자 권한이 필요합니다.");
        }

        return true;
    }
}
