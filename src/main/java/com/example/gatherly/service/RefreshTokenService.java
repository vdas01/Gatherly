package com.example.gatherly.service;

import com.example.gatherly.dtos.AccessTokenResponse;
import com.example.gatherly.entity.RefreshToken;
import com.example.gatherly.exception.ProgramException;
import com.example.gatherly.repository.RefreshTokenRepository;
import com.example.gatherly.utils.Constants;
import com.example.gatherly.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
   private final RefreshTokenRepository refreshTokenRepository;
   private final JwtUtil jwtUtil;

   public void saveRefreshToken(String username, String refreshToken, Instant expiration) {
      RefreshToken token = new RefreshToken();
      token.setUsername(username);
      token.setToken(refreshToken);
      token.setExpiration(expiration);
      refreshTokenRepository.save(token);
   }

   public ResponseEntity<?> generateRefreshToken(String oldRefreshToken) {
      RefreshToken tokenEntity =
         refreshTokenRepository.findByToken(oldRefreshToken)
            .orElseThrow(() -> new ProgramException(HttpStatus.UNAUTHORIZED,"Invalid refresh token"));

      // 🚨 reuse detection
      if (tokenEntity.isRevoked()) {
         refreshTokenRepository.revokeAllRefreshTokensByUsername(tokenEntity.getUsername());
         throw new ProgramException(HttpStatus.UNAUTHORIZED,"Invalid refresh token");
      }

      // expire check
      if (tokenEntity.getExpiration().isBefore(Instant.now())) {
         throw new ProgramException(HttpStatus.UNAUTHORIZED,"Refresh token is expired");
      }

      // invalidate old token
      tokenEntity.setRevoked(true);
      refreshTokenRepository.save(tokenEntity);

      // issue new tokens
      String newAccessToken =
         jwtUtil.generateAccessToken(tokenEntity.getUsername());

      String newRefreshToken =
         jwtUtil.generateRefreshToken(tokenEntity.getUsername());

      saveRefreshToken(
         tokenEntity.getUsername(),
         newRefreshToken,
         Instant.now().plus(7, ChronoUnit.DAYS)
      );

      ResponseCookie cookie = ResponseCookie.from("refreshToken", newRefreshToken)
         .httpOnly(true)
         .secure(true)
         .path("/auth/refresh")
         .maxAge(Constants.REFRESH_EXP/1000)
         .build();

      return ResponseEntity.ok()
         .header(HttpHeaders.SET_COOKIE, cookie.toString())
         .body(new AccessTokenResponse(newAccessToken, Constants.REFRESH_EXP/1000));
   }

   public void revokeRefreshToken(String refreshToken) {
      refreshTokenRepository.revokeRefreshToken(refreshToken);
   }


}
