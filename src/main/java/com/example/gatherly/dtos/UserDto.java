package com.example.gatherly.dtos;

import com.example.gatherly.enums.UserRole;
import com.example.gatherly.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
   private Long id;
   private String userName;
   private String firstName;
   private String lastName;
   private String password;
   private String email;
   private String phone;
   private String address;
   private UserRole role = UserRole.PARTICIPANT;
   private String gender;
   private UserStatus userStatus;
}
