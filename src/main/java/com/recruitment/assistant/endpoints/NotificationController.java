package com.recruitment.assistant.endpoints;

import com.recruitment.assistant.service.INotifProcessSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.recruitment.assistant.model.Notification;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/notification/v1")
public class NotificationController {

    @Autowired
    private INotifProcessSvc notifProcessSvc;

    @PostMapping("/deliverNotf/v1")
    public ResponseEntity deliverNotification(@Valid @RequestBody Notification notification){

        this.notifProcessSvc.processNotification(notification);
        return ResponseEntity.ok().build();
    }
}
