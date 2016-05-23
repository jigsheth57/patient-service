package io.pivotal.demo.controller;

import java.sql.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.demo.domain.Patient;
import io.pivotal.demo.repository.PatientRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class PatientController {

	@Autowired
	protected PatientRepository patientRepo;
	private static final Log log = LogFactory.getLog(PatientController.class);
	
	@RequestMapping(value="/patients", method=RequestMethod.GET)
	@ApiOperation(value = "Retrieve all patients", notes = "Calls patient repository to get all of the patients", response = Patient.class, responseContainer = "List")
	public @ResponseBody List<Patient> getAllContacts() {
		return patientRepo.findAll();
	}
	
	@RequestMapping(value = "/patient/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Retrieve patient by id", notes = "Calls patient repository to retrieve patient by id", response = Patient.class)
	public ResponseEntity<?> get(@ApiParam(value = "Patient ID", required = true) @PathVariable Long id) {
		Patient patient = null;
		HttpStatus httpstatus = HttpStatus.OK;
		if(patientRepo.exists(id)) {
			patient = patientRepo.findOne(id);
			log.debug(String.format("Found patient for id %d: [%s]",id, patient));
		} else {
			patient = new Patient();
			httpstatus = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<Patient>(patient, new HttpHeaders(), httpstatus);

	}
	
    @RequestMapping(path="/patient", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieve patient by either name or birthdate.",notes = "Calls patient repository to retrieve patient by either searching for name or birthdate.", response = Patient.class, responseContainer = "List")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", value = "Patient's name", required = false, dataType = "string", paramType = "query", defaultValue="John Smith"),
        @ApiImplicitParam(name = "birthdate", value = "Patient's birthdate", required = false, dataType = "date", paramType = "query", defaultValue="1980-01-01")
      })
	public @ResponseBody List<Patient> getPatientByNameOrBirthdate(@RequestParam(value="name", required=false) String name, @RequestParam(value="birthdate",required=false) Date birthdate) {
		return patientRepo.findByNameOrBirthDate(name, birthdate);
	}

	@RequestMapping(value = "/patient/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete patient by id",notes = "Calls patient repository to remove patient by id")
	public ResponseEntity<?> delete(@ApiParam(value = "Patient ID", required = true) @PathVariable Long id) {
		HttpStatus httpstatus = HttpStatus.OK;
		if(patientRepo.exists(id)) {
			patientRepo.delete(id);
			log.debug(String.format("Remove patient for id %d",id));
		} else {
			httpstatus = HttpStatus.NOT_FOUND;
		}
		ResponseEntity.status(httpstatus);
		return ResponseEntity.noContent().build();

	}

	@RequestMapping(value = "/patient", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create new patient",notes = "Calls patient repository to create new patient", response = Patient.class)
	public ResponseEntity<?> post(@ApiParam(value = "Patient model", required = true) @RequestBody Patient patient) {
		patient.setId(null);
		patient = patientRepo.save(patient);
		HttpStatus httpstatus = HttpStatus.CREATED;
		log.debug(String.format("Created new contact with id %d: [%s]",patient.getId(), patient));
		return new ResponseEntity<Patient>(patient, new HttpHeaders(), httpstatus);

	}
	
	@RequestMapping(value = "/patient/{id}", method = RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update patient by id",notes = "Calls patient repository to update existing patient by id", response = Patient.class)
	public ResponseEntity<?> put(@ApiParam(value = "Patient ID", required = true) @PathVariable Long id, @ApiParam(value = "Patient model", required = true) @RequestBody Patient patient) {
		HttpStatus httpstatus = HttpStatus.OK;
		if(patientRepo.exists(id)) {
			patient.setId(id);
			patient = patientRepo.save(patient);
			log.debug(String.format("Updated patient for id %d: [%s]",id, patient));
		} else {
			httpstatus = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<Patient>(patient, new HttpHeaders(), httpstatus);

	}
}
