package com.recruitment.assistant.endpoints;

import com.recruitment.assistant.model.Notification;
import com.recruitment.assistant.service.INotifProcessSvc;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(NotificationController.class)
public class NotificationControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private INotifProcessSvc notifProcessSvc;

    /**
     * This test case unit tests the notification API and
     * verifies the HTTP status 200 OK.
     *
     * @throws Exception
     */
    @Test
    public void testDeliverNotf_returnsPositiveResp() throws Exception {

        Mockito.when(notifProcessSvc.processNotification(Mockito.any()))
                .thenReturn(new Notification("Sample notification"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/notification/v1/deliverNotf/v1")
                .accept(MediaType.APPLICATION_JSON).content("{\n" +
                        "\t\"notificationBody\":\"Hi This is my notification\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
