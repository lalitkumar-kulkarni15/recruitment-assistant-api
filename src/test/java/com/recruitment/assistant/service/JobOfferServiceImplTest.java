package com.recruitment.assistant.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.recruitment.assistant.exception.DataNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.jpa.JpaSystemException;
import com.recruitment.assistant.repository.IJobOfferRepository;
import com.recruitment.assistant.entity.JobOfferEntity;
import com.recruitment.assistant.enums.JobOfferStatus;
import com.recruitment.assistant.exception.RecAsstntTechnicalException;
import com.recruitment.assistant.model.JobOffer;

/**
 * This class houses the unit test cases for {@link JobOfferSvcImpl}
 *
 * @since  26-04-2019
 * @author lalit
 * @version 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class JobOfferServiceImplTest {

    @Mock
    private IJobOfferRepository jobOfferRepository;

    @InjectMocks
    private JobOfferSvcImpl jobOfferSvc;

    /**
     * The intent of this test case is to submit a new job offer in the data store
     * and verify that it was persisted successfully by veryfying the job id created
     * which is returned back by the persisting method.
     *
     * @throws RecAsstntTechnicalException
     */
    @Test
    public void test_createNewJobOffer_persistEntitySuccessfully_returnsJobId_Successfully()
            throws RecAsstntTechnicalException{

        JobOffer jobOfferTestData = getJobOfferTestData();

        JobOfferEntity jobOfferEntity = new JobOfferEntity();
        BeanUtils.copyProperties(jobOfferTestData,jobOfferEntity);
        when(this.jobOfferRepository.save(Mockito.any())).thenReturn(jobOfferEntity);
        JobOffer persistedJobOffer = this.jobOfferSvc.createNewJobOffer(jobOfferTestData);
        assertEquals(1L, persistedJobOffer.getJobId());
    }

    private JobOffer getJobOfferTestData() {
        JobOffer jobOfferTestData = new JobOffer();
        jobOfferTestData.setJobId(1L);
        jobOfferTestData.setJobTitle("title");
        jobOfferTestData.setContactPerson("Contact person");
        jobOfferTestData.setCreatedDate(LocalDate.now());
        jobOfferTestData.setModifiedDate(LocalDate.now());
        jobOfferTestData.setJobDesc("Job Desc");
        jobOfferTestData.setJobOfferStatus(JobOfferStatus.ACTIVE);
        return jobOfferTestData;
    }

    private JobOffer createJobOfferTestDataPositive() {

        JobOffer jobOfr = new JobOffer(2L,"Title","JobDesc","Lalit",
                LocalDate.now(),LocalDate.now(),JobOfferStatus.ACTIVE);

        return jobOfr;
    }

    /**
     * The main intent of this test case is to check if the desired exception is
     * sent back when a run time exception ex :- Null pointer comes up.
     *
     * @throws RecAsstntTechnicalException
     */
    @Test(expected=RecAsstntTechnicalException.class)
    public void test_createNewJobOffer_throwsRecAsstntException_WhenNullPointer()
            throws RecAsstntTechnicalException {
    	
    	final JobOffer jobOfferTestData = createJobOfferTestData();
        JobOfferEntity jobOfferEntity = new JobOfferEntity();
        BeanUtils.copyProperties(jobOfferTestData,jobOfferEntity);
    	when(this.jobOfferRepository.save(Mockito.any())).thenThrow(NullPointerException.class);
    	this.jobOfferSvc.createNewJobOffer(jobOfferTestData);
    	
    }

    /**
     * The main intent of this test case is to check if the desired exception
     * that is {@link RecAsstntTechnicalException} is sent back when a run time
     *
     * @throws RecAsstntTechnicalException
     */
    @Test(expected=RecAsstntTechnicalException.class)
    public void test_createNewJobOffer_throwsRecAsstntException_WhenJPASystemException()
            throws RecAsstntTechnicalException {
    	
    	final JobOffer jobOfferTestData = createJobOfferTestData();
        JobOfferEntity jobOfferEntity = new JobOfferEntity();
        BeanUtils.copyProperties(jobOfferTestData,jobOfferEntity);
    	when(this.jobOfferRepository.save(Mockito.any())).thenThrow(JpaSystemException.class);
    	this.jobOfferSvc.createNewJobOffer(jobOfferTestData);
    	
    }

    private JobOffer createJobOfferTestData() {

        JobOffer jobOffer = new JobOffer(1L,"Sr. Java Developer",
                "SR. Java Dev with 6+ yrs exp in Spring boot with microservices.",
                "Maria Bezrovkov", LocalDate.now(),LocalDate.now(), JobOfferStatus.ACTIVE);

        return jobOffer;
    }

    /**
     * The main intent of this test case is to check if job offers are fetched
     * successfully by the service class implementation by mocking the dependency.
     */
    @Test
    public void test_getAllJobOffers_checksNotNullJobOffersList(){

        final List<JobOfferEntity> jobOfferEntyLstTestData = prepareJobOffersEntityListTestData();
        when(this.jobOfferRepository.findAll()).thenReturn(jobOfferEntyLstTestData);
        List<JobOffer> receivedJobOfferList = this.jobOfferSvc.getAllJobOffers();
        assertNotNull(receivedJobOfferList);
    }

    /**
     * The main intent of this test case is to check if job id field is
     * available and is fetched successfull by the service class implementation
     * by mocking the dependency using mockito framework.
     */
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

    private Optional<JobOffer> getJobOfferEntityForTest() {
        final List<JobOfferEntity> jobOfferEntyLstTestData = prepareSingleJobOfferEntityListTestData();
        when(this.jobOfferRepository.findAll()).thenReturn(jobOfferEntyLstTestData);
        List<JobOffer> receivedJobOfferList = this.jobOfferSvc.getAllJobOffers();
        assertNotNull(receivedJobOfferList);
        return receivedJobOfferList.stream().findFirst();
    }

    /**
     * The main intent of this test case is to check if job title field is
     * available and is fetched successfull by the service class implementation
     * by mocking the dependency using mockito framework.
     */
    @Test
    public void test_getAllJobOffers_checksJobTitleFieldsFromResp(){

        Optional<JobOffer> jobOfferEntity = getJobOfferEntityForTest();

        if(jobOfferEntity.isPresent()){
            final String jobtitle = jobOfferEntity.get().getJobTitle();
            assertEquals("Data scientist",jobtitle);
        } else{
            fail("jobOfferEntity is absent");
        }
    }

    /**
     * The main intent of this test case is to check if contact person field is
     * available and is fetched successfull by the service class implementation
     * by mocking the dependency using mockito framework.
     */
    @Test
    public void test_getAllJobOffers_checksCntckPersnFieldsFromResp(){

        Optional<JobOffer> jobOfferEntity = getJobOfferEntityForTest();

        if(jobOfferEntity.isPresent()){
            final String contkPerson = jobOfferEntity.get().getContactPerson();
            assertEquals("Amar Singh",contkPerson);
        } else{
            fail("jobOfferEntity is absent");
        }
    }

    private List<JobOfferEntity> prepareSingleJobOfferEntityListTestData() {

        JobOfferEntity jobOfferEntityDtaScDev = new JobOfferEntity(1L,"Data scientist",
                "Data scientist Test","Amar Singh",LocalDate.now(),LocalDate.now(), JobOfferStatus.ACTIVE);

        List<JobOfferEntity> jobOfferDataScEntityList = new ArrayList<>();
        jobOfferDataScEntityList.add(jobOfferEntityDtaScDev);

        return jobOfferDataScEntityList;
    }

    private List<JobOfferEntity> prepareJobOffersEntityListTestData() {

        JobOfferEntity jobOfferEntityJavaDev = new JobOfferEntity(1L,"Sr Java Dev Test",
                "Sr Java Dev Test","Juhi Singh",LocalDate.now(),LocalDate.now(), JobOfferStatus.ACTIVE);

        JobOfferEntity jobOfferEntityPythonDev = new JobOfferEntity(1L,"Sr Python Dev Test",
                "Sr Python Dev Test","Amit Singh",LocalDate.now(),LocalDate.now(), JobOfferStatus.ACTIVE);

        List<JobOfferEntity> jobOfferEntityList = new ArrayList<>();
        jobOfferEntityList.add(jobOfferEntityJavaDev);
        jobOfferEntityList.add(jobOfferEntityPythonDev);
        return jobOfferEntityList;
    }

    /**
     * The main intent of this test case is to check if the job offer received
     * is not null while fetching it by job title from the data store.
     */
    @Test
    public void test_findJobOfferByJobTitle_checksNotNullJobOfferReceived()
            throws DataNotFoundException {

        Optional<JobOfferEntity> jobOfferEntityTestData = Optional.of(prepJobOfferEntityTestData());
        when(this.jobOfferRepository.findByJobTitle(Mockito.anyString())).thenReturn(jobOfferEntityTestData);
        JobOffer jobOffer = this.jobOfferSvc.findJobOfferByJobTitle("Sr Java Developer");
        assertNotNull(jobOffer);
    }

    /**
     * The main intent of this test case is to verify the job offer title received
     * while fetching it by job title from the data store.
     */
    @Test
    public void test_findJobOfferByJobTitle_checksJobOfferTitleReceived()
            throws DataNotFoundException {

        Optional<JobOfferEntity> jobOfferEntityTestData = Optional.of(prepJobOfferEntityTestData());
        when(this.jobOfferRepository.findByJobTitle(Mockito.anyString())).thenReturn(jobOfferEntityTestData);
        JobOffer jobOffer = this.jobOfferSvc.findJobOfferByJobTitle("Data scientist");
        assertNotNull(jobOffer);
        assertEquals("Data scientist",jobOffer.getJobTitle());
    }

    private JobOfferEntity prepJobOfferEntityTestData() {

        JobOfferEntity jobOfferEntityDtaScDev = new JobOfferEntity(1L,"Data scientist",
                "Data scientist Test","Amar Singh",LocalDate.now(),LocalDate.now(), JobOfferStatus.ACTIVE);
        return jobOfferEntityDtaScDev;
    }

}
