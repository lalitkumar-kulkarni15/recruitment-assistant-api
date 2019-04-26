package com.recruitment.assistant.endpoints;

import com.recruitment.assistant.exception.DataNotFoundException;
import com.recruitment.assistant.model.JobCreationResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.recruitment.assistant.exception.RecAsstntTechnicalException;
import com.recruitment.assistant.model.JobOffer;
import com.recruitment.assistant.service.IJobOfferSvc;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/job-offer/v1")
@Api(tags = "Job offer API", value = "This API houses all the lifecycle of the job  " +
		"offer.")
public class JobOfferController {
	
	@Autowired
	private IJobOfferSvc jobOfferSvc;

	@ApiOperation(value = "Get the average day and nightly temperature daily for 3 consecutive days.",
			notes = "This API invokes the 3rd party API - https://openweathermap.org to fetch the weather "
					+ "forecast data i.e. temperature in degrees and pressure in hpa parameters.")
	@ApiResponses(value = {@ApiResponse(code = 500, message = "Something went wrong in the service, please "
			+ "contact the system administrator - Email - lalitkulkarniofficial@gmail.com"),
			@ApiResponse(code = 200, message = "Weather forecast has been successfully fetched.") })
	@ApiResponse(code = 400, message = "Bad input request.Please check the error description"
			+ " for more details.")
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

  	@GetMapping(value = "/getAllJobOffers/v1",consumes = "application/json")
  	public ResponseEntity<List<JobOffer>> getAllJobOffers(){

		final List<JobOffer> jobOffers = this.jobOfferSvc.getAllJobOffers();
		return ResponseEntity.ok().body(jobOffers);
	}

	@GetMapping("/getJobOfferByJobId/v1/{jobId}")
	public ResponseEntity<JobOffer> getJobOfferByJobId(@PathVariable long jobId)
			throws DataNotFoundException {

		JobOffer jobOffer = this.jobOfferSvc.findJobOfferByJobId(jobId);
		return ResponseEntity.ok().body(jobOffer);
	}

	@GetMapping("/getJobOfferByJobTitle/v1/{jobTitle}")
	public ResponseEntity<JobOffer> getJobOfferByJobTitle(@PathVariable String jobTitle)
			throws DataNotFoundException {

		JobOffer jobOffer = this.jobOfferSvc.findJobOfferByJobTitle(jobTitle);
		return ResponseEntity.ok().body(jobOffer);
	}

}
