package com.minibackoffice.backend.domain.user.dto;

public class UserLoginResponse {
    private final String token;
    private final UserResponse user;

    public UserLoginResponse(String token, UserResponse user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() { return token; }
    public UserResponse getUser() { return user; }
}
