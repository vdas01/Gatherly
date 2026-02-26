package com.example.gatherly.repository;

import com.example.gatherly.entity.Event;
import com.example.gatherly.entity.EventUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

   Page<Event> findByEventEndDateBetween(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);


   @Query("SELECT * FROM Event e WHERE e.eventStartDate >= :start AND e.eventStartDate < :end")
   List<Event> findEventsInRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

   @Query("SELECT * FROM EventUsers eu WHERE eu.eventId in :eventIds")
   List<EventUser> findUsersWithEventIds(@Param("eventIds") List<Long> eventIds);
}
