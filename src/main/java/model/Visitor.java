package model;

import java.sql.SQLException;

public class Visitor {
    private final String fiscalCode;
    private final Integer expoId;
    private final String name;
    private final String surname;
    private final String email;

    public Visitor(String fiscalCode, Integer expoId, String name, String surname, String email) {
        this.fiscalCode = fiscalCode;
        this.expoId = expoId;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public Integer getExpoId() {
        return expoId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public Ticket purchaseEventTicket(Event event) {
        return event.bookEvent(this);
    }
}
