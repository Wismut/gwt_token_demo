package com.example.jwtdemo.repository;

import com.example.jwtdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(Stream name);
}
