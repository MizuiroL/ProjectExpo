package model;

import jdbc.ExhibitionDataAccess;
import jdbc.ExhibitorDataAccess;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Exhibitor {
    private Integer exhibitorId;
    private String exhibitorName;
    private List<Exhibition> exhibitionList;

    public Exhibitor(Integer exhibitorId, String exhibitorName) {
        this.exhibitorId = exhibitorId;
        this.exhibitorName = exhibitorName;
        this.exhibitionList = new ArrayList<Exhibition>();
    }

    public Integer getExhibitorId() {
        return exhibitorId;
    }

    public String getExhibitorName() {
        return exhibitorName;
    }

    public List<Exhibition> getExhibitionList() throws SQLException {
        if (exhibitionList.isEmpty()) {
            exhibitionList = new ExhibitorDataAccess().getExhibitionsByExhibitorId(this.getExhibitorId());
        }
        return exhibitionList;
    }

    public void printExhibitions() throws SQLException {
        for (Exhibition e : this.getExhibitionList()) {
            System.out.println(e);
        }
    }

    public Exhibition bookExhibitionArea(Expo expo, LocalDateTime start, LocalDateTime end) throws SQLException {
        Exhibition exhibition = expo.concedeExhibitionArea(this, start, end);
        if (exhibition != null) {
            exhibitionList.add(exhibition);
            exhibition = new ExhibitionDataAccess().newExhibition(exhibition);
        }
        return exhibition;
    }

    @Override
    public String toString() {
        return "Exhibitor{" +
                "exhibitorId=" + exhibitorId +
                ", exhibitorName='" + exhibitorName + '\'' +
                '}';
    }
}
