package com.example.gatherly.controller;

import com.example.gatherly.dtos.EventDto;
import com.example.gatherly.dtos.PageResponse;
import com.example.gatherly.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event")
public class EventController {
   private final EventService eventService;

   @GetMapping
   public PageResponse<EventDto> getAllEvents(Pageable pageable) {
      return eventService.getAllEvents(pageable);
   }

   @PostMapping
   public EventDto createOrUpdateEvent(@RequestBody EventDto eventDto) {
      return eventService.createEvent(eventDto);
   }
}
