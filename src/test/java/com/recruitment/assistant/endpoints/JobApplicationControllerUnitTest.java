package com.recruitment.assistant.endpoints;

import com.recruitment.assistant.enums.JobApplicationStatus;
import com.recruitment.assistant.model.JobApplication;
import com.recruitment.assistant.service.IJobApplicatnSvc;
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
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class contains the unit test cases for testing the methods
 * of {@link JobApplicatnController}
 */
@RunWith(SpringRunner.class)
@WebMvcTest(JobApplicatnController.class)
public class JobApplicationControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IJobApplicatnSvc jobAppsvc;

    /**
     * This test case persists the fresh job application in the data store
     * and returns http status code 201.
     * @throws Exception
     */
    @Test
    public void testSubmitJobApp_returnsPositiveResp() throws Exception {

        when(jobAppsvc.submitJobApplicatn(Mockito.any())).thenReturn(1L);
        JobApplication jobApplication = new JobApplication(1L,"lalit@gmail.com","SampleResume", JobApplicationStatus.APPLIED);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/job-application/v1/newJobApplication/v1")
                .accept(MediaType.APPLICATION_JSON).content("{\n" +
                        "\t\"jobId\" : \"1\",\n" +
                        "\t\"candidateEmail\" : \"pallavi.mishra@gmail.com\",\n" +
                        "\t\"resumeTxt\" :\"Sample resume\",\n" +
                        "\t\"applicationStatus\" : \"APPLIED\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }

    /**
     * This test case fetches the persisted job application by app id field
     * and verifies the http status code returned.
     * @throws Exception
     */
    @Test
    public void getJobApplicationByAppIdTest_returnsJobAppWith200Ok()
            throws Exception {

        JobApplication jobApplication = new JobApplication(1L,"lalit@gmail.com","SampleResume", JobApplicationStatus.APPLIED);
        when(jobAppsvc.getApplcnsByAppId(Mockito.anyLong())).thenReturn(jobApplication);

        RequestBuilder request = MockMvcRequestBuilders.get("/job-application/v1/getJobApplicationByAppId/v1/1").
                accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    /**
     * This test case fetches the persisted job application by job id field
     * and verifies the http status code returned.
     *
     * @throws Exception
     */
    @Test
    public void getJobApplicationByJobIdTest_returnsJobAppWith200Ok()
            throws Exception {

        JobApplication jobApplicationRecOne = new JobApplication(1L,"lalit@gmail.com",
                "SampleResume1", JobApplicationStatus.APPLIED);

        JobApplication jobApplicationRecTwo = new JobApplication(1L,"test@gmail.com",
                "SampleResume2", JobApplicationStatus.APPLIED);

        List<JobApplication> jobAppListTestData = new ArrayList<>();
        jobAppListTestData.add(jobApplicationRecOne);
        jobAppListTestData.add(jobApplicationRecTwo);


        when(jobAppsvc.getApplicationsByJobId(Mockito.anyLong())).thenReturn(jobAppListTestData);

        RequestBuilder request = MockMvcRequestBuilders.get("/job-application/v1/getJobApplicationsByJobId/v1/1").
                accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    /**
     *  This test case focuses on testing and verifying the update app status.
     * @throws Exception
     */
    @Test
    public void testUpdateAppSts_returnsPositiveResp() throws Exception {

        JobApplication jobApplication = new JobApplication(1L,"lalit@gmail.com",
                "SampleResume", JobApplicationStatus.HIRED);
        when(jobAppsvc.updateJobApplnStatus(Mockito.any(),Mockito.anyLong())).thenReturn(jobApplication);


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/job-application/v1/updateJobApplnStatus/v1/9/HIRED")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

}
