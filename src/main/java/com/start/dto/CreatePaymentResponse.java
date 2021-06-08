package com.start.dto;

import com.google.gson.annotations.SerializedName;

public class CreatePaymentResponse {
	
	private String clientSecret;
    public CreatePaymentResponse(String clientSecret) {
      this.clientSecret = clientSecret;
    }
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
    

}
