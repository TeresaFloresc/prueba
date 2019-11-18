package com.rapifarma.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "patients")
public class Patient {
	

	@Id
	@Column(name = "patient_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(max = 100, message = "El nombre de contener a lo más 100 caracteres")
	@NotEmpty(message = "Ingrese nombre del paciente")
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Size(max = 100, message = "El Primer apellido debe contener a lo más 100 caracteres")
	@NotEmpty(message = "Ingrese el Primer apellido del paciente")
	@Column(name = "firstsurname", nullable = false, length = 100)
	private String firstsurname;
	
	@Size(max = 100, message = "El Segundo apellido debe contener a lo más 100 caracteres")
	@NotEmpty(message = "Ingrese el Segundo apellido del paciente")
	@Column(name = "secondsurname", nullable = false, length = 100)
	private String secondsurname;
	
	@NotEmpty(message = "Ingrese el número de DNI del paciente")
	@Size(max = 8, min = 8, message = "El DNI es únicamente de 8 digitos")
	@Column(name = "dni", nullable = false, length = 8)
	private String dni;
		
	@Column(name = "sex", nullable = false, length = 1)
	private String sex;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getFirstsurname() {
		return firstsurname;
	}


	public void setFirstsurname(String firstsurname) {
		this.firstsurname = firstsurname;
	}


	public String getSecondsurname() {
		return secondsurname;
	}


	public void setSecondsurname(String secondsurname) {
		this.secondsurname = secondsurname;
	}


	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getSex() {
		return sex;
	}
}
