package com.example.gatherly.repository;

import com.example.gatherly.entity.EventUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventUserRepository extends JpaRepository<EventUser, Long> {
   List<EventUser> findByEventIdIn(List<Long> eventIds);
}
