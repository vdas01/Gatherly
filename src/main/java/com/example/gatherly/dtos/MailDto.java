package com.example.gatherly.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailDto {
   private String username;
   private String eventName;
   private String eventLocation;
   private String eventTime;
}
