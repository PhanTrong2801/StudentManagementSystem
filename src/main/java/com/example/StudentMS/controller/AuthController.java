package com.example.StudentMS.controller;

import com.example.StudentMS.dto.AuthRequest;
import com.example.StudentMS.dto.AuthResponse;
import com.example.StudentMS.entity.UserEntity;
import com.example.StudentMS.repository.UserRepository;
import com.example.StudentMS.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(),req.getPassword())
        );
        String token = jwtUtils.generateJwtToken(req.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest req){
        if(userRepository.existsByUsername(req.getUsername())){
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        UserEntity u = UserEntity.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword())) // <-- MÃ HOÁ MẬT KHẨU
                .role("ROLE_STUDENT")
                .build();

        userRepository.save(u);
        return ResponseEntity.ok("User registered successfully");
    }
}
