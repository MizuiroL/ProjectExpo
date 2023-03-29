package model;

import java.time.LocalDateTime;

public interface Expo {
    Exhibit assignExhibitArea(Exhibitor exhibitor, LocalDateTime start, LocalDateTime end);
    
    Integer getExpoId();
}
