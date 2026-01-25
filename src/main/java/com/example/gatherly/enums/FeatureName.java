package com.example.gatherly.enums;

import com.example.gatherly.dtos.SubscriptionConfigDto;
import com.example.gatherly.interfaces.AbstractFeatureConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FeatureName {
   SUBSCRIPTION_DATA(new TypeReference<SubscriptionConfigDto>(){});

   private final TypeReference<? extends AbstractFeatureConfig> typeReference;

}
