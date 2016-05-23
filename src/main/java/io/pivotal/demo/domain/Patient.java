package io.pivotal.demo.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.pivotal.demo.repository.LogListener;

@Entity
@EntityListeners(LogListener.class)
public class Patient {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long Id;
	
	@Column(nullable=false)
	private String name, gender, phone;

	@Column(nullable=false)
	private Date birthDate;

	@Column(nullable=false)
	private Boolean active;

	public Patient() {}
	
	public Patient(String name, String gender, String phone, Date birthDate, Boolean active) {
		this.name = name;
		this.gender = gender;
		this.phone = phone;
		this.birthDate = birthDate;
		this.active = active;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public Date getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}


	public Boolean getActive() {
		return active;
	}


	public void setActive(Boolean active) {
		this.active = active;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return String.format(
				"{\"Id\": %d, \"name\": \"%s\", \"gender\": \"%s\", \"phone\": \"%s\", \"birthdate\": \"%s\", \"active\": \"%s\"}",
				Id, 
				name, 
				gender,
				phone,
				birthDate,
				active);
	}
	
	

}
