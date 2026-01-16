package com.minibackoffice.backend.domain.user;

import com.minibackoffice.backend.domain.user.dto.UserCreateRequest;
import com.minibackoffice.backend.domain.user.dto.UserLoginRequest;
import com.minibackoffice.backend.domain.user.dto.UserLoginResponse;
import com.minibackoffice.backend.domain.user.dto.UserResponse;
import com.minibackoffice.backend.domain.user.dto.UserStatusUpdateRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest request) {
        UserResponse response = userService.create(request);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }
    
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpServletRequest request) {
        Object userIdAttr = request.getAttribute("userId");

        if (userIdAttr == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        Long userId;
        try {
            userId = Long.parseLong(userIdAttr.toString());
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.");
        }   

        return ResponseEntity.ok(userService.me(userId));
    }

    // 관리자 기능: 사용자 상태 변경 API
    @PatchMapping("/{id}/status")
    public ResponseEntity<UserResponse> updateStatus(
            HttpServletRequest request,
            @PathVariable Long id,
            @Valid @RequestBody UserStatusUpdateRequest req
    ) {
        // 1) 관리자 체크
        // requireAdmin(request);

        // 2) 상태 변경 로직
        UserResponse response = userService.updateStatus(id, req);

        // 3) 성공 응답
        return ResponseEntity.ok(response);
    }

}
