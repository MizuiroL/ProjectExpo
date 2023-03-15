package model;

import java.sql.SQLException;
import java.time.LocalDateTime;

public interface Expo {
    public Exhibition concedeExhibitionArea(Exhibitor exhibitor, LocalDateTime start, LocalDateTime end) throws SQLException;
}
