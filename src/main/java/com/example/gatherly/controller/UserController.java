package com.example.gatherly.controller;

import com.example.gatherly.dtos.UserDto;
import com.example.gatherly.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
   private final UserService userService;

   @GetMapping
   public UserDto getUserByUserName(@RequestParam String userName) {
      return userService.getUserByUserName(userName);
   }

   @PostMapping
   public UserDto createOrUpdateUser(@RequestBody UserDto userDto, @RequestParam Boolean isUpdateUser) throws JsonProcessingException {
     return userService.createUser(userDto,isUpdateUser);
   }

   @DeleteMapping
   public ResponseEntity<String> deleteUser(@RequestParam String userName) {
      String result = userService.deleteUser(userName);
      return ResponseEntity.ok(result);
   }

   @GetMapping("/login")
   public UserDto login(@RequestParam String username, @RequestParam String password) {
      return userService.login(username, password);
   }
}
