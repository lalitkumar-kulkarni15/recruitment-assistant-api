package com.recruitment.assistant.service;

import com.recruitment.assistant.enums.JobApplicationStatus;
import com.recruitment.assistant.exception.DataNotFoundException;
import com.recruitment.assistant.exception.RecAsstntTechnicalException;
import com.recruitment.assistant.model.JobApplication;
import com.recruitment.assistant.repository.IJobApplicationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.List;
import static org.mockito.Mockito.when;

/**
 * This class houses the unit test cases for {@link JobApplicatnSvcImpl}
 *
 * @since  26-04-2019
 * @author lalit
 * @version 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class JobApplicatnSvcImplTest {

    @Mock
    private IJobApplicationRepository jobApplicationRepository;

    @InjectMocks
    private JobApplicatnSvcImpl jobApplicatnSvc;

    /**
     * The intent of this test case is mainly to check if teh appropriate
     * exception {@link RecAsstntTechnicalException} is thrown to the upper layer
     * when there is any runtime exception while finding the objects from the data store.
     *
     * @throws RecAsstntTechnicalException
     */
    @Test(expected = RecAsstntTechnicalException.class)
    public void getApplicationsByJobIdTest_throwsRecAsstTechEx()
            throws RecAsstntTechnicalException, DataNotFoundException {

        when(this.jobApplicationRepository.findJobApplicationByOferId
                (Mockito.anyLong()))
                .thenThrow(RuntimeException.class);
        List<JobApplication> jobApplicationEntity = this.jobApplicatnSvc.
                getApplicationsByJobId(1L);
    }

    /**
     * The intent of this test case is mainly to check if teh appropriate
     * exception {@link RecAsstntTechnicalException} is thrown to the upper layer
     * when data returned by the lower layers like repository returns unacceptable
     * response like null.
     *
     * @throws RecAsstntTechnicalException
     */
    @Test(expected = RecAsstntTechnicalException.class)
    public void updateJobApplnStatusTest_throwsDataNtFndEx() throws DataNotFoundException, RecAsstntTechnicalException {

        when(this.jobApplicationRepository.findById
                (Mockito.anyLong()))
                .thenReturn(null);

        JobApplication jobApplication = this.jobApplicatnSvc.updateJobApplnStatus(
                JobApplicationStatus.APPLIED,1L);
    }

}
