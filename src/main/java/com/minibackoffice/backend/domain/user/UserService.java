package com.minibackoffice.backend.domain.user;

import com.minibackoffice.backend.domain.user.dto.UserCreateRequest;
import com.minibackoffice.backend.domain.user.dto.UserResponse;
import com.minibackoffice.backend.domain.user.repository.UserRepository;
import com.minibackoffice.backend.global.enums.UserRole;
import com.minibackoffice.backend.global.enums.UserStatus;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse create(UserCreateRequest request) {

        // 1) 이메일 중복 검사
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.");
        }

        // 2) role/status 기본값 세팅
        UserRole role = UserRole.USER;
        if (request.getRole() != null && !request.getRole().isBlank()) {
            role = UserRole.valueOf(request.getRole()); // "USER" / "ADMIN"
        }

        UserStatus status = UserStatus.ACTIVE;
        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            status = UserStatus.valueOf(request.getStatus()); // "ACTIVE" / "BLOCKED"
        }

        // 3) 엔티티 생성
        User user = new User(
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                role,
                status
        );

        // 4) 저장
        User savedUser = userRepository.save(user);

        // 5) 응답 DTO로 변환해서 반환
        return new UserResponse(savedUser);
    }
}
