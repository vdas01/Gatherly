package com.example.gatherly.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
   public static final String ACCESS_SECRET_KEY = "811552024e349dc086cfeb494b6d0d13c7fe3f695579f364d939b92daac0a96a";
   public static final String REFRESH_SECRET_KEY = "811552024e349dc086cfeb494b6d0d13c7fe3f695579f364d939b92daac0a97b";
   public static final long ACCESS_EXP = 1000 * 60 * 15; // 15 min
   public static final long REFRESH_EXP = 1000 * 60 * 60 * 24 * 7;
}
