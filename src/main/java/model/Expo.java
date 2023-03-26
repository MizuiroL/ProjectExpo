package model;

import java.sql.SQLException;
import java.time.LocalDateTime;

public interface Expo {
    Exhibit concedeExhibitArea(Exhibitor exhibitor, LocalDateTime start, LocalDateTime end);
    
    Integer getExpoId();
}
