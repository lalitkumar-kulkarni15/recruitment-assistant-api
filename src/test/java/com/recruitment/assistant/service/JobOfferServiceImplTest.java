package com.recruitment.assistant.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.jpa.JpaSystemException;
import com.recruitment.assistant.data.IJobOfferRepository;
import com.recruitment.assistant.entity.JobOfferEntity;
import com.recruitment.assistant.enums.JobApplnStatus;
import com.recruitment.assistant.exception.RecAsstntTechnicalException;
import com.recruitment.assistant.model.JobOffer;

@RunWith(MockitoJUnitRunner.class)
public class JobOfferServiceImplTest {

    @Mock
    private IJobOfferRepository jobOfferRepository;

    @InjectMocks
    private JobOfferSvcImpl jobOfferSvc;

    @Test
    public void test_createNewJobOffer_persistEntitySuccessfully_returnsJobId_Successfully() throws RecAsstntTechnicalException{

       final JobOffer jobOfferTestData = createJobOfferTestData();
       JobOfferEntity jobOfferEntity = new JobOfferEntity();
       BeanUtils.copyProperties(jobOfferTestData,jobOfferEntity);
       when(this.jobOfferRepository.save(Mockito.any())).thenReturn(jobOfferEntity);
       long persistedJobOfferJobId = this.jobOfferSvc.createNewJobOffer(jobOfferTestData);
       assertEquals(1L, persistedJobOfferJobId);
    }
    
    @Test(expected=RecAsstntTechnicalException.class)
    public void test_createNewJobOffer_throwsRecAsstntException_WhenNullPointer() throws RecAsstntTechnicalException {
    	
    	final JobOffer jobOfferTestData = createJobOfferTestData();
        JobOfferEntity jobOfferEntity = new JobOfferEntity();
        BeanUtils.copyProperties(jobOfferTestData,jobOfferEntity);
    	when(this.jobOfferRepository.save(Mockito.any())).thenThrow(NullPointerException.class);
    	this.jobOfferSvc.createNewJobOffer(jobOfferTestData);
    	
    }
    
    @Test(expected=RecAsstntTechnicalException.class)
    public void test_createNewJobOffer_throwsRecAsstntException_WhenJPASystemException() throws RecAsstntTechnicalException {
    	
    	final JobOffer jobOfferTestData = createJobOfferTestData();
        JobOfferEntity jobOfferEntity = new JobOfferEntity();
        BeanUtils.copyProperties(jobOfferTestData,jobOfferEntity);
    	when(this.jobOfferRepository.save(Mockito.any())).thenThrow(JpaSystemException.class);
    	this.jobOfferSvc.createNewJobOffer(jobOfferTestData);
    	
    }

    private JobOffer createJobOfferTestData() {

       return new JobOffer(1L,"Senior Java Developer",
                "SR. Java Dev with 6+ yrs exp in Spring boot with microservices.",
                "Maria Bezrovkov", LocalDate.now(),LocalDate.now(), JobApplnStatus.ACTIVE);

    }
}
