package model;

import java.time.LocalDateTime;

public interface ExhibitArea {
    boolean isOccupied(LocalDateTime start, LocalDateTime end);
    Exhibit occupy(Exhibit exhibit);
    
    Integer getExhibitAreaId();
}
