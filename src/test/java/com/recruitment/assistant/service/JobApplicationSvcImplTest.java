package com.recruitment.assistant.service;

import com.recruitment.assistant.entity.JobOfferEntity;
import com.recruitment.assistant.enums.JobApplicationStatus;
import com.recruitment.assistant.exception.DataNotFoundException;
import com.recruitment.assistant.exception.RecAsstntTechnicalException;
import com.recruitment.assistant.model.JobApplication;
import com.recruitment.assistant.repository.IJobApplicationRepository;
import com.recruitment.assistant.repository.IJobOfferRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.time.LocalDate;
import java.util.Optional;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JobApplicationSvcImplTest {

    @Mock
    private IJobOfferRepository jobOfferRepo;

    @Mock
    private IJobApplicationRepository jobAppRepo;

    @InjectMocks
    private JobApplicatnSvcImpl jobApplicatnSvc;

    @Test(expected = RecAsstntTechnicalException.class)
    public void getAllAppsTest() throws DataNotFoundException {

        when(this.jobAppRepo.findJobApplicationByOfferId(Mockito.anyLong())).thenThrow(RecAsstntTechnicalException.class);
        this.jobApplicatnSvc.getAllApps(1L);


    }

    private JobOfferEntity createTestDataForNewJobApplication() {

        JobOfferEntity jobOfferEntity = new JobOfferEntity();
        jobOfferEntity.setJobId(1L);
        jobOfferEntity.setContactPerson("Lalit");
        jobOfferEntity.setCreatedDate(LocalDate.now());
        jobOfferEntity.setJobDesc("Sample Job desc");
        return jobOfferEntity;
    }

    private JobApplication createJobAppTestDataForNewJobApplication() {

        JobApplication jobApplication = new JobApplication();
        jobApplication.setResumeTxt("Sample resume text");
        jobApplication.setApplicationStatus(JobApplicationStatus.APPLIED);
        return jobApplication;
    }






}
