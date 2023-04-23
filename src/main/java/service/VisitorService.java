package service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import model.Event;
import model.Ticket;
import model.Visitor;

public class VisitorService {
	private EntityManager entityManager;
	
	public VisitorService() {
		new ExpoEntityManagerFactory() {
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

	public Ticket bookEvent(Event event, Visitor visitor) {
		// TODO check if ticket is null
		return visitor.purchaseEventTicket(event);
	}
}
