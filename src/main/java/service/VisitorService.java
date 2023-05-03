package service;

import java.util.List;

import exceptions.EventAlreadyBookedException;
import jakarta.persistence.EntityManager;
import jdbc.ExpoDAO;
import jdbc.TicketDAO;
import model.Event;
import model.ExpoManager;
import model.Ticket;
import model.Visitor;

public class VisitorService {
	private EntityManager entityManager;

	public VisitorService() {
		entityManager = new ExpoEntityManagerFactory() {
		}.getManager();
	}

	public Visitor createVisitor(String fiscalCode, String name, String surname, String email) {
		Visitor visitor = new Visitor();
		visitor.setFiscalCode(fiscalCode);
		visitor.setName(name);
		visitor.setSurname(surname);
		visitor.setEmail(email);
		entityManager.persist(visitor);
		return visitor;
	}

	public Visitor createVisitor(Visitor visitor) {
		entityManager.persist(visitor);
		return visitor;
	}

	public Visitor findVisitor(String fiscalCode) {
		Visitor visitor = entityManager.find(Visitor.class, fiscalCode);
		return visitor;
	}

	public Visitor updateVisitor(Visitor visitor) {
		entityManager.merge(visitor);
		return visitor;
	}

	public Ticket bookEvent(ExpoManager expo, Event event, Visitor visitor) throws EventAlreadyBookedException {
		List<Event> alreadyBookedEvents = new ExpoManagerService().getBookedEvents(expo, visitor);
		//if(alreadyBookedEvents.contains(event)) {
		for(Event e : alreadyBookedEvents) {
			if(e.getExhibitId() == event.getExhibitId())
				throw new EventAlreadyBookedException("You have already booked this event");
		}
		Ticket ticket = visitor.purchaseEventTicket(event);
		ticket = new TicketDAO().newTicket(ticket);
		new EventService().updateEvent(event);

		return ticket;
	}
}
