package com.minibackoffice.backend.domain.user.dto;

public class UserCreateRequest {

    private String email;
    private String password;
    private String name;
    private String role;    // "USER", "ADMIN"
    private String status;  // "ACTIVE", "BLOCKED"

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }
}
