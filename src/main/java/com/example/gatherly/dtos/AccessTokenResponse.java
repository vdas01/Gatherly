package com.example.gatherly.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AccessTokenResponse {
   private String accessToken;
   private String tokenType = "Bearer";
   private long expiresIn; // seconds

   public  AccessTokenResponse(String accessToken, long expiresIn) {
      this.expiresIn = expiresIn;
      this.accessToken = accessToken;
   }
}
