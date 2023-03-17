package model;

import java.sql.SQLException;
import java.time.LocalDateTime;

public interface ExhibitArea {
    public boolean isOccupied(LocalDateTime start, LocalDateTime end) throws SQLException;
    public Exhibit occupy(Exhibitor exhibitor, String exhibitorName, LocalDateTime start, LocalDateTime end) throws SQLException;
}
