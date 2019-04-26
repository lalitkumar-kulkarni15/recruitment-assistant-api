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

    @GetMapping(path = "/getJobApplication/v1/{appId}", consumes = "application/json")
    public ResponseEntity<JobApplication> getJobApplication(@PathVariable Long appId) throws DataNotFoundException {
        final JobApplication jobApplicationRec = this.jobApplicatnSvc.getAllApps(appId);
        return ResponseEntity.ok().body(jobApplicationRec);
    }

    @GetMapping(path = "/getJobApplicationsByJobId/v1/{jobId}", consumes = "application/json")
    public ResponseEntity<JobApplication> getJobApplicationByJobId(@PathVariable Long jobId) throws DataNotFoundException {
        final JobApplication jobApplicationRec = this.jobApplicatnSvc.getAllApps(jobId);
        return ResponseEntity.ok().body(jobApplicationRec);
    }

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
