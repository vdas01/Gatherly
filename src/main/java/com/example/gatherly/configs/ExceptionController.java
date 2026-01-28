package com.example.gatherly.configs;

import com.example.gatherly.exception.ExceptionResponseDto;
import com.example.gatherly.exception.ProgramException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

   @ExceptionHandler({ProgramException.class})
   public ExceptionResponseDto handleProgramException(ProgramException programException){
      ExceptionResponseDto exceptionResponseDto = ExceptionResponseDto.builder()
         .message(programException.getMessage())
         .errors(programException.getDescription())
         .errorCode(programException.getHttpStatus())
         .build();
      log.error("Program Exception occurred: {}",exceptionResponseDto,programException.getCause());
      return exceptionResponseDto;
   }

   @ExceptionHandler(SQLException.class)
   public ExceptionResponseDto handleSQLException(SQLException sqlException){
      ExceptionResponseDto exceptionResponseDto = ExceptionResponseDto.builder()
         .message(sqlException.getMessage())
         .errorCode(HttpStatus.INTERNAL_SERVER_ERROR)
         .build();
      log.error("SQL Exception occurred: {}",exceptionResponseDto,sqlException.getCause());
      return exceptionResponseDto;
   }



   @ExceptionHandler({Exception.class})
   public ExceptionResponseDto handleException(Exception exception){
      log.error("Unexpected Exception occurred: {}",exception.getMessage(),exception.getCause());
      return ExceptionResponseDto.builder()
         .message("Unexpected error occurred: " + exception.getMessage())
         .errorCode(HttpStatus.INTERNAL_SERVER_ERROR)
         .build();
   }
}
