package com.example.StudentMS.repository;

import com.example.StudentMS.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long > {
    Optional<User>findByUsername(String username);
    boolean existByUsername(String username);
}
