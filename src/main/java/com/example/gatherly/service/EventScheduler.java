package com.example.gatherly.service;


import com.example.gatherly.dtos.MailDto;
import com.example.gatherly.entity.Event;
import com.example.gatherly.entity.EventUser;
import com.example.gatherly.entity.User;
import com.example.gatherly.repository.EventRepository;
import com.example.gatherly.repository.EventUserRepository;
import com.example.gatherly.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventScheduler {
   private final EventRepository eventRepository;
   private final JavaMailSender mailSender;
   private final UserRepository userRepository;
   private final EventUserRepository eventUserRepository;
   private final SpringTemplateEngine springTemplateEngine;
   @Value("${spring.mail.remainder.from}")
   private String fromEmail;
   @Value("${spring.mail.remainder.subject}")
   private String subject;

   //evryday at 9am
   @Scheduled(cron = "0 0 9 * * *")
   public void sendRemainderEmailToUser(){
      LocalDate today = LocalDate.now();

      LocalDateTime start = today.atStartOfDay();
      LocalDateTime end = today.plusDays(1).atStartOfDay();
      List<Event> eventList = eventRepository.findEventsInRange(start, end);
      if(eventList.isEmpty()) return;
      Map<Long,Event> eventMap = eventList.stream().collect(Collectors.toMap(
         Event::getId,
         Function.identity(),
         (existing, replacement) -> existing
      ));
      List<Long> eventIds = new ArrayList<>(eventMap.keySet());
      List<EventUser> eventUserList = eventUserRepository.findByEventIdIn(eventIds);
      if(eventUserList.isEmpty()) return;
      // Map<EventId, List<UserId>>
      Map<Long, List<Long>> eventUserListMap = eventUserList.stream()
         .collect(Collectors.groupingBy(
            eu -> eu.getEvent().getId(),
            Collectors.mapping(eu -> eu.getUser().getId(), Collectors.toList())
         ));
      Set<Long> userIdsList = eventUserListMap.values().stream()
         .flatMap(List::stream).collect(Collectors.toSet());
      Map<Long,User> userMap = userRepository.findAllByIdIn(userIdsList)
         .stream().collect(Collectors.toMap(User::getId, Function.identity()));
      eventUserListMap.forEach((eventId, userIdList) -> {
            Event event = eventMap.get(eventId);
            if(Objects.isNull(event)) {
               return;
            }
            for (Long userId : userIdList) {
               User user = userMap.get(userId);
               if(Objects.isNull(user)) { return;}
               try{
                  MailDto mailDto = new MailDto();
                  mailDto.setUsername(user.getFirstName());
                  mailDto.setEventName(event.getEventName());
                  mailDto.setEventLocation(event.getEventLocation());
                  mailDto.setEventTime(event.getEventStartDate().toLocalTime().toString());
                  Context context = new Context();
                  context.setVariable("mailDto", mailDto);
                  String content = springTemplateEngine.process("RemainderMail.html", context);

                  MimeMessage message = mailSender.createMimeMessage();
                  MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                  helper.setFrom(fromEmail);
                  helper.setTo(user.getEmail());
                  helper.setSubject(subject);
                  helper.setText(content, true);

                  mailSender.send(message);
               } catch (MessagingException e) {
                  throw new RuntimeException("Failed to send email", e);
               }
            }

      });
   }

}
