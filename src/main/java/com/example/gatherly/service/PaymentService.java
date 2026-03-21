package com.example.gatherly.service;

import com.example.gatherly.exception.ProgramException;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

   public Map<String,Object> createOrder(int amount) throws RazorpayException {
      RazorpayClient client = new RazorpayClient("KEY_ID", "KEY_SECRET");

      JSONObject options = new JSONObject();
      options.put("amount", amount * 100); // in paise
      options.put("currency", "INR");
      options.put("receipt", "txn_123456");

      Order order = client.orders.create(options);

      Map<String, Object> response = new HashMap<>();
      response.put("orderId", order.get("id"));
      response.put("amount", order.get("amount"));

      return response;
   }

   public ResponseEntity<String> verifyPayment(Map<String, String> payload) throws RazorpayException {
      String orderId = payload.get("orderId");
      String paymentId = payload.get("paymentId");
      String signature = payload.get("signature");

      String generatedSignature = Utils.getHash(orderId + "|" + paymentId, "SECRET");

      if (generatedSignature.equals(signature)) {
         return ResponseEntity.ok("Payment Successful");
      }
      throw new ProgramException(HttpStatus.BAD_REQUEST, "Invalid signature");
   }

}
