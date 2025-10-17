package com.example.StudentMS.repository;

import com.example.StudentMS.entity.StudentEnttity;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEnttity,Long> {

    Optional<StudentEnttity>findByEmail(String email);

    Page<StudentEnttity>findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email, Pageable pageable);
}
