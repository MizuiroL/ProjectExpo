package model;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<Exhibition> getExhibitionList() {
        return exhibitionList;
    }

    public boolean bookExhibitionArea(Expo expo, LocalDateTime start, LocalDateTime end) throws SQLException {
        Exhibition exhibition = expo.concedeExhibitionArea(this, start, end);
        if(exhibition != null) {
            exhibitionList.add(exhibition);
            return true;
        } else {
            return false;
        }
    }
}
