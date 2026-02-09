package com.example.gatherly.utils;

import java.util.Arrays;
import java.util.List;

public class StringUtils {

   public List<String> splitStringToList(String res, String separator) {
      String[] split = res.split(separator);
      return Arrays.asList(split);
   }
}
