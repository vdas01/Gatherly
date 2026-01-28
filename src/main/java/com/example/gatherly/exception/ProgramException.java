package com.example.gatherly.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ProgramException extends RuntimeException {
   private String[] description;
   private HttpStatus httpStatus;

   public ProgramException(){
      this("Something went wrong");
   }

   public ProgramException(String message,String ...description) {
      this(HttpStatus.BAD_REQUEST,message,description);
   }

   public ProgramException(HttpStatus httpStatus,String message,String... description){
      super( message);
      this.httpStatus=httpStatus;
      this.description=description;
   }
}
