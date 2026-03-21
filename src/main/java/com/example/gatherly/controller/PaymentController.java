package com.example.gatherly.controller;

import com.example.gatherly.service.PaymentService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
   private final PaymentService paymentService;


   @PostMapping("/create-order")
   public Map<String, Object> createOrder(@RequestParam int amount) throws Exception {
      return paymentService.createOrder(amount);
   }


   @PostMapping("/verify")
   public ResponseEntity<String> verifyPayment(@RequestBody Map<String, String> payload) throws RazorpayException {
      return paymentService.verifyPayment(payload);
   }
}
