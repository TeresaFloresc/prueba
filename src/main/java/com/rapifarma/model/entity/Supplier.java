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
@Table(name = "suppliers")
public class Supplier {
	
	@Id
	@Column(name = "supplier_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(max = 100, message = "El nombre de contener a lo más 100 caracteres")
	@NotEmpty(message = "Ingrese nombre del proveedor")
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Size(max = 255, message = "La dirección debe contener a lo más 255 caracteres")
	@NotEmpty(message = "Ingrese una dirección")
	@Column(name = "adress", nullable = false, length = 255)
	private String adress;
	
	@Column(name = "email", nullable = false, length = 100)
	private String email;
	
	@NotEmpty(message = "Ingrese un número de teléfono")
	@Column(name = "phone", nullable = false, length = 20)
	private String phone;
	
	@NotEmpty(message = "Ingrese un contacto")
	@Column(name = "contact", nullable = false, length = 100)
	private String contact;
	
	@Size(min = 11, max = 11, message = "El RUC es de a lo máx 11 digitos")
	@NotEmpty(message = "Ingrese RUC del proveedor")
	@Column(name = "ruc", nullable = false, length = 11)
	private String ruc;

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

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
		
}
