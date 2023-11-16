package com.anshuman.authentication.authorization.Controller;

import com.anshuman.authentication.authorization.Configuration.CustomUserDetailsService;
import com.anshuman.authentication.authorization.DTO.JwtResponse;
import com.anshuman.authentication.authorization.DTO.RefreshTokenRequest;
import com.anshuman.authentication.authorization.Entity.RefreshToken;
import com.anshuman.authentication.authorization.Repository.RefreshTokenRepository;
import com.anshuman.authentication.authorization.Security.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtHelper jwtHelper;

    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping(value = "/refreshToken/updateAccessToken")
    public ResponseEntity<JwtResponse> updateAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest){

        //Check that the refresh token is present in the database or not.
       Optional<RefreshToken> existingRefreshToken = refreshTokenRepository.findByToken(refreshTokenRequest.getToken());
        System.out.println("The returned refresh Token is"+existingRefreshToken.get());

       //If the refresh token is present and still has not expired.
       if(existingRefreshToken.isPresent() && existingRefreshToken.get().getExpiryDate().isAfter(LocalDateTime.now())){
           UserDetails userDetails = customUserDetailsService.loadUserByUsername(existingRefreshToken.get().getUser().getEmailId());
           //Get the access token.
           String token = this.jwtHelper.generateToken(userDetails);
           JwtResponse jwtResponse = JwtResponse.builder()
                   .accessToken(token)
                   .refreshToken(refreshTokenRequest.getToken())
                   .userName(existingRefreshToken.get().getUser().getEmailId())
                   .build();
           return ResponseEntity.status(HttpStatus.CREATED).body(jwtResponse);
       }
       //If the refresh token is present but expired.
       else if(existingRefreshToken.isPresent()){
           //Delete the expired refresh token.
           refreshTokenRepository.delete(existingRefreshToken.get());
           throw new RuntimeException("Refresh token is expired Please login again");
       }
       else{
            throw new RuntimeException("No Refresh Token present. Please login again");
       }
    }
}
