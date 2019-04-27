package com.recruitment.assistant.endpoints;

import com.recruitment.assistant.exception.DataNotFoundException;
import com.recruitment.assistant.exception.RecAsstntTechnicalException;
import com.recruitment.assistant.model.JobOffer;
import com.recruitment.assistant.service.IJobOfferSvc;
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
 * of the job offers like - creation fetching the job offers on the basis of various
 * inputs.
 *
 * Please refer the swagger link {@link "http://localhost:9098/swagger-ui.html"} for
 * detailed information regarding the API , input and output parameters and Http status codes.
 *
 * @since 26-04-2019
 * @version 1.0
 * @author lalit
 */
@Validated
@RestController
@RequestMapping("/job-offer/v1")
@Api(tags = "Job offer API", value = "This API houses all the lifecycle of the job  " +
		"offer.")
public class JobOfferController {
	
	@Autowired
	private IJobOfferSvc jobOfferSvc;

	@ApiOperation(value = "Create a new Job Offer.",
			      notes = "This API creates a new Job Offer and stores in the data store.")
	@ApiResponses(value = {@ApiResponse(code = 500, message = "Something went wrong in the service, please "
			              + "contact the system administrator - Email - lalitkulkarniofficial@gmail.com"),
			@ApiResponse(code = 201, message = "Job offer has been successfully created in the system.") })
	        @ApiResponse(code = 400, message = "Bad input request.Please check the error description for more details.")
	@PostMapping(path = "/newJobOffer/v1", consumes = "application/json")
	public ResponseEntity<JobOffer> createNewJobOffer(@Valid @RequestBody JobOffer jobOffer)
			throws RecAsstntTechnicalException {
	
		final JobOffer jobOfferSaved = this.jobOfferSvc.createNewJobOffer(jobOffer);

		final URI uri =
				MvcUriComponentsBuilder.fromController(getClass())
						.path("/{id}")
						.buildAndExpand(jobOfferSaved.getJobId())
						.toUri();

		return ResponseEntity.created(uri).body(jobOfferSaved);
  }

	@ApiOperation(value = "Get all job offers available in the system.",
			notes = "This API fetches the list of job offers available in the system.")
	@ApiResponses(value = {@ApiResponse(code = 500, message = "Something went wrong in the" +
			        " service, please contact the system administrator - " +
			        "Email - lalitkulkarniofficial@gmail.com"), @ApiResponse(code = 200, message =
			        "Job offers have been successfully fetched") })
	@ApiResponse(code = 400, message = "Bad input request.Please check the error description"
			+ " for more details.")
  	@GetMapping(value = "/getAllJobOffers/v1",consumes = "application/json")
  	public ResponseEntity<List<JobOffer>> getAllJobOffers(){

		final List<JobOffer> jobOffers = this.jobOfferSvc.getAllJobOffers();
		return ResponseEntity.ok().body(jobOffers);
	}

	@ApiOperation(value = "Get all job offers available by job id.",
			notes = "This API fetches the list of job offers available in the system by job id")
	@ApiResponses(value = {@ApiResponse(code = 500, message = "Something went wrong in the" +
			" service, please contact the system administrator - " +
			"Email - lalitkulkarniofficial@gmail.com"), @ApiResponse(code = 200, message =
			"Job offers have been successfully fetched") })
	@ApiResponse(code = 400, message = "Bad input request.Please check the error description"
			+ " for more details.")
	@GetMapping("/getJobOfferByJobId/v1/{jobId}")
	public ResponseEntity<JobOffer> getJobOfferByJobId(@PathVariable long jobId)
			throws DataNotFoundException {

		JobOffer jobOffer = this.jobOfferSvc.findJobOfferByJobId(jobId);
		return ResponseEntity.ok().body(jobOffer);
	}

	@ApiOperation(value = "Get all job offers available by job title.",
			notes = "This API fetches the job offers available in the system by job title")
	@ApiResponses(value = {@ApiResponse(code = 500, message = "Something went wrong in the" +
			" service, please contact the system administrator - " +
			"Email - lalitkulkarniofficial@gmail.com"), @ApiResponse(code = 200, message =
			"Job offers have been successfully fetched") })
	@ApiResponse(code = 400, message = "Bad input request.Please check the error description"
			+ " for more details.")
	@GetMapping("/getJobOfferByJobTitle/v1/{jobTitle}")
	public ResponseEntity<JobOffer> getJobOfferByJobTitle(@PathVariable String jobTitle)
			throws DataNotFoundException {

		JobOffer jobOffer = this.jobOfferSvc.findJobOfferByJobTitle(jobTitle);
		return ResponseEntity.ok().body(jobOffer);
	}

}
