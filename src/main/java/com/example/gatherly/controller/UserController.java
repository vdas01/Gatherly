package com.example.gatherly.controller;

import com.example.gatherly.dtos.AccessTokenResponse;
import com.example.gatherly.dtos.UserDto;
import com.example.gatherly.service.RefreshTokenService;
import com.example.gatherly.service.UserService;
import com.example.gatherly.utils.Constants;
import com.example.gatherly.utils.JwtUtil;
import com.example.gatherly.utils.SecurityUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

import static com.example.gatherly.utils.Constants.REFRESH_EXP;


@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(
   origins = "http://localhost:5173",
   allowCredentials = "true"
)
public class UserController {
   private final AuthenticationManager authenticationManager;
   private final UserService userService;
   private final JwtUtil jwtUtil;
   private final RefreshTokenService refreshTokenService;

   @GetMapping
   public UserDto getUserByUserName() {
      return userService.getUserByUserName(SecurityUtil.getUserName());
   }

   @PostMapping
   public UserDto createOrUpdateUser(@RequestBody UserDto userDto, @RequestParam(required = false) Boolean isUpdateUser) throws JsonProcessingException {
     return userService.createUser(userDto,isUpdateUser);
   }

   @DeleteMapping
   public ResponseEntity<String> deleteUser(@RequestParam String userName) {
      String result = userService.deleteUser(userName);
      return ResponseEntity.ok(result);
   }

//   @GetMapping("/login")
//   public UserDto login(@RequestParam String username, @RequestParam String password) {
//      log.info("username: {}, password: {}", username, password);
//      return userService.login(username, password);
//   }

   @GetMapping("/login")
   public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
      log.info("username: {}, password: {}", username, password);
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

      String accessToken = jwtUtil.generateAccessToken(username);
      String refreshToken = jwtUtil.generateRefreshToken(username);

      // Save refresh token in DB
      refreshTokenService.saveRefreshToken(username, refreshToken, Instant.now().plusSeconds(REFRESH_EXP/1000));

      ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
         .httpOnly(true)
         .secure(false)
         .sameSite("Lax")
         .path("/")
         .maxAge(7 * 24 * 60 * 60)
         .build();

      return ResponseEntity.ok()
         .header(HttpHeaders.SET_COOKIE, cookie.toString())
         .body(new AccessTokenResponse(accessToken, Constants.ACCESS_EXP/1000));

   }

   @PostMapping("/refresh")
   public ResponseEntity<?> refresh(
      @CookieValue("refreshToken") String oldToken) {
        return refreshTokenService.generateRefreshToken(oldToken);
   }


   @PostMapping("/logout")
   public ResponseEntity<?> logout(
      @CookieValue("refreshToken") String refreshToken) {

      refreshTokenService.revokeRefreshToken(refreshToken);

      ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
         .maxAge(0)
         .path("/auth/refresh")
         .build();

      return ResponseEntity.ok()
         .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
         .build();
   }

}
