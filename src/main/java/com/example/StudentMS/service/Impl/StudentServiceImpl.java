package com.example.StudentMS.service.Impl;

import com.example.StudentMS.dto.StudentRequest;
import com.example.StudentMS.dto.StudentResponse;
import com.example.StudentMS.entity.Student;
import com.example.StudentMS.repository.StudentRepository;
import com.example.StudentMS.service.StudentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.management.relation.RelationNotFoundException;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository repo;

    private StudentResponse toResponse(Student s){
        return StudentResponse.builder()
                .id(s.getId())
                .name(s.getName())
                .email(s.getEmail())
                .phone(s.getPhone())
                .address(s.getAddress())
                .build();
    }

    @Override
    public StudentResponse createStudent(Student request) {
        repo.findByEmail(request.getEmail()).ifPresent(s->{  //kiem tra co ton tai gia tri
            throw new DataIntegrityViolationException("Email already exits");
        });

        Student s = Student.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();

        Student saved = repo.save(s);
        return toResponse(saved);

    }

    @Override
    public StudentResponse getStudentById(Long id) {
        Student s = repo.findById(id).orElseThrow(()-> new RelationNotFoundException("Student not found with id "+ id)) ;
        return toResponse(s);
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentRequest request) {
        return null;
    }

    @Override
    public void deleteStudent(Long id) {

    }

    @Override
    public Page<StudentResponse> getAllStudents(Pageable pageable) {
        return null;
    }

    @Override
    public Page<StudentResponse> searchStudents(String q, Pageable pageable) {
        return null;
    }
}
