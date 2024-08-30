package com.vitiwari.repository;

import com.vitiwari.model.RefreshToken;
import com.vitiwari.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String userName);
    User findByEmailId(String emailId);

    User findByRefreshToken(RefreshToken refreshToken);
}
