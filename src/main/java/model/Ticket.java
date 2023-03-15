package model;

public class Ticket {
    private Integer ticketCode;
    private Integer eventId;
    private String fiscalCode;

    public Ticket(Integer ticketCode, Integer eventId, String fiscalCode) {
        this.ticketCode = ticketCode;
        this.eventId = eventId;
        this.fiscalCode = fiscalCode;
    }

    public Ticket(Integer eventId, String fiscalCode) {
        this.eventId = eventId;
        this.fiscalCode = fiscalCode;
    }

    public Integer getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(Integer ticketCode) {
        this.ticketCode = ticketCode;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }
}
