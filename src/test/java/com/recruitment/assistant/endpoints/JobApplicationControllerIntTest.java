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

        httpHeaders = new HttpHeaders();
        HttpEntity<JobOffer> entity = new HttpEntity<>(createTestDataForNewJobOffer(), httpHeaders);
        restTemplate.exchange(createURLWithPort("/job-offer/v1/newJobOffer/v1",
                host, port), HttpMethod.POST, entity, JobOffer.class);

        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediatypeList = new ArrayList<>();
        mediatypeList.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediatypeList);

        HttpEntity<JobApplication> entityappl = new HttpEntity<JobApplication>(createTestDataForNewJobApplication(), httpHeaders);
        ResponseEntity<JobApplication> response = restTemplate.exchange(createURLWithPort("/job-application/v1/newJobApplication/v1", host, port), HttpMethod.POST, entityappl, JobApplication.class);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

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

    @Test
    public void postNewJobAppln_retBadReqWhenResumeTextNotPassed() {

        httpHeaders = new HttpHeaders();
        HttpEntity<JobOffer> entity = new HttpEntity<>(createTestDataFrResumeTxtNotSent(), httpHeaders);
        restTemplate.exchange(createURLWithPort("/job-offer/v1/newJobOffer/v1",
                host, port), HttpMethod.POST, entity, JobOffer.class);

        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediatypeList = new ArrayList<>();
        mediatypeList.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediatypeList);

        HttpEntity<JobApplication> entityappl = new HttpEntity<JobApplication>(createTestDataResumeTxtNotPassed(), httpHeaders);
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


    @Test
    public void postNewJobAppln_retBadReqWhenEmailNotPassed() {

        httpHeaders = new HttpHeaders();
        HttpEntity<JobOffer> entity = new HttpEntity<>(createTestDataFrResumeTxtNotSent(), httpHeaders);
        restTemplate.exchange(createURLWithPort("/job-offer/v1/newJobOffer/v1",
                host, port), HttpMethod.POST, entity, JobOffer.class);

        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediatypeList = new ArrayList<>();
        mediatypeList.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediatypeList);

        HttpEntity<JobApplication> entityappl = new HttpEntity<JobApplication>(createTestDataResumeTxtNotPassed(), httpHeaders);
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

    @Test
    public void getJobApplication_retOKStsWithDataSucc(){

        httpHeaders = new HttpHeaders();
        HttpEntity<JobOffer> entity = new HttpEntity<JobOffer>(createTestDataForAddingJobOffer()
                ,httpHeaders);
        restTemplate.exchange(createURLWithPort("/job-offer/v1/newJobOffer/v1",host,port),
                HttpMethod.POST,entity,JobOffer.class);


        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediatypeList = new ArrayList<>();
        mediatypeList.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediatypeList);

        HttpEntity<JobApplication> entityappl = new HttpEntity<JobApplication>(createTestDataForNewJobApplication(), httpHeaders);
        ResponseEntity<JobApplication> response = restTemplate.exchange(createURLWithPort("/job-application/v1/newJobApplication/v1", host, port), HttpMethod.POST, entityappl, JobApplication.class);


        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediaTypeList);



        HttpEntity<String> entityFetch = new HttpEntity<String>(null,httpHeaders);
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



    @Test
    public void getJobApplicationByJobId_retOKStsWithDataSucc(){

        httpHeaders = new HttpHeaders();
        HttpEntity<JobOffer> entity = new HttpEntity<JobOffer>(creTstDataFrGettingJobOffrSrhAppByJobId()
                ,httpHeaders);
        restTemplate.exchange(createURLWithPort("/job-offer/v1/newJobOffer/v1",host,port),
                HttpMethod.POST,entity,JobOffer.class);


        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediatypeList = new ArrayList<>();
        mediatypeList.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediatypeList);

        HttpEntity<JobApplication> entityappl = new HttpEntity<JobApplication>(createTestDataForNewJobApplication(), httpHeaders);
        ResponseEntity<JobApplication> response = restTemplate.exchange(createURLWithPort("/job-application/v1/newJobApplication/v1", host, port), HttpMethod.POST, entityappl, JobApplication.class);


        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediaTypeList);



        HttpEntity<String> entityFetch = new HttpEntity<String>(null,httpHeaders);
        ResponseEntity<String> responseApp = restTemplate.exchange
                (createURLWithPort ("/job-application/v1/getJobApplicationsByJobId/v1/1",host,port),
                        HttpMethod.GET,entityFetch,String.class);
        Assert.assertEquals(HttpStatus.OK, responseApp.getStatusCode());

    }

    private JobOffer creTstDataFrGettingJobOffrSrhAppByJobId() {

        return new JobOffer(1L, "Sr. Java Engineer",
                "Job Desc", "Contact Person", LocalDate.now(),
                LocalDate.now(), JobOfferStatus.ACTIVE, 2);
    }


    @Test
    public void patchJobApplicationStatus_retOKSts(){

        httpHeaders = new HttpHeaders();
        HttpEntity<JobOffer> entity = new HttpEntity<JobOffer>(creTstDataFrGettingJobOffrSrhAppByJobId()
                ,httpHeaders);
        restTemplate.exchange(createURLWithPort("/job-offer/v1/newJobOffer/v1",host,port),
                HttpMethod.POST,entity,JobOffer.class);


        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediatypeList = new ArrayList<>();
        mediatypeList.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediatypeList);

        HttpEntity<JobApplication> entityappl = new HttpEntity<JobApplication>(createTestDataForNewJobApplication(), httpHeaders);
        ResponseEntity<JobApplication> response = restTemplate.exchange(createURLWithPort("/job-application/v1/newJobApplication/v1", host, port), HttpMethod.POST, entityappl, JobApplication.class);


        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediaTypeList);


        HttpEntity<String> entityPatch = new HttpEntity<String>(null,httpHeaders);

        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        ResponseEntity<JobApplication> responseUpdtd =restTemplate.exchange(createURLWithPort("job-application/v1/updateJobApplnStatus/v1/1/REJECTED",host,port),
                HttpMethod.PATCH,entityPatch,JobApplication.class);

        Assert.assertEquals(responseUpdtd.getBody().getApplicationStatus(),JobApplicationStatus.REJECTED);

    }

    private JobApplication creTstDataFrUpdatingJobAppStatus() {

        return new JobApplication(1L, null,
                JobApplicationStatus.HIRED, "Sample Resume");
    }

    /*private JobOffer creTstDataFrGettingJobOffrSrhAppByJobId() {

        return new JobOffer(1L, "Sr. Java Engineer",
                "Job Desc", "Contact Person", LocalDate.now(),
                LocalDate.now(), JobOfferStatus.ACTIVE, 2);
    }*/







}