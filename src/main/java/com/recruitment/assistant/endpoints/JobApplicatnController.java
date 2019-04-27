package com.recruitment.assistant.endpoints;

import com.recruitment.assistant.enums.JobApplicationStatus;
import com.recruitment.assistant.exception.DataNotFoundException;
import com.recruitment.assistant.exception.RecAsstntTechnicalException;
import com.recruitment.assistant.model.JobApplication;
import com.recruitment.assistant.service.IJobApplicatnSvc;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * This class is the rest endpoint controller class for handling all the lifecycle
 * of the job application like - creation fetching the applications on the basis of various
 * inputs.
 * <p>
 * Please refer the swagger link {@link "http://localhost:9098/swagger-ui.html"} for
 * detailed information regarding the API , input and output parameters and Http status codes.
 *
 * @author lalit
 * @version 1.0
 * @since 26-04-2019
 */
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
     * @return : {@link ResponseEntity}
     * @throws RecAsstntTechnicalException
     * @throws DataNotFoundException
     */
    @ApiOperation(value = "Create a new Job Application.", notes = "This API creates a new Job" +
                         " Application and stores in the data store.")
    @ApiResponses(value = {@ApiResponse(code = 500, message = "Something went wrong in the service, please "
                          + "contact the system administrator - Email - lalitkulkarniofficial@gmail.com"),
            @ApiResponse(code = 201, message = "Job Application has been successfully created in the system.")})
            @ApiResponse(code = 400, message = "Bad input request.Please check the error description for more details.")
    @PostMapping(path = "/newJobApplication/v1", consumes = "application/json")
    public ResponseEntity submitJobApplication(@Valid @RequestBody JobApplication jobApplication)
            throws RecAsstntTechnicalException, DataNotFoundException {

        /**Invoke the service layer to persist the job application to the data store.
         jobApplication will be already validated using the validation annotations
         inside the {@link JobApplication}
         */
        final long jobAppId = this.jobApplicatnSvc.submitJobApplicatn(jobApplication);

        // Creating and returning the uri of the job application resource created in the data store.
        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}")
                .buildAndExpand(jobAppId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    /**
     * This api finds the job application from the data store on the basis
     * of the app id.
     * It returns back Http.OK - 200 if it finds the job application successfully
     * from the data store.
     *
     * @param appId : The application id of the required application.
     * @return {@link ResponseEntity<JobApplication>}
     * @throws DataNotFoundException : This exception is thrown when no job
     *                               application is found for the given app id.
     */
    @ApiOperation(value = "Get a Job Application by app id", notes = "This API fetches Job Application by appId")
    @ApiResponses(value = {@ApiResponse(code = 500, message = "Something went wrong in the service, please "
            + "contact the system administrator - Email - lalitkulkarniofficial@gmail.com"),
            @ApiResponse(code = 201, message = "Job Application has been successfully fetched the system.")})
            @ApiResponse(code = 400, message = "Bad input request.Please check the error description for more details.")
    @GetMapping(path = "/getJobApplicationByAppId/v1/{appId}", consumes = "application/json")
    public ResponseEntity<JobApplication> getJobApplicationByAppId(@PathVariable Long appId) throws DataNotFoundException {

        // Invoking the service layer to fetch the job application by passing appId as the input.
        final JobApplication jobApplicationRec = this.jobApplicatnSvc.getApplcnsByAppId(appId);
        return ResponseEntity.ok().body(jobApplicationRec);
    }

    /**
     * This api finds the job application from the data store on the basis
     * of the job id.
     * It returns back Http.OK - 200 if it finds the job application successfully
     * from the data store.
     *
     * @param jobId : The job id for which the application was submitted.
     * @return : {@link ResponseEntity<JobApplication>}
     * @throws DataNotFoundException : This exception is thrown when no job
     *                              application is found for the given job id.
     */
    @ApiOperation(value = "Get a Job Application by job id", notes = "This API fetches Job Application by job id")
    @ApiResponses(value = {@ApiResponse(code = 500, message = "Something went wrong in the service, please "
                  + "contact the system administrator - Email - lalitkulkarniofficial@gmail.com"),
            @ApiResponse(code = 201, message = "Job Application has been successfully fetched the system.")})
            @ApiResponse(code = 400, message = "Bad input request.Please check the error description for more details.")
    @GetMapping(path = "/getJobApplicationsByJobId/v1/{jobId}", consumes = "application/json")
    public ResponseEntity<List<JobApplication>> getJobApplicationByJobId(@PathVariable Long jobId) throws DataNotFoundException,
            RecAsstntTechnicalException {
        final List<JobApplication> jobApplicationRec = this.jobApplicatnSvc.getApplicationsByJobId(jobId);
        return ResponseEntity.ok().body(jobApplicationRec);
    }

    /**
     * This api updates the status of the job application and then triggers
     * a notification to inform the same.
     *
     * @param appId     : It accepts the application id of the application whoose
     *                  status needs to be updated.
     * @param appStatus : It accepts the application status that needs to be updated.
     * @return : {@link ResponseEntity<JobApplication>}
     * @throws DataNotFoundException : This exception is thrown when no job application is found
     *                               for the given app id.
     */
    @PatchMapping(path = "/updateJobApplnStatus/v1/{appId}/{appStatus}", consumes = "application/json")
    public ResponseEntity<JobApplication> patchJobApplicationStatus
    (@PathVariable long appId, @PathVariable JobApplicationStatus appStatus)
            throws DataNotFoundException, RecAsstntTechnicalException {

        // Invoke the service layer to update the job application status in the data store.
        JobApplication jobApplication = this.jobApplicatnSvc.updateJobApplnStatus(appStatus, appId);
        return ResponseEntity.ok().body(jobApplication);
    }

}
