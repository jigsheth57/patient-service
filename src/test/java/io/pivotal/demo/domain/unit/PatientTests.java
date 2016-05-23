package io.pivotal.demo.domain.unit;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import io.pivotal.demo.domain.Patient;
import junit.framework.TestCase;

/**
 * Unit tests for the <code>Patient</code> domain object.
 * 
 * @author Jignesh Sheth
 *
 */
public class PatientTests {

	private Patient patient;
	
	private static final String PATIENT_TO_JSON_PATTERN = "{\"Id\": %d, \"name\": \"%s\", \"gender\": \"%s\", \"phone\": \"%s\", \"birthdate\": \"%s\", \"active\": \"%s\"}";
	
	/**
	 * Builds the Patient object to test
	 */
	@Before
	public void buildPatient() {
		final String name = "David Lee";
		final String gender = "male";
		final String phone = "312-555-1212";
		final Date birthDate = new Date(2005, 1, 1);
		final Boolean active = Boolean.TRUE;
		patient = new Patient(name,gender,phone,birthDate,active);
	}
	
	/**
	 * Tests the overridden toString method is correct.
	 */
	@Test
	public void testToString() {
		String expectedToString = String.format(PATIENT_TO_JSON_PATTERN,
				patient.getId(), 
				patient.getName(), 
				patient.getGender(),
				patient.getPhone(),
				patient.getBirthDate(),
				patient.getActive());

		TestCase.assertEquals(
				"The toString method should match the pattern [" 
						+ expectedToString + "].",
				expectedToString, patient.toString());
	}
}
