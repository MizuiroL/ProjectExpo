package model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "event")
public class Event extends AbstractExhibit {
	@Column(name = "eventTotalSeats")
	private Integer eventTotalSeats;
	@Column(name = "eventAvailableSeats")
	private Integer eventAvailableSeats;
	@Transient
	private List<EventObserver> observerList;

	public Integer getEventAvailableSeats() {
		return eventAvailableSeats;
	}

	public void setEventAvailableSeats(Integer eventAvailableSeats) {
		this.eventAvailableSeats = eventAvailableSeats;
	}

	public Integer getEventTotalSeats() {
		return eventTotalSeats;
	}

	public Ticket bookEvent(Visitor visitor) {
		Ticket ticket = new Ticket();
		ticket.setEvent(this);
		ticket.setVisitor(visitor);

		if (eventAvailableSeats > 0) {
			eventAvailableSeats -= 1;
			// ticket = new TicketDAO().newTicket(ticket);
			notifyObservers();
		}

		subscribe(visitor);
		return ticket;
	}

	public void subscribe(EventObserver observer) {
		if (observerList == null) {
			observerList = new ArrayList<>();
		}

		if (observer != null && !observerList.contains(observer)) {
			observerList.add(observer);
		}
	}

	public void unsubscribe(EventObserver observer) {
		observerList.remove(observer);
	}

	public void notifyObservers() {
		if (observerList != null) {
			for (EventObserver observer : observerList) {
				observer.update(this);
			}
		}
	}

	public void setEventTotalSeats(Integer eventTotalSeats) {
		this.eventTotalSeats = eventTotalSeats;
	}
}
