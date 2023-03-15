package model;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ExhibitionArea {
    public boolean isOccupied(LocalDateTime start, LocalDateTime end) throws SQLException;
    public Exhibition occupy(Exhibitor exhibitor, String exhibitorName, LocalDateTime start, LocalDateTime end) throws SQLException;
}
