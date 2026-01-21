package com.minibackoffice.backend.domain.user.dto;

import com.minibackoffice.backend.domain.user.User;
import com.minibackoffice.backend.global.enums.UserRole;
import com.minibackoffice.backend.global.enums.UserStatus;

public class UserResponse {

    private Long id;
    private String email;
    private String name;
    private UserRole role;
    private UserStatus status;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.role = user.getRole();
        this.status = user.getStatus();
    }

    public static UserResponse from(User user) {
        return new UserResponse(user);
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public UserRole getRole() { return role; }
    public UserStatus getStatus() { return status; }
}

