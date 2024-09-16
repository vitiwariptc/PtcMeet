package com.vitiwari.services;

import com.vitiwari.model.RefreshToken;
import com.vitiwari.model.User;
import com.vitiwari.repository.RefreshTokenRepository;
import com.vitiwari.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final long expTime = 15*60*1000;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public RefreshToken generateToken(){
        RefreshToken rt = new RefreshToken();
        rt.setRefreshToken(UUID.randomUUID().toString());
        rt.setExpiry(Instant.now().plusMillis(expTime));

        return rt;
    }

    public RefreshToken verifyRefreshToken(String token) {
        RefreshToken rt = refreshTokenRepository.findByRefreshToken(token);
        if(rt != null && rt.getExpiry().compareTo(Instant.now()) > 0)
            return rt;
        return null;
    }

    public RefreshToken generateOrUpdateRefreshToken(User user) {
        RefreshToken rt;
        if(user.getRefreshToken() == null){
            rt = generateToken();
            rt.setUser(user);
        }else{
            rt = user.getRefreshToken();
            rt.setRefreshToken(UUID.randomUUID().toString());
            rt.setExpiry(Instant.now().plusMillis(expTime));
        }
        refreshTokenRepository.save(rt);
        return rt;
    }

    
    public void deleteRefreshToken(RefreshToken token) {
        System.out.println(token);
        refreshTokenRepository.deleteById(token.getRefreshTokenId());
    }
}
