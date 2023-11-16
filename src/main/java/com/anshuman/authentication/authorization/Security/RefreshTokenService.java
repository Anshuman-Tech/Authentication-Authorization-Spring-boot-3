package com.anshuman.authentication.authorization.Security;

import com.anshuman.authentication.authorization.Entity.RefreshToken;
import com.anshuman.authentication.authorization.Repository.RefreshTokenRepository;
import com.anshuman.authentication.authorization.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    public ResponseEntity<RefreshToken> createRefreshToken(String userName){
        //Create a refreshToken object.
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByEmailId(userName).get())
                .token(UUID.randomUUID().toString())
                .expiryDate(LocalDateTime.now().plusMinutes(5))
                .build();

        //Save the refresh token to DB
        RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRefreshToken);
    }
}
