package com.recruitment.assistant.exception;

public class RecAsstntBusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RecAsstntBusinessException() {
		
	}
	
	public RecAsstntBusinessException(String message,Throwable cause) {
		super(message,cause);
	}

}
