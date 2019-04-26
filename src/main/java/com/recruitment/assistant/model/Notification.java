package com.recruitment.assistant.model;

import javax.validation.constraints.NotNull;

public class Notification {

    @NotNull(message = "Notification body cannot be null")
    private String notificationBody;

    public String getNotificationBody() {
        return notificationBody;
    }

    public void setNotificationBody(String notificationBody) {
        this.notificationBody = notificationBody;
    }

    public Notification(String notificationBody) {
        this.notificationBody = notificationBody;
    }
    public Notification() {
    }


}
