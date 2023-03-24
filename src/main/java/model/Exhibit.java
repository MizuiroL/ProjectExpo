package model;

import jdbc.ExhibitDAO;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Exhibit {
    Integer exhibitId;
    Integer exhibitAreaId;
    Integer exhibitorId;
    String exhibitName;
    LocalDateTime exhibitStartDate;
    LocalDateTime exhibitEndDate;

    // Constructor without primary key
    public Exhibit(Integer exhibitAreaId, Integer exhibitorId, String exhibitName, LocalDateTime exhibitStartDate, LocalDateTime exhibitEndDate) {
        this.exhibitAreaId = exhibitAreaId;
        this.exhibitorId = exhibitorId;
        this.exhibitName = exhibitName;
        this.exhibitStartDate = exhibitStartDate;
        this.exhibitEndDate = exhibitEndDate;
    }

    public Exhibit(Integer exhibitId, Integer exhibitAreaId, Integer exhibitorId, String exhibitName, LocalDateTime exhibitStartDate, LocalDateTime exhibitEndDate) {
        this.exhibitId = exhibitId;
        this.exhibitAreaId = exhibitAreaId;
        this.exhibitorId = exhibitorId;
        this.exhibitName = exhibitName;
        this.exhibitStartDate = exhibitStartDate;
        this.exhibitEndDate = exhibitEndDate;
    }

    public Integer getExhibitId() {
        return exhibitId;
    }

    public Integer getExhibitAreaId() {
        return exhibitAreaId;
    }

    public Integer getExhibitorId() {
        return exhibitorId;
    }

    public String getExhibitName() {
        return exhibitName;
    }

    public LocalDateTime getExhibitStartDate() {
        return exhibitStartDate;
    }

    public LocalDateTime getExhibitEndDate() {
        return exhibitEndDate;
    }

    public void setExhibitId(Integer exhibitId) {
        this.exhibitId = exhibitId;
    }

    public void setExhibitName(String exhibitName) {
        this.exhibitName = exhibitName;
        new ExhibitDAO().updateName(exhibitId, exhibitName);
    }

    public void setExhibitStartDate(LocalDateTime exhibitStartDate) {
        this.exhibitStartDate = exhibitStartDate;
    }

    public void setExhibitEndDate(LocalDateTime exhibitEndDate) {
        this.exhibitEndDate = exhibitEndDate;
    }

    @Override
    public String toString() {
        return "Exhibit{" +
                "exhibitId=" + exhibitId +
                ", exhibitAreaId=" + exhibitAreaId +
                ", exhibitorId=" + exhibitorId +
                ", exhibitName='" + exhibitName + '\'' +
                ", exhibitStartDate=" + exhibitStartDate +
                ", exhibitEndDate=" + exhibitEndDate +
                '}';
    }
}
