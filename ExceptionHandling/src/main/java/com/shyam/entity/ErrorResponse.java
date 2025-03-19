package com.shyam.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class ErrorResponse {

	private LocalDateTime timeStamp;
	private String errorMsg;
	private String errorDetails;
	private String errorCode;
	public ErrorResponse(String errorMsg, String errorDetails, String errorCode) {
		super();
		this.timeStamp=LocalDateTime.now();
		this.errorMsg = errorMsg;
		this.errorDetails = errorDetails;
		this.errorCode = errorCode;
	}
	
	
}
