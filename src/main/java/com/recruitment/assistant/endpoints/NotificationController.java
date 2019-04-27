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

/**
 * This is the rest controller class for processing the notifications.
 * Please refer the swagger link {@link "http://localhost:9098/swagger-ui.html"} for
 * detailed information regarding the API , input and output parameters and Http status codes.
 *
 * @since 26-04-2019
 * @version 1.0
 * @author lalit
 *
 */
@Validated
@RestController
@RequestMapping("/notification/v1")
public class NotificationController {

    @Autowired
    private INotifProcessSvc notifProcessSvc;

    /**
     * This is the rest end point of the notification component which
     * processes and delivers the notification which it has received
     * as an input in the request body {@link Notification}
     * @param notification : This object contains the notification body which needs to be
     *                       processed and published.
     * @return ResponseEntity : Returms Http.OK - 200 if the notifiation is processed successfully.
     */
    @PostMapping("/deliverNotf/v1")
    public ResponseEntity<Notification> deliverNotification(@Valid @RequestBody Notification notification){

        // Invoke the service layer to process the notifications further.
        final Notification notifSent = this.notifProcessSvc.processNotification(notification);
        return ResponseEntity.ok().body(notifSent);
    }
}
