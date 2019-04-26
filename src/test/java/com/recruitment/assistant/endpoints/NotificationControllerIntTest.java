package com.recruitment.assistant.endpoints;

import com.recruitment.assistant.RecruitmentAssistantApiApplication;
import com.recruitment.assistant.model.JobApplication;
import com.recruitment.assistant.model.Notification;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import static com.recruitment.assistant.utils.ITestUtils.createURLWithPort;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = {RecruitmentAssistantApiApplication.class})
@TestPropertySource(locations = "classpath:application-test.properties")
public class NotificationControllerIntTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${server.port}")
    private String port;

    @Value("${application.test.host}")
    private String host;

    private HttpHeaders httpHeaders;

    @Test
    public void deliverNotificationTest_returnsOk(){

        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediatypeList = new ArrayList<>();
        mediatypeList.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediatypeList);

        HttpEntity<Notification> entityNotif = new HttpEntity<Notification>(createTestDataForNotif(), httpHeaders);
        ResponseEntity<JobApplication> response = restTemplate.exchange(createURLWithPort("/notification/v1/deliverNotf/v1",
                host, port), HttpMethod.POST, entityNotif, JobApplication.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    private Notification createTestDataForNotif() {
        return new Notification("Sample notification body");
    }

    @Test
    public void deliverNotificationTest_returnsBadRequestWhenBodyNull(){

        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediatypeList = new ArrayList<>();
        mediatypeList.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediatypeList);

        HttpEntity<Notification> entityNotif = new HttpEntity<Notification>(createTestDataForNotifBodyNull(), httpHeaders);
        ResponseEntity<JobApplication> response = restTemplate.exchange(createURLWithPort("/notification/v1/deliverNotf/v1",
                host, port), HttpMethod.POST, entityNotif, JobApplication.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    private Notification createTestDataForNotifBodyNull() {
        return new Notification(null);
    }


}
