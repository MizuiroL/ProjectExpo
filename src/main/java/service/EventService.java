package service;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.EntityManager;
import model.Event;
import model.Exhibitor;
import model.FixedExhibitArea;

public class EventService {
private EntityManager entityManager;
	
	public EventService() {
		entityManager = new PersistenceEntityManagerFactory().getManager();
	}
	
	public Event createEvent(FixedExhibitArea exhibitArea, Exhibitor exhibitor, String exhibitName,LocalDate exhibitStartDate,LocalTime exhibitStartTime,LocalDate exhibitEndDate,LocalTime exhibitEndTime, Integer eventTotalSeats, Integer eventAvailableSeats) {
		Event event = new Event();
		event.setExhibitArea(exhibitArea);
		event.setExhibitor(exhibitor);
		event.setExhibitName(exhibitName);
		event.setExhibitStartDate(exhibitStartDate);
		event.setExhibitStartTime(exhibitStartTime);
		event.setExhibitEndDate(exhibitEndDate);
		event.setExhibitEndTime(exhibitEndTime);
		event.setEventTotalSeats(eventTotalSeats);
		event.setEventAvailableSeats(eventAvailableSeats);
		
		entityManager.persist(event);
		return event;
		
	}
	
	public Event createEvent(Event event) {
		entityManager.persist(event);
		return event;
	}
	
	public Event findEvent(Integer eventId) {
		Event event = entityManager.find(Event.class, eventId);
		return event;
	}
	
	public Event updateEvent(Event event) {
		entityManager.merge(event);
		return event;
	}
}
