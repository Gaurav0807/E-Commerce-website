package com.start;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.stripe.Stripe;

@SpringBootApplication
public class ShoppingApplication {

	@PostConstruct
	public void setup()
	{
		 Stripe.apiKey = "sk_test_51IuYrkSBMZn0qlUjny91rgMq7vP7jtEwdWseSUJis1f644iO3llRzZOIfePtdHmJlZQeNcqJ8TQZdEeMjLmhqJGg00Z1DkH6lg";
	}

	public static void main(String[] args) {
		SpringApplication.run(ShoppingApplication.class, args);
	}

}
