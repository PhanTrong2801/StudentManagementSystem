package com.example.StudentMS.service;

import com.example.StudentMS.dto.StudentRequest;
import com.example.StudentMS.dto.StudentResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
    StudentResponse createStudent(StudentRequest request);
    StudentResponse getStudentById(Long id);
    StudentResponse updateStudent(Long id, StudentRequest request);
    void deleteStudent(Long id);
    Page<StudentResponse>getAllStudents(Pageable pageable);
    Page<StudentResponse>searchStudents(String q, Pageable pageable);


}
