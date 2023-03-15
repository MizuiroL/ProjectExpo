package model;

import java.sql.SQLException;
import java.time.LocalDateTime;

public interface ExhibitionArea {
    public boolean isOccupied(LocalDateTime start, LocalDateTime end) throws SQLException;
    public Exhibition occupy(Exhibitor exhibitor, String exhibitorName, LocalDateTime start, LocalDateTime end) throws SQLException;
}
