package com.recruitment.assistant.service;

import com.recruitment.assistant.exception.NotificationException;
import com.recruitment.assistant.model.JobApplication;
import com.recruitment.assistant.model.Notification;
import com.recruitment.assistant.model.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.Arrays;

@Service
public class NotifClientSvcImpl implements INotifClientSvc {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public void processNotification(JobApplication updatedJobApplication)
            throws NotificationException {

        try {

            NotificationRequest notifReq = createNotfReq(updatedJobApplication);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            Notification notificationSvcReq = new Notification(notifReq.getNotificationString());
            HttpEntity<Notification> request = new HttpEntity<>(notificationSvcReq, headers);
            ResponseEntity result = restTemplate.postForEntity(notifReq.getNotfSvcUrl(),request,null);
            int statusCode = result.getStatusCodeValue();

            if (200 != statusCode) {
                throw new NotificationException("Notification was not processed successfully - status code returned is " +
                        "not 200 but it is :"+statusCode);
            }
        } catch(Exception exception){
            throw new NotificationException("Exception occured while sending the notification",exception);
        }
    }


    private NotificationRequest createNotfReq(JobApplication updatedJobApplication){

        final String notfString = createNotifiString(updatedJobApplication);
        NotificationRequest notificationRequest = new
                NotificationRequest(notfString,"http://localhost:9098/notification/v1/deliverNotf/v1");

        return notificationRequest;
    }

    private String createNotifiString(JobApplication updatedJobApplication){

        StringBuilder notfStringBuilder = new StringBuilder();
        notfStringBuilder.append("Hello user, the status of the application id : "+updatedJobApplication.getApplicationId());
        notfStringBuilder.append(" has been changed to : "+updatedJobApplication.getApplicationStatus());
        notfStringBuilder.append(" on date : "+ LocalDate.now());
        return notfStringBuilder.toString();
    }
}
