package com.recruitment.assistant.endpoints;

import com.recruitment.assistant.RecruitmentAssistantApiApplication;
import com.recruitment.assistant.enums.JobApplicationStatus;
import com.recruitment.assistant.enums.JobOfferStatus;
import com.recruitment.assistant.model.JobApplication;
import com.recruitment.assistant.model.JobOffer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static com.recruitment.assistant.utils.ITestUtils.createURLWithPort;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class houses the integration test cases specifically pertaining to
 * the controller class {@link JobApplicatnController}
 *
 * @since 26-04-2019
 * @version 1.0
 * @author lalit
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = {RecruitmentAssistantApiApplication.class})
@TestPropertySource(locations = "classpath:application-test.properties")
public class JobApplicationControllerIntTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${server.port}")
    private String port;

    @Value("${application.test.host}")
    private String host;

    private HttpHeaders httpHeaders;

    @Test
    public void postNewJobAppln_retStsOKWithDataPersisted() {

        // Step 1 : Post a job offer .
        postJobTestData(createTestDataForNewJobOffer());

        // step 2: Post a new job application
        HttpEntity<JobApplication> entityappl = new HttpEntity<JobApplication>(createTestDataForNewJobApplication(), getHttpHeader());
        ResponseEntity<JobApplication> response = restTemplate.exchange(createURLWithPort("/job-application/v1/newJobApplication/v1", host, port), HttpMethod.POST, entityappl, JobApplication.class);

        // Step 3: Verify the status code
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    private void getHeaderToPostJob(JobOffer testDataForNewJobOffer) {
        httpHeaders = new HttpHeaders();
        HttpEntity<JobOffer> entity = new HttpEntity<>(testDataForNewJobOffer, httpHeaders);
        restTemplate.exchange(createURLWithPort("/job-offer/v1/newJobOffer/v1",
                host, port), HttpMethod.POST, entity, JobOffer.class);
    }

    private JobApplication createTestDataForNewJobApplication() {

        return new JobApplication(1L, "lalit.kulkarni@gmail.com",
                JobApplicationStatus.APPLIED, "Sample resume text");
    }


    private JobOffer createTestDataForNewJobOffer() {

        return new JobOffer(1L, "Sr. Java Engineer",
                "Job Desc", "Contact Person", LocalDate.now(),
                LocalDate.now(), JobOfferStatus.ACTIVE, 2);
    }

    /**
     * The intent of this test case is to verify if bad request status code is thrown
     * if the resume text field is not passed which is mandatory.
     */
    @Test
    public void postNewJobAppln_retBadReqWhenResumeTextNotPassed() {

        postJobTestData(createTestDataFrResumeTxtNotSent());

        HttpEntity<JobApplication> entityappl = new HttpEntity<JobApplication>(createTestDataResumeTxtNotPassed(), getHttpHeader());
        ResponseEntity<JobApplication> response = restTemplate.exchange(createURLWithPort("/job-application/v1/newJobApplication/v1",
                host, port), HttpMethod.POST, entityappl, JobApplication.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    private JobOffer createTestDataFrResumeTxtNotSent(){

        return new JobOffer(1L, "Sr. Java Engineer",
                "Job Desc", "Contact Person", LocalDate.now(),
                LocalDate.now(), JobOfferStatus.ACTIVE, 2);

    }

    private JobApplication createTestDataResumeTxtNotPassed() {

        return new JobApplication(1L, "lalit.kulkarni@gmail.com",
                JobApplicationStatus.APPLIED, null);
    }


    /**
     * The intent of this test case is to verify if bad request status code is thrown
     * if the email field is not passed which is mandatory.
     */
    @Test
    public void postNewJobAppln_retBadReqWhenEmailNotPassed() {

        postJobTestData(createTestDataFrResumeTxtNotSent());
        HttpEntity<JobApplication> entityappl = new HttpEntity<JobApplication>(createTestDataResumeTxtNotPassed(), getHttpHeader());
        ResponseEntity<JobApplication> response = restTemplate.exchange(createURLWithPort("/job-application/v1/newJobApplication/v1",
                host, port), HttpMethod.POST, entityappl, JobApplication.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    private JobOffer createTestDataFrEmailNotSent(){

        return new JobOffer(1L, "Sr. Java Engineer",
                "Job Desc", "Contact Person", LocalDate.now(),
                LocalDate.now(), JobOfferStatus.ACTIVE, 2);

    }

    private JobApplication createTestDataEmailNotPassed() {

        return new JobApplication(1L, null,
                JobApplicationStatus.APPLIED, "Sample Resume");
    }

    /**
     * The intent of this test case is to verify if the job applications are
     * able to fetch using the app id.
     */
    @Test
    public void getJobApplication_retOKStsWithDataSucc(){

        // Step 1: Post a new job offer.
        postJobTestData(createTestDataForAddingJobOffer());

        // Step 2: Post new job application.
        HttpEntity<JobApplication> entityappl = new HttpEntity<JobApplication>(createTestDataForNewJobApplication(), getHttpHeader());
        ResponseEntity<JobApplication> response = restTemplate.exchange(createURLWithPort("/job-application/v1/newJobApplication/v1", host, port), HttpMethod.POST, entityappl, JobApplication.class);

        // Step 3 : Fetch the job application from the data store by app id.
        HttpEntity<String> entityFetch = new HttpEntity<String>(null,getHttpHeader());
        ResponseEntity<String> responseApp = restTemplate.exchange
                (createURLWithPort ("/job-application/v1/getJobApplicationByAppId/v1/1",host,port),
                        HttpMethod.GET,entityFetch,String.class);
        Assert.assertEquals(HttpStatus.OK, responseApp.getStatusCode());

    }

    private JobOffer createTestDataForAddingJobOffer() {

        return new JobOffer(1L, "Sr. Java Engineer",
                "Job Desc", "Contact Person", LocalDate.now(),
                LocalDate.now(), JobOfferStatus.ACTIVE, 2);
    }


    /**
     * The intent of this test case is to verify if the job applications are
     * able to fetch using the job id.
     */
    @Test
    public void getJobApplicationByJobId_retOKStsWithDataSucc(){

        // Post a job offer.
        postJobTestData(creTstDataFrGettingJobOffrSrhAppByJobId());
        httpHeaders = getHttpHeader();

        HttpEntity<JobApplication> entityappl = new HttpEntity<JobApplication>(createTestDataForNewJobApplication(), httpHeaders);
        ResponseEntity<JobApplication> response = restTemplate.exchange(createURLWithPort("/job-application/v1/newJobApplication/v1", host, port), HttpMethod.POST, entityappl, JobApplication.class);

        HttpEntity<String> entityFetch = new HttpEntity<String>(null,httpHeaders);

        ResponseEntity<String> responseApp = restTemplate.exchange
                (createURLWithPort ("/job-application/v1/getJobApplicationsByJobId/v1/1",host,port),
                        HttpMethod.GET,entityFetch,String.class);
        Assert.assertEquals(HttpStatus.OK, responseApp.getStatusCode());

    }

    private HttpHeaders getHttpHeader(){

        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediatypeList = new ArrayList<>();
        mediatypeList.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediatypeList);
        return httpHeaders;
    }
// creTstDataFrGettingJobOffrSrhAppByJobId()
    private void postJobTestData(JobOffer jobOffer){

        httpHeaders = new HttpHeaders();
        HttpEntity<JobOffer> entity = new HttpEntity<JobOffer>(jobOffer
                ,httpHeaders);
        restTemplate.exchange(createURLWithPort("/job-offer/v1/newJobOffer/v1",host,port),
                HttpMethod.POST,entity,JobOffer.class);
    }

    private JobOffer creTstDataFrGettingJobOffrSrhAppByJobId() {
        return new JobOffer(1L, "Sr. Java Engineer",
                "Job Desc", "Contact Person", LocalDate.now(),
                LocalDate.now(), JobOfferStatus.ACTIVE, 2);
    }

    /**
     * The intent of this test case is to verify if the job application status
     * is able to change successfully. Please refer to the below inline steps comments
     * for understanding the various steps involved to achieve this test.
     */
    @Test
    public void patchJobApplicationStatus_retOKSts(){

        // Step 1: Post a new job offer in the data store.
        httpHeaders = new HttpHeaders();
        HttpEntity<JobOffer> entity = new HttpEntity<JobOffer>(creTstDataFrGettingJobOffrSrhAppByJobId()
                ,httpHeaders);
        restTemplate.exchange(createURLWithPort("/job-offer/v1/newJobOffer/v1",host,port),
                HttpMethod.POST,entity,JobOffer.class);

        // Step 2: Submit a new job application for the job offer posted in step 1.
        HttpEntity<JobApplication> entityappl = new HttpEntity<JobApplication>(createTestDataForNewJobApplication(), getHttpHeader());
        ResponseEntity<JobApplication> response = restTemplate.exchange(createURLWithPort("/job-application/v1/newJobApplication/v1", host, port), HttpMethod.POST, entityappl, JobApplication.class);

        // Step 3: Submit a patch request to update the application status.
        HttpEntity<String> entityPatch = new HttpEntity<String>(null,getHttpHeader());
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        ResponseEntity<JobApplication> responseUpdtd =restTemplate.exchange(createURLWithPort("job-application/v1/updateJobApplnStatus/v1/1/REJECTED",host,port),
                HttpMethod.PATCH,entityPatch,JobApplication.class);

        // Step 4 : Check if the application status has been updated successfully.
        Assert.assertEquals(responseUpdtd.getBody().getApplicationStatus(),JobApplicationStatus.REJECTED);

    }

    private JobApplication creTstDataFrUpdatingJobAppStatus() {
        return new JobApplication(1L, null,
                JobApplicationStatus.HIRED, "Sample Resume");
    }

}