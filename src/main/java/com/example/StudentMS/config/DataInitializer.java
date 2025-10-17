package com.example.StudentMS.config;

import com.example.StudentMS.entity.UserEntity;
import com.example.StudentMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        String adminUsername = "admin@example.com";
        if (!userRepository.existsByUsername(adminUsername)){
            UserEntity admin = UserEntity.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode("Admin@123"))
                    .role("ROLE_ADMIN")
                    .build();
            userRepository.save(admin);
            System.out.println("Create default admin: " +adminUsername+  " / Admin@123");
        }
    }
}
