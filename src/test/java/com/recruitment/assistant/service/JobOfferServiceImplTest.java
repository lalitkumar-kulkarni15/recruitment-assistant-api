package com.recruitment.assistant.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.junit.Assert.*;
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
    public void test_pos_createNewJobOffer_persistEntitySuccessfully_returnsJobId_Successfully()
            throws RecAsstntTechnicalException{

       final JobOffer jobOfferTestData = createJobOfferTestData();
       JobOfferEntity jobOfferEntity = new JobOfferEntity();
       BeanUtils.copyProperties(jobOfferTestData,jobOfferEntity);
       when(this.jobOfferRepository.save(Mockito.any())).thenReturn(jobOfferEntity);
       long persistedJobOfferJobId = this.jobOfferSvc.createNewJobOffer(jobOfferTestData);
       assertEquals(1L, persistedJobOfferJobId);
    }
    
    @Test(expected=RecAsstntTechnicalException.class)
    public void test_neg_createNewJobOffer_throwsRecAsstntException_WhenNullPointer()
            throws RecAsstntTechnicalException {
    	
    	final JobOffer jobOfferTestData = createJobOfferTestData();
        JobOfferEntity jobOfferEntity = new JobOfferEntity();
        BeanUtils.copyProperties(jobOfferTestData,jobOfferEntity);
    	when(this.jobOfferRepository.save(Mockito.any())).thenThrow(NullPointerException.class);
    	this.jobOfferSvc.createNewJobOffer(jobOfferTestData);
    	
    }
    
    @Test(expected=RecAsstntTechnicalException.class)
    public void test_neg_createNewJobOffer_throwsRecAsstntException_WhenJPASystemException()
            throws RecAsstntTechnicalException {
    	
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

    @Test
    public void test_pos_getAllJobOffers_checksNotNullJobOffersList(){

        final List<JobOfferEntity> jobOfferEntyLstTestData = prepareJobOffersEntityListTestData();
        when(this.jobOfferRepository.findAll()).thenReturn(jobOfferEntyLstTestData);
        List<JobOffer> receivedJobOfferList = this.jobOfferSvc.getAllJobOffers();
        assertNotNull(receivedJobOfferList);
    }

    @Test
    public void test_pos_getAllJobOffers_checksJobIdFieldsFromResp(){

        Optional<JobOffer> jobOfferEntity = getJobOfferEntityForTest();

        if(jobOfferEntity.isPresent()){
            long jobId = jobOfferEntity.get().getJobId();
            assertEquals(1L,jobId);
        } else{
            fail("jobOfferEntity is absent");
        }
    }

    @Test
    public void test_pos_getAllJobOffers_checksJobTitleFieldsFromResp(){

        Optional<JobOffer> jobOfferEntity = getJobOfferEntityForTest();

        if(jobOfferEntity.isPresent()){
            final String jobtitle = jobOfferEntity.get().getJobTitle();
            assertEquals("Data scientist",jobtitle);
        } else{
            fail("jobOfferEntity is absent");
        }
    }

    @Test
    public void test_pos_getAllJobOffers_checksCntckPersnFieldsFromResp(){

        Optional<JobOffer> jobOfferEntity = getJobOfferEntityForTest();

        if(jobOfferEntity.isPresent()){
            final String contkPerson = jobOfferEntity.get().getContactPerson();
            assertEquals("Amar Singh",contkPerson);
        } else{
            fail("jobOfferEntity is absent");
        }
    }

    private Optional<JobOffer> getJobOfferEntityForTest() {
        final List<JobOfferEntity> jobOfferEntyLstTestData = prepareSingleJobOfferEntityListTestData();
        when(this.jobOfferRepository.findAll()).thenReturn(jobOfferEntyLstTestData);
        List<JobOffer> receivedJobOfferList = this.jobOfferSvc.getAllJobOffers();
        assertNotNull(receivedJobOfferList);
        return receivedJobOfferList.stream().findFirst();
    }

    private List<JobOfferEntity> prepareSingleJobOfferEntityListTestData() {

        JobOfferEntity jobOfferEntityDtaScDev = new JobOfferEntity(1L,"Data scientist",
                "Data scientist Test","Amar Singh",LocalDate.now(),LocalDate.now(),JobApplnStatus.ACTIVE);

        List<JobOfferEntity> jobOfferDataScEntityList = new ArrayList<>();
        jobOfferDataScEntityList.add(jobOfferEntityDtaScDev);

        return jobOfferDataScEntityList;
    }

    private List<JobOfferEntity> prepareJobOffersEntityListTestData() {

        JobOfferEntity jobOfferEntityJavaDev = new JobOfferEntity(1L,"Sr Java Dev Test",
                "Sr Java Dev Test","Juhi Singh",LocalDate.now(),LocalDate.now(),JobApplnStatus.ACTIVE);

        JobOfferEntity jobOfferEntityPythonDev = new JobOfferEntity(1L,"Sr Python Dev Test",
                "Sr Python Dev Test","Amit Singh",LocalDate.now(),LocalDate.now(),JobApplnStatus.ACTIVE);

        List<JobOfferEntity> jobOfferEntityList = new ArrayList<>();
        jobOfferEntityList.add(jobOfferEntityJavaDev);
        jobOfferEntityList.add(jobOfferEntityPythonDev);
        return jobOfferEntityList;
    }
}
