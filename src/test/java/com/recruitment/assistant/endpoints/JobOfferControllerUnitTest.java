package com.recruitment.assistant.endpoints;

import com.recruitment.assistant.enums.JobApplicationStatus;
import com.recruitment.assistant.enums.JobOfferStatus;
import com.recruitment.assistant.model.JobApplication;
import com.recruitment.assistant.model.JobOffer;
import com.recruitment.assistant.service.IJobOfferSvc;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(JobOfferController.class)
public class JobOfferControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IJobOfferSvc jobOffersvc;

    /**
     * This method submits the job application with positive test data
     * and verifies the HTTP status code CREATED. 201.
     * @throws Exception
     */
    @Test
    public void testSubmitJobApp_returnsPositiveResp() throws Exception {

        JobOffer jobOffer = new JobOffer(1L,"JavaDev","Kirti","Sample Dev desc",
                LocalDate.now(),LocalDate.now(), JobOfferStatus.ACTIVE);

        when(jobOffersvc.createNewJobOffer(Mockito.any())).thenReturn(jobOffer);
        JobApplication jobApplication = new JobApplication(1L,"lalit@gmail.com",
                "SampleResume", JobApplicationStatus.APPLIED);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/job-offer/v1/newJobOffer/v1")
                .accept(MediaType.APPLICATION_JSON).content("{\n" +
                        "    \"jobTitle\": \"zzzz\",\n" +
                        "    \"jobDesc\": \"Sample C++ scientist\",\n" +
                        "    \"contactPerson\": \"Basho\",\n" +
                        "    \"createdDate\": \"2019-03-03\",\n" +
                        "    \"modifiedDate\":\"2019-03-03\",\n" +
                        "    \"jobOfferStatus\":\"ACTIVE\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }


    /**
     * This method fetches all the job applications from the data store
     * with positive test data and verifies the HTTP status code OK. 200.
     * @throws Exception
     */
    @Test
    public void getAllJobOffersTest_returnsJobOffrsWith200Ok()
            throws Exception {

        JobOffer jobOfferOne = new JobOffer(1L,"JavaDev","Kirti","Sample Dev desc",
                LocalDate.now(),LocalDate.now(), JobOfferStatus.ACTIVE);

        JobOffer jobOfferTwo = new JobOffer(1L,"PythonDev","Kirti","Sample Dev desc",
                LocalDate.now(),LocalDate.now(), JobOfferStatus.ACTIVE);

        List<JobOffer> jobOffersListTestDataRet = new ArrayList<>();
        jobOffersListTestDataRet.add(jobOfferOne);
        jobOffersListTestDataRet.add(jobOfferTwo);

        when(jobOffersvc.getAllJobOffers()).thenReturn(jobOffersListTestDataRet);

        RequestBuilder request = MockMvcRequestBuilders.get("/job-offer/v1/getAllJobOffers/v1").
                accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    /**
     * Thsi test case finds the job offers by passing positive job Id
     * and verifies the http code 200 OK.
     * @throws Exception
     */
    @Test
    public void findJobOffersByJobIdTest_returnsJobOffrWith200Ok()
            throws Exception {

        JobOffer jobOfferOne = new JobOffer(1L,"JavaDev","Kirti","Sample Dev desc",
                LocalDate.now(),LocalDate.now(), JobOfferStatus.ACTIVE);

        when(jobOffersvc.findJobOfferByJobId(Mockito.anyLong())).thenReturn(jobOfferOne);

        RequestBuilder request = MockMvcRequestBuilders.get("/job-offer/v1/getJobOfferByJobId/v1/1").
                accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }
}
