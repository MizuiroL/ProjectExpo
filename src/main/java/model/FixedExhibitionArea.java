package model;

import jdbc.ExhibitionAreaDataAccess;
import jdbc.ExhibitionDataAccess;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/*
    This class identifies a space 'unit' likely to be occupied by an exhibitor
    It contains a list of all of its occupancies (0..*)
 */
public class FixedExhibitionArea implements ExhibitionArea {
    private Integer exhibitionAreaId;
    private Integer expoId;
    private ExhibitionAreaDataAccess exhibitionAreaDataAccess;

    public FixedExhibitionArea(Integer exhibitionAreaId, Integer expoId) {
        this.exhibitionAreaId = exhibitionAreaId;
        this.expoId = expoId;
        exhibitionAreaDataAccess = new ExhibitionAreaDataAccess();
    }

    public Integer getExhibitionAreaId() {
        return exhibitionAreaId;
    }

    public Integer getExpoId() {
        return expoId;
    }

    public ExhibitionAreaDataAccess getExhibitionAreaDataAccess() {
        return exhibitionAreaDataAccess;
    }

    public Exhibition occupy(Exhibitor exhibitor, String exhibitionName, LocalDateTime start, LocalDateTime end) throws SQLException {
        Exhibition exhibition = new Exhibition(this.getExhibitionAreaId(), exhibitor.getExhibitorId(), exhibitionName, start, end);
        return new ExhibitionDataAccess().newExhibition(exhibition);
    }

    public boolean isOccupied(LocalDateTime start, LocalDateTime end) throws SQLException {
        List<Exhibition> exhibitionList = exhibitionAreaDataAccess.getExhibitionsByExhibitionAreaId(this.exhibitionAreaId);
        for(Exhibition o : exhibitionList) {
            if(start.isAfter(o.getExhibitionStartDate()) && start.isBefore(o.getExhibitionEndDate()) || end.isAfter(o.getExhibitionStartDate()) && end.isBefore(o.getExhibitionEndDate())) {
                return true;
            }
        }
        return false;
    }
}
