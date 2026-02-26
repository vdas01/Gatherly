package com.example.gatherly.service;


import com.example.gatherly.dtos.MailDto;
import com.example.gatherly.entity.Event;
import com.example.gatherly.entity.EventUser;
import com.example.gatherly.entity.User;
import com.example.gatherly.repository.EventRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventScheduler {
   private final EventRepository eventRepository;
   private final JavaMailSender mailSender;
   private final UserRepository userRepository;
   private SpringTemplateEngine springTemplateEngine;
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
      Map<Long,Event> eventMap = eventList.stream().collect(Collectors.toMap(
         Event::getId,
         Function.identity(),
         (existing, replacement) -> existing
      ));
      List<Long> eventIds = eventList.stream().map(Event::getId).toList();
      List<EventUser> eventUserList = eventRepository.findUsersWithEventIds(eventIds);
      List<Long> userIdsList = new ArrayList<>();
      Map<Long,List<Long>> eventUserListMap = new HashMap<>();
      eventUserList.forEach(eventUser -> {
         if(!eventUserListMap.containsKey(eventUser.getId())) {
            List<Long> userIdList = eventUserListMap.get(eventUser.getId());
            Long userId = eventUser.getUser().getId();
            userIdList.add(userId);
            userIdList.add(userId);
            eventUserListMap.put(eventUser.getId(), userIdList);
         }else {
            userIdsList.add(eventUser.getUser().getId());
            eventUserListMap.get(eventUser.getId()).add(eventUser.getUser().getId());
         }
      });
      List<User> userList = userRepository.findAllByIdIn(userIdsList);
      eventUserListMap.forEach((eventId, userIdList) -> {
            Event event = eventMap.get(eventId);
            for (User user : userList) {
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
                  helper.setText(content, true); // true = HTML

                  mailSender.send(message);

               } catch (MessagingException e) {
                  throw new RuntimeException("Failed to send email", e);
               }
            }

      });
   }

}
