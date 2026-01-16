package com.minibackoffice.backend.global.config;

import com.minibackoffice.backend.global.auth.AdminAuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AdminAuthorizationInterceptor adminAuthorizationInterceptor;

    public WebMvcConfig(AdminAuthorizationInterceptor adminAuthorizationInterceptor) {
        this.adminAuthorizationInterceptor = adminAuthorizationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminAuthorizationInterceptor)
                .addPathPatterns("/api/**");
    }
}
