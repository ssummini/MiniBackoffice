package com.minibackoffice.backend.domain.user.dto;

import com.minibackoffice.backend.domain.user.User;

public class UserMeResponse {
    public Long id;
    public String email;
    public String name;
    public String role;
    public String status;

    private UserMeResponse(Long id, String email, String name, String role, String status) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.status = status;
    }

    // User 엔티티를 기반으로 응답 DTO 생성
    public static UserMeResponse from(User user) {
        return new UserMeResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole().name(),
                user.getStatus().name()
        );
    }
}

