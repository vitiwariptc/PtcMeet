package com.vitiwari.repository;

import com.vitiwari.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    RefreshToken findByRefreshToken(String token);
}
