package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "visitor")
public class Visitor implements EventObserver {
	@Id
	@Column(name = "fiscalCode")
	private String fiscalCode;
	@Column(name = "name")
	private String name;
	@Column(name = "surname")
	private String surname;
	@Column(name = "email")
	private String email;
	@Transient
	private Integer expoId;

	public String getFiscalCode() {
		return fiscalCode;
	}

	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getExpoId() {
		return expoId;
	}

	public void setExpoId(Integer expoId) {
		this.expoId = expoId;
	}

	public Ticket purchaseEventTicket(Event event) {
		return event.bookEvent(this);
	}

	@Override
	public void update(Event e) {
		// TODO Auto-generated method stub

	}
}
