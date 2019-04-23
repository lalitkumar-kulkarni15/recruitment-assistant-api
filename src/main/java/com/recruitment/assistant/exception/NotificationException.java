package com.recruitment.assistant.exception;

import com.recruitment.assistant.model.Notification;

public class NotificationException extends Exception {

    public NotificationException(){
    }

    public NotificationException(String message){
        super(message);
    }

    public NotificationException(String message,Throwable cause){
        super(message,cause);
    }
}
