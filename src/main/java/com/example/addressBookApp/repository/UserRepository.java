package com.example.addressBookApp.repository;

import com.example.addressBookApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    //UC6
    Optional<User> findByResetToken(String resetToken);
}
