package com.example.gatherly.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@ToString
@Accessors(chain=true)
public class ExceptionResponseDto {
   private String message;
   private String[] errors;
   private HttpStatus errorCode;
}
