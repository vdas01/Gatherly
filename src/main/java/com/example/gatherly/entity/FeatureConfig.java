package com.example.gatherly.entity;

import com.example.gatherly.enums.FeatureName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "feature_config")
@Getter
@Setter
public class FeatureConfig {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "feature_name")
   @Enumerated(EnumType.STRING)
   private FeatureName featureName;

   @Column
   private String config;
}
