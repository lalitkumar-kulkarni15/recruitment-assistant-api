package com.recruitment.assistant.exception;

public class InputValidationException extends Exception {

    public InputValidationException(){
    }

    public InputValidationException(String message,Throwable cause){
        super(message,cause);
    }
}
