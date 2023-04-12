package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ticket")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ticketCode")
    private Integer ticketCode;
	@ManyToOne
	@JoinColumn(name = "eventId")
	private Event event;
	@ManyToOne
	@JoinColumn(name = "fiscalCode")
	private Visitor visitor;
	public Integer getTicketCode() {
		return ticketCode;
	}
	public void setTicketCode(Integer ticketCode) {
		this.ticketCode = ticketCode;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public Visitor getVisitor() {
		return visitor;
	}
	public void setVisitor(Visitor visitor) {
		this.visitor = visitor;
	}
	
}
