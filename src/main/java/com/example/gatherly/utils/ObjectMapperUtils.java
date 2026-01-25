package com.example.gatherly.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class ObjectMapperUtils {
   private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

   static {
      OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
   }

   public static <T> String convertDtoToJson(T dto){
      if(Objects.nonNull(dto)){
         try{
            return OBJECT_MAPPER.writeValueAsString(dto);
         }catch (JsonProcessingException e){
            e.printStackTrace();
         }
      }
     return "";
   }

   public static  <T> T convertJsonToObjectWithDefault(String json, Class<T> clazz){

      try{
         if(StringUtils.isNotEmpty(json) && Objects.nonNull(clazz)){
               return OBJECT_MAPPER.readValue(json, clazz);
         }
         return clazz.getDeclaredConstructor().newInstance();
      }catch (Exception e){
         e.printStackTrace();
      }
      return null;
   }

}
