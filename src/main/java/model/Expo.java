package model;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public interface Expo {
    public Exhibition concedeExhibitionArea(Exhibitor exhibitor, LocalDateTime start, LocalDateTime end) throws SQLException;
}
