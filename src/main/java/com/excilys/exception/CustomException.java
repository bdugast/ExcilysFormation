package com.excilys.exception;

public class CustomException extends RuntimeException{

	private static final long serialVersionUID = 1L;
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
