package com.example.StudentMS.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
}
