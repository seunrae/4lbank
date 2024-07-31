package com.example.seun4L._LBank.user.repository;

import com.example.seun4L._LBank.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);
    Optional<User> findByAccountNumber(String accountNumber);
    Optional<User> findByEmailAndAccountNumber(String email, String accountNumber);
}
