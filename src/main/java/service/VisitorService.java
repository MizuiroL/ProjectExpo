package service;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

	public Ticket bookEvent(ExpoManager expo, Event event, Visitor visitor) {
		List<Event> alreadyBookedEvents = new ExpoDAO().getBookedEvents(expo.getExpoId(), visitor.getFiscalCode());
		if(alreadyBookedEvents.contains(event)) {
			// TODO throw alreadyPurchasedException
		}
		Ticket ticket = visitor.purchaseEventTicket(event);
		entityManager.persist(ticket);

		return ticket;
	}
}
