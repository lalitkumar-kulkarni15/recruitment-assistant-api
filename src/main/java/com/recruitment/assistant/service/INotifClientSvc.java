package com.recruitment.assistant.service;

import com.recruitment.assistant.exception.NotificationException;
import com.recruitment.assistant.model.JobApplication;

public interface INotifClientSvc {

    public void processNotification(JobApplication updatedJobApplication)
            throws NotificationException;
}
