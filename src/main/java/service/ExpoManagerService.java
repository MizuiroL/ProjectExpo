package service;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.EntityManager;
import jdbc.ExpoDAO;
import model.Event;
import model.Exhibit;
import model.ExpoManager;
import model.Visitor;

public class ExpoManagerService {
	private EntityManager entityManager;
	
	public ExpoManagerService() {
		entityManager = new ExpoEntityManagerFactory() {}.getManager();
	}
	
	public ExpoManager createExpo(String province, String comune, String address, String streetNumber, LocalDate startDate, LocalDate endDate) {
		ExpoManager expo = new ExpoManager();
		expo.setProvince(province);
		expo.setComune(comune);
		expo.setAddress(address);
		expo.setStreetNumber(streetNumber);
		expo.setStartDate(startDate);
		expo.setEndDate(endDate);
		entityManager.persist(expo);
		return expo;
	}
	
	public ExpoManager createExpo(ExpoManager expo) {
		entityManager.persist(expo);
		return expo;
	}
	
	public ExpoManager findExpo(Integer expoId) {
		ExpoManager expo = null;
		expo = entityManager.find(ExpoManager.class, expoId);
		return expo;
	}
	
	public ExpoManager updateExpo(ExpoManager expo) {
		entityManager.merge(expo);
		return expo;
	}
	
	public void deleteExpo(ExpoManager expo) {
		entityManager.remove(expo);
	}
	
	public List<Exhibit> getPresentExhibits(ExpoManager expo) {
		List<Exhibit> exhibitList = new ExpoDAO().getStartedExhibits(expo.getExpoId());
		return exhibitList;
	}
	
	public List<Event> getFutureEvents(ExpoManager expo) {
		List<Event> eventList = new ExpoDAO().getFutureEvents(expo.getExpoId());
		return eventList;
	}
	
	public List<Event> getBookedEvents(ExpoManager expo, Visitor visitor) {
		List<Event> eventList = new ExpoDAO().getBookedEvents(expo.getExpoId(), visitor.getFiscalCode());
		return eventList;
	}
}
