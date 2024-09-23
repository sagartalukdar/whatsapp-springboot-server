package com.react.Exception;

import java.time.LocalDateTime;

public class ErrorDetails {

	 private String error;
	 private String message;
	 private LocalDateTime timeStamp;

	 public ErrorDetails(String error, String message, LocalDateTime timeStamp) {
		super();
		this.error = error;
		this.message = message;
		this.timeStamp = timeStamp;
	 }
	 
	 
	 
}
