package model;

import jdbc.TicketDAO;

import java.time.LocalDateTime;
import java.util.List;

public class Event extends Exhibit {
    private final Integer eventTotalSeats;
    private Integer eventAvailableSeats;
    private List<EventObserver> observerList;

    public Event(Integer exhibitId, Integer exhibitAreaId, Integer exhibitorId, String exhibitName, LocalDateTime exhibitStartDate, LocalDateTime exhibitEndDate, Integer eventTotalSeats, Integer eventAvailableSeats) {
        super(exhibitId, exhibitAreaId, exhibitorId, exhibitName, exhibitStartDate, exhibitEndDate);
        this.eventTotalSeats = eventTotalSeats;
        this.eventAvailableSeats = eventAvailableSeats;
    }
    
    

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
        Ticket ticket = new Ticket(this.exhibitId, visitor.getFiscalCode());
        if (eventAvailableSeats > 0) {
            eventAvailableSeats -= 1;
            ticket = new TicketDAO().newTicket(ticket);
            notifyObservers();
        }
        subscribe(visitor);
        return ticket;
    }
    
    public void subscribe(EventObserver observer) {
    	if (observer != null && observerList.contains(observer)) {
    		observerList.add(observer);
    	}
    }
    
    public void unsubscribe(EventObserver observer) {
    	observerList.remove(observer);
    }
    
    public void notifyObservers() {
    	for (EventObserver observer : observerList) {
    		observer.update(this);
    	}
    }
}
