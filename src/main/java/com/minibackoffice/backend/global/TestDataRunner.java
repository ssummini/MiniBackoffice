package com.minibackoffice.backend.global;

import com.minibackoffice.backend.domain.user.User;
import com.minibackoffice.backend.domain.user.repository.UserRepository;
import com.minibackoffice.backend.global.enums.UserRole;
import com.minibackoffice.backend.global.enums.UserStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class TestDataRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TestDataRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (!userRepository.existsByEmail("admin@test.com")) {
            User admin = new User(
                    "admin@test.com",
                    passwordEncoder.encode("1234"),
                    "관리자",
                    UserRole.ADMIN,
                    UserStatus.ACTIVE
            );
            userRepository.save(admin);
            System.out.println("Admin created: admin@test.com / 1234");
        } else {
            System.out.println("Admin already exists");
        }
    }
}
