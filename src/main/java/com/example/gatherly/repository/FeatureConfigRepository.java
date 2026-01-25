package com.example.gatherly.repository;

import com.example.gatherly.entity.FeatureConfig;
import com.example.gatherly.enums.FeatureName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeatureConfigRepository extends JpaRepository<FeatureConfig, Long> {
   Optional<FeatureConfig> findByFeatureName(FeatureName featureName);
}
