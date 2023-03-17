package model;

import jdbc.TicketDataAccess;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Event extends Exhibit {
    private final Integer eventTotalSeats;
    private Integer eventAvailableSeats;
    public Event(Integer exhibitId, Integer exhibitAreaId, Integer exhibitorId, String exhibitName, LocalDateTime exhibitStartDate, LocalDateTime exhibitEndDate, Integer eventTotalSeats, Integer eventAvailableSeats) {
        super(exhibitId, exhibitAreaId, exhibitorId, exhibitName, exhibitStartDate, exhibitEndDate);
        this.eventTotalSeats = eventTotalSeats;
        this.eventAvailableSeats = eventAvailableSeats;
    }

    public Ticket bookEvent(Visitor visitor) {
        Ticket ticket = new Ticket(this.exhibitId, visitor.getFiscalCode());
        if(eventAvailableSeats > 0) {
            eventAvailableSeats -= 1;
            ticket = new TicketDataAccess().newTicket(ticket);
        }
        return ticket;
    }
}
