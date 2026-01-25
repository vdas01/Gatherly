package com.example.gatherly.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SubscriptionType {
    MONTHLY(1),
   QUARTERLY(3),
    YEARLY(12);

   private final Integer validityPeriod;

   public static Integer getValidityPeriod(SubscriptionType subscriptionType) {
      for(SubscriptionType subscriptionTypeEnum : SubscriptionType.values()) {
         if(subscriptionTypeEnum.equals(subscriptionType)) {
            return subscriptionTypeEnum.validityPeriod;
         }
      }
      return 0;
   }
}
