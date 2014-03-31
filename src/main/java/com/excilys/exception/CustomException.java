package com.excilys.exception;

public class CustomException extends RuntimeException{

	String message;
	String stack;
	
	public CustomException(String message, String stack) {
		super();
		this.message = message;
		this.stack = stack;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getStack() {
		return stack;
	}
	
	public void setStack(String stack) {
		this.stack = stack;
	}	
}
