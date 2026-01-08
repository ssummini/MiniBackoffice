package com.minibackoffice.backend.domain.user;

import com.minibackoffice.backend.domain.user.dto.UserCreateRequest;
import com.minibackoffice.backend.domain.user.dto.UserLoginRequest;
import com.minibackoffice.backend.domain.user.dto.UserResponse;
import com.minibackoffice.backend.domain.user.repository.UserRepository;
import com.minibackoffice.backend.global.enums.UserRole;
import com.minibackoffice.backend.global.enums.UserStatus;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse create(UserCreateRequest request) {

    String encodedPassword = passwordEncoder.encode(request.getPassword());

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
                encodedPassword,
                request.getName(),
                role,
                status
        );

        // 4) 저장
        User savedUser = userRepository.save(user);

        // 5) 응답 DTO로 변환해서 반환
        return new UserResponse(savedUser);
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));
        return new UserResponse(user);
    }

    public UserResponse login(UserLoginRequest request) {

        // 1) 이메일로 사용자 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다.")
                );

        // 2) 비밀번호 비교 (핵심!!)
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "이메일 또는 비밀번호가 올바르지 않습니다."
            );
        }

        // 3) 로그인 성공 → 사용자 정보 반환
        return new UserResponse(user);
    }

}
