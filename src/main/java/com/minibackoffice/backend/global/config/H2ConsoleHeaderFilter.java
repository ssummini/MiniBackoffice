package com.minibackoffice.backend.global.config;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Profile("dev")
@Component
public class H2ConsoleHeaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // H2 콘솔은 iframe을 사용함 → SAMEORIGIN 허용
        response.setHeader("X-Frame-Options", "SAMEORIGIN");

        filterChain.doFilter(request, response);
    }
}
