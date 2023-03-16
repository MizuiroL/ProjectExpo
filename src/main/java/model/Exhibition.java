package model;

import java.time.LocalDateTime;

public class Exhibition {
    Integer exhibitionId;
    Integer exhibitionAreaId;
    Integer exhibitorId;
    String exhibitionName;
    LocalDateTime exhibitionStartDate;
    LocalDateTime exhibitionEndDate;

    // Constructor without primary key
    public Exhibition(Integer exhibitionAreaId, Integer exhibitorId, String exhibitionName, LocalDateTime exhibitionStartDate, LocalDateTime exhibitionEndDate) {
        this.exhibitionAreaId = exhibitionAreaId;
        this.exhibitorId = exhibitorId;
        this.exhibitionName = exhibitionName;
        this.exhibitionStartDate = exhibitionStartDate;
        this.exhibitionEndDate = exhibitionEndDate;
    }
    public Exhibition(Integer exhibitionId, Integer exhibitionAreaId, Integer exhibitorId, String exhibitionName, LocalDateTime exhibitionStartDate, LocalDateTime exhibitionEndDate) {
        this.exhibitionId = exhibitionId;
        this.exhibitionAreaId = exhibitionAreaId;
        this.exhibitorId = exhibitorId;
        this.exhibitionName = exhibitionName;
        this.exhibitionStartDate = exhibitionStartDate;
        this.exhibitionEndDate = exhibitionEndDate;
    }

    public Integer getExhibitionId() {
        return exhibitionId;
    }

    public Integer getExhibitionAreaId() {
        return exhibitionAreaId;
    }

    public Integer getExhibitorId() {
        return exhibitorId;
    }

    public String getExhibitionName() {
        return exhibitionName;
    }

    public LocalDateTime getExhibitionStartDate() {
        return exhibitionStartDate;
    }

    public LocalDateTime getExhibitionEndDate() {
        return exhibitionEndDate;
    }

    public void setExhibitionId(Integer exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    @Override
    public String toString() {
        return "Exhibition{" +
                "exhibitionId=" + exhibitionId +
                ", exhibitionAreaId=" + exhibitionAreaId +
                ", exhibitorId=" + exhibitorId +
                ", exhibitionName='" + exhibitionName + '\'' +
                ", exhibitionStartDate=" + exhibitionStartDate +
                ", exhibitionEndDate=" + exhibitionEndDate +
                '}';
    }
}
