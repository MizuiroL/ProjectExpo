package model;

import jdbc.ExhibitorDataAccess;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Exhibitor {
    private Integer exhibitorId;
    private String exhibitorName;
    private List<Exhibit> exhibitList;

    public Exhibitor(Integer exhibitorId, String exhibitorName) {
        this.exhibitorId = exhibitorId;
        this.exhibitorName = exhibitorName;
        this.exhibitList = new ArrayList<Exhibit>();
    }

    public Integer getExhibitorId() {
        return exhibitorId;
    }

    public String getExhibitorName() {
        return exhibitorName;
    }

    public List<Exhibit> getExhibitList() throws SQLException {
        if (exhibitList.isEmpty()) {
            exhibitList = new ExhibitorDataAccess().getExhibitsByExhibitorId(this.getExhibitorId());
        }
        return exhibitList;
    }

    public void printExhibits() throws SQLException {
        for (Exhibit e : this.getExhibitList()) {
            System.out.println(e);
        }
    }

    public Exhibit bookExhibitArea(Expo expo, LocalDateTime start, LocalDateTime end) throws SQLException {
        Exhibit exhibit = expo.concedeExhibitArea(this, start, end);
        if (exhibit != null) {
            exhibitList.add(exhibit);
        }
        return exhibit;
    }

    @Override
    public String toString() {
        return "Exhibitor{" +
                "exhibitorId=" + exhibitorId +
                ", exhibitorName='" + exhibitorName + '\'' +
                '}';
    }
}
