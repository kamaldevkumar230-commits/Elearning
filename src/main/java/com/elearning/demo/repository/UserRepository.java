package com.elearning.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.elearning.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}