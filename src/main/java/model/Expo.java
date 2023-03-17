package model;

import java.sql.SQLException;
import java.time.LocalDateTime;

public interface Expo {
    public Exhibit concedeExhibitArea(Exhibitor exhibitor, LocalDateTime start, LocalDateTime end) throws SQLException;
}
