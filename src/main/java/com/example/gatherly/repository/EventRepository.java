package com.example.gatherly.repository;

import com.example.gatherly.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

   Page<Event> findByEventEndDateBetween(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);


   @Query("SELECT e FROM event e WHERE e.eventStartDate >= :start AND e.eventStartDate < :end")
   List<Event> findEventsInRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
