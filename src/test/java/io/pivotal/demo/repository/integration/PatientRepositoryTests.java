package io.pivotal.demo.repository.integration;

import java.sql.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import io.pivotal.demo.PatientDataServiceApplication;
import io.pivotal.demo.domain.Patient;
import io.pivotal.demo.repository.PatientRepository;
import junit.framework.TestCase;

/**
 * Integration tests for the <code>PatientRepository</code>
 * JPA repository interface.
 * 
 * The <code>SpringApplicationConfiguration</code> annotation
 * ensures that the embedded database is started and configured
 * for the integration tests.
 * 
 * Most of the methods tested (<code>findOne</code>, <code>save</code>),
 * are provided by the base JpaRepository class.
 * 
 * @author Jignesh Sheth
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PatientDataServiceApplication.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@WebAppConfiguration
public class PatientRepositoryTests {

	//The repository to test.
	@Autowired
	PatientRepository patientRepo;
	
	/**
	 * Tests the repository's findAll method, by asserting that
	 * there is more than 0 replies returned.
	 */
	@Test
	public void testFindAll() {
		Iterable<Patient> patients = patientRepo.findAll();
		TestCase.assertNotNull(
				"Find all should return at least 1 result.",
				patients.iterator().next());
	}

	/**
	 * Tests the repository's findByNameOrBirthDate method, by
	 * getting the first patient from findAll, and then 
	 * using that patient's name to call and assert the 
	 * findByNameOrBirthDate method's results.
	 */
	@Test
	public void testFindByNameOrBirthDate() {
		
		Patient firstPatient = patientRepo.findAll().iterator().next();
		List<Patient> resultOfFindByNameOrBirthDate = patientRepo.findByNameOrBirthDate(firstPatient.getName(),firstPatient.getBirthDate());
		TestCase.assertEquals(
				firstPatient.getName(), 
				resultOfFindByNameOrBirthDate.get(0).getName());
		
	}

	/**
	 * Tests the repository's save method, by
	 * creating a new Patient object, saving it,
	 * fetching it back from the repository, and
	 * asserting that it was fetched properly.
	 */
	@Test
	public void testSaveNew() {
		
		final String name = "John Lee";
		final String gender = "male";
		final String phone = "312-555-1212";
		final Date birthDate = new Date(1980, 1, 1);
		final Boolean active = Boolean.TRUE;
		Patient newPatient = new Patient(name,gender,phone,birthDate,active);
		newPatient = patientRepo.save(newPatient);
		Patient savedPatient = 
				patientRepo.findOne(newPatient.getId());
		TestCase.assertEquals(
				name, savedPatient.getName());
		TestCase.assertEquals(
				gender, savedPatient.getGender());
		TestCase.assertEquals(
				phone, savedPatient.getPhone());
		TestCase.assertEquals(
				birthDate, savedPatient.getBirthDate());
		TestCase.assertEquals(
				active, savedPatient.getActive());
	}

	/**
	 * Tests the repository's update method, by
	 * creating a new Patient object, saving it,
	 * fetching it back from the repository, and 
	 * updating the attributes and asserting
	 * that it was fetched properly.
	 */
	@Test
	public void testUpdateExisting() {
		
		final String name = "Sara Lee";
		final String gender = "female";
		final String phone = "312-555-1212";
		final Date birthDate = new Date(1980, 1, 1);
		final Boolean active = Boolean.TRUE;
		Patient newPatient = new Patient(name,gender,phone,birthDate,active);
		newPatient = patientRepo.save(newPatient);
		Patient updatedPatient = newPatient;
		updatedPatient.setName(name+"_updated");
		updatedPatient.setGender(gender);
		updatedPatient.setPhone("847-555-1212");
		updatedPatient.setBirthDate(birthDate);
		updatedPatient.setActive(active);
		updatedPatient = patientRepo.save(updatedPatient);
		Patient savedPatient = 
				patientRepo.findOne(updatedPatient.getId());
		TestCase.assertEquals(
				name+"_updated", savedPatient.getName());
		TestCase.assertEquals(
				gender, savedPatient.getGender());
		TestCase.assertEquals(
				"847-555-1212", savedPatient.getPhone());
	}

	/**
	 * Tests the repository's delete method, by
	 * creating a new Patient object, saving it 
	 * and then deleting it and then trying to
	 * fetch it back from the repository, and
	 * asserting that it was not available.
	 */
	@Test
	public void testDelete() {
		
		final String name = "Sara Lee";
		final String gender = "female";
		final String phone = "312-555-1212";
		final Date birthDate = new Date(1980, 1, 1);
		final Boolean active = Boolean.TRUE;
		Patient newPatient = new Patient(name,gender,phone,birthDate,active);
		newPatient = patientRepo.save(newPatient);
		Long patientID = newPatient.getId();
		patientRepo.delete(patientID);
		Patient savedPatient = 
				patientRepo.findOne(patientID);
		TestCase.assertNull(savedPatient);
	}
}
