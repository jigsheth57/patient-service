package io.pivotal.demo.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import io.pivotal.demo.domain.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{
	
	List<Patient> findAll();

	List<Patient> findByNameOrBirthDate(@Param("name") String name, @Param("birthdate") Date birthDate); 
}
