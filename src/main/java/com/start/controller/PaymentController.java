package com.start.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.annotations.SerializedName;
import com.start.dto.CreatePayment;
import com.start.dto.CreatePaymentResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;


@Controller
public class PaymentController {
	
	@PostMapping("/create-payment-intent")
	public CreatePaymentResponse createPaymentIntent(@RequestBody CreatePayment createPayment) throws StripeException {
		     
		      PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
		      .setCurrency("inr")
		      .setAmount(123*100L)
		      .build();
		      // Create a PaymentIntent with the order amount and currency
		      PaymentIntent intent = PaymentIntent.create(createParams);
		      return new CreatePaymentResponse(intent.getClientSecret());
		  }

}
