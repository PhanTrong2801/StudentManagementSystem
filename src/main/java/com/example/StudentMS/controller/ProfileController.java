package com.example.StudentMS.controller;

import com.example.StudentMS.dto.StudentResponse;
import com.example.StudentMS.entity.StudentEnttity;
import com.example.StudentMS.repository.StudentRepository;
import com.example.StudentMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProfileController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<?> profile(@AuthenticationPrincipal UserDetails userDetails){
        if (userDetails == null){
            return ResponseEntity.status(401).body("Unauthorized");
        }
        String username = userDetails.getUsername();
        StudentEnttity studentEnttity = studentRepository.findByEmail(username).orElse(null);

        if (studentEnttity == null){
            return ResponseEntity.notFound().build();
        }
        StudentResponse res = StudentResponse.builder()
                .id(studentEnttity.getId())
                .name(studentEnttity.getName())
                .email(studentEnttity.getEmail())
                .phone(studentEnttity.getPhone())
                .address(studentEnttity.getAddress())
                .build();
        return ResponseEntity.ok(res);
    }

}
