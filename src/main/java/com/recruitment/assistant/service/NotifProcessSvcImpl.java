package com.recruitment.assistant.service;

import com.recruitment.assistant.model.Notification;
import org.springframework.stereotype.Service;

@Service
public class NotifProcessSvcImpl implements INotifProcessSvc {

    @Override
    public Notification processNotification(Notification notificationBody) {
        System.out.println(notificationBody.getNotificationBody());
        return notificationBody;
    }
}
