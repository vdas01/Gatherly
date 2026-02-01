package com.example.gatherly.repository;

import com.example.gatherly.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

   Optional<RefreshToken> findByToken(String token);

   @Modifying
   @Transactional
   @Query("UPDATE RefreshToken rt SET rt.revoked = true WHERE rt.username = :username")
   void revokeAllRefreshTokensByUsername(@Param("username") String username);

   @Modifying
   @Transactional
   @Query("update RefreshToken rt set rt.revoked = true where rt.token = :refreshToken")
   void revokeRefreshToken(@Param("refreshToken") String refreshToken);
}
