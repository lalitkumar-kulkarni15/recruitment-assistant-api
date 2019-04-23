package com.recruitment.assistant.model;

public class NotificationRequest {

    private String notificationString;

    private String notfSvcUrl;

    public NotificationRequest(String notificationString, String notfSvcUrl) {
        this.notificationString = notificationString;
        this.notfSvcUrl = notfSvcUrl;
    }

    public String getNotificationString() {
        return notificationString;
    }

    public void setNotificationString(String notificationString) {
        this.notificationString = notificationString;
    }

    public String getNotfSvcUrl() {
        return notfSvcUrl;
    }

    public void setNotfSvcUrl(String notfSvcUrl) {
        this.notfSvcUrl = notfSvcUrl;
    }

}
