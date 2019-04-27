package com.recruitment.assistant.exception;

public class DataNotFoundException extends Exception {

    public DataNotFoundException(){
    }

    public DataNotFoundException(String message,Throwable cause){
        super(message,cause);
    }

    public DataNotFoundException(String message){
        super(message);
    }
}
