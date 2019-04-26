package com.recruitment.assistant.endpoints;

import com.recruitment.assistant.enums.JobApplicationStatus;
import com.recruitment.assistant.exception.DataNotFoundException;
import com.recruitment.assistant.exception.RecAsstntTechnicalException;
import com.recruitment.assistant.model.JobApplication;
import com.recruitment.assistant.service.IJobApplicatnSvc;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/job-application/v1")
@Api(tags = "Job application API", value = "This API houses all the lifecycle of the job  " +
        "application.")
public class JobApplicatnController {

    @Autowired
    private IJobApplicatnSvc jobApplicatnSvc;

    /**
     * This method accepts the job application details and persists in the
     * data store.
     *
     * @param jobApplication : {@link JobApplication}
     * @return               : {@link ResponseEntity}
     * @throws RecAsstntTechnicalException
     * @throws DataNotFoundException
     */
    @PostMapping(path = "/newJobApplication/v1", consumes = "application/json")
    public ResponseEntity submitJobApplication(@Valid @RequestBody JobApplication jobApplication)
            throws RecAsstntTechnicalException, DataNotFoundException {

        final long jobAppId = this.jobApplicatnSvc.submitJobApplicatn(jobApplication);

       final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                        .path("/{id}")
                        .buildAndExpand(jobAppId)
                        .toUri();

        return ResponseEntity.created(uri).build();
    }

    /**
     *  This api finds the job application from the data store on the basis
     *  of the app id.
     *
     * @param  appId : The application id of the required application.
     * @return {@link ResponseEntity<JobApplication>}
     * @throws DataNotFoundException : This exception is thrown when no job
     *                                 application is found for the given app id.
     */
    @GetMapping(path = "/getJobApplicationByAppId/v1/{appId}", consumes = "application/json")
    public ResponseEntity<JobApplication> getJobApplicationByAppId(@PathVariable Long appId) throws DataNotFoundException {
        final JobApplication jobApplicationRec = this.jobApplicatnSvc.getAllApps(appId);
        return ResponseEntity.ok().body(jobApplicationRec);
    }

    /**
     * This api finds the job application from the data store on the basis
     * of the job id.
     *
     * @param jobId : The job id for which the application was submitted.
     * @return {@link ResponseEntity<JobApplication>}
     * @throws DataNotFoundException : This exception is thrown when no job
     *      *                          application is found for the given job id.
     */
    @GetMapping(path = "/getJobApplicationsByJobId/v1/{jobId}", consumes = "application/json")
    public ResponseEntity<JobApplication> getJobApplicationByJobId(@PathVariable Long jobId) throws DataNotFoundException {
        final JobApplication jobApplicationRec = this.jobApplicatnSvc.getAllApps(jobId);
        return ResponseEntity.ok().body(jobApplicationRec);
    }

    /**
     * This api updates the status of the job application and then triggers
     * a notification to inform the same.
     * @param appId     : It accepts the application id of the application whoose
     *                    status needs to be updated.
     * @param appStatus : It accepts the application status that needs to be updated.
     * @return          : {@link ResponseEntity<JobApplication>}
     * @throws DataNotFoundException : This exception is thrown when no job application is found
     *                   for the given app id.
     */
    @PatchMapping(path = "/updateJobApplnStatus/v1/{appId}/{appStatus}",consumes = "application/json")
    public ResponseEntity<JobApplication> patchJobApplicationStatus
            (@PathVariable long appId, @PathVariable JobApplicationStatus appStatus)
            throws DataNotFoundException {

        JobApplication jobApplication = this.jobApplicatnSvc.updateJobApplnStatus(appStatus,appId);
        return ResponseEntity.ok().body(jobApplication);
    }

    @GetMapping(path = "/getJobApplicatByJobId/v1/{jobId}", consumes = "application/json")
    public ResponseEntity<List<JobApplication>> getJobApplicatByJobId(@PathVariable Long jobId) throws
            DataNotFoundException {
        final List<JobApplication> jobApplicationRec = this.jobApplicatnSvc.getApplicationsByJobId(jobId);
        return ResponseEntity.ok().body(jobApplicationRec);
    }
}
