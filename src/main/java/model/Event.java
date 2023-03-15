package model;

import jdbc.TicketDataAccess;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Event extends Exhibition {
    private final Integer eventTotalSeats;
    private Integer eventAvailableSeats;
    public Event(Integer exhibitionId, Integer exhibitionAreaId, Integer exhibitorId, String exhibitionName, LocalDateTime exhibitionStartDate, LocalDateTime exhibitionEndDate, Integer eventTotalSeats, Integer eventAvailableSeats) {
        super(exhibitionId, exhibitionAreaId, exhibitorId, exhibitionName, exhibitionStartDate, exhibitionEndDate);
        this.eventTotalSeats = eventTotalSeats;
        this.eventAvailableSeats = eventAvailableSeats;
    }

    public Ticket bookEvent(Visitor visitor) throws SQLException {
        Ticket ticket = new Ticket(this.exhibitionId, visitor.getFiscalCode());
        if(eventAvailableSeats > 0) {
            eventAvailableSeats -= 1;
            ticket = new TicketDataAccess().newTicket(ticket);
        }
        return ticket;
    }
}
