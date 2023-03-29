package model;

import java.time.LocalDateTime;

public interface ExhibitArea {
    boolean isOccupied(LocalDateTime start, LocalDateTime end);
    Exhibit occupy(Exhibitor exhibitor, String exhibitorName, LocalDateTime start, LocalDateTime end);
}
