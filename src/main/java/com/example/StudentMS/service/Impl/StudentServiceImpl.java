package com.example.StudentMS.service.Impl;

import com.example.StudentMS.dto.StudentRequest;
import com.example.StudentMS.dto.StudentResponse;
import com.example.StudentMS.entity.Student;
import com.example.StudentMS.errorhandling.ResourceNotFoundException;
import com.example.StudentMS.repository.StudentRepository;
import com.example.StudentMS.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    public StudentResponse createStudent(StudentRequest request) {
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
        Student s = repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Student not found with id " + id));
        return toResponse(s);
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentRequest request) {
        Student s = repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Student not found with id "+id));

        if (!s.getEmail().equals(request.getEmail())){
            repo.findByEmail(request.getEmail()).ifPresent(existing -> {
                throw new DataIntegrityViolationException("Email already exits");
            });
        }
        s.setName(request.getName());
        s.setEmail(request.getEmail());
        s.setPhone(request.getPhone());
        s.setAddress(request.getAddress());

        Student saved = repo.save(s);
        return toResponse(saved);
    }

    @Override
    public void deleteStudent(Long id) {
        Student s = repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Student not found with id " + id));
        repo.delete(s);

    }

    @Override
    public Page<StudentResponse> getAllStudents(Pageable pageable ) {
        Page<Student> page = repo.findAll(pageable);
        return page.map(this::toResponse);
    }

    @Override
    public Page<StudentResponse> searchStudents(String q, Pageable pageable) {
        Page<Student> page = repo.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(q,q,pageable);
        return page.map(this::toResponse);
    }

}
