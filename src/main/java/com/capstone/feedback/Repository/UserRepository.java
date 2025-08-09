package com.capstone.feedback.Repository;

import com.capstone.feedback.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // This method will be used by Spring Security to find a user by their email
    Optional<User> findByEmail(String email);
}
