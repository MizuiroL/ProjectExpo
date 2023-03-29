package model;

import jdbc.ExhibitAreaDAO;
import jdbc.ExhibitDAO;

import java.time.LocalDateTime;
import java.util.List;

/*
    This class identifies a space 'unit' likely to be occupied by an exhibitor
    It contains a list of all of its occupancies (0..*)
 */
public class FixedExhibitArea implements ExhibitArea {
    private final Integer exhibitAreaId;
    private final Integer expoId;
    private final ExhibitAreaDAO exhibitAreaDataAccess;

    public FixedExhibitArea(Integer exhibitAreaId, Integer expoId) {
        this.exhibitAreaId = exhibitAreaId;
        this.expoId = expoId;
        exhibitAreaDataAccess = new ExhibitAreaDAO();
    }

    public Integer getExhibitAreaId() {
        return exhibitAreaId;
    }

    public Integer getExpoId() {
        return expoId;
    }

    public ExhibitAreaDAO getExhibitAreaDataAccess() {
        return exhibitAreaDataAccess;
    }
    // TODO reduce number of arguments
    public Exhibit occupy(Exhibitor exhibitor, String exhibitName, LocalDateTime start, LocalDateTime end) {
        Exhibit exhibit = new Exhibit(this.getExhibitAreaId(), exhibitor.getExhibitorId(), exhibitName, start, end);
        return new ExhibitDAO().newExhibit(exhibit);
    }

    public boolean isOccupied(LocalDateTime start, LocalDateTime end) {
        List<Exhibit> exhibitList = exhibitAreaDataAccess.getExhibitsByExhibitAreaId(this.exhibitAreaId);
        for (Exhibit o : exhibitList) {
            Boolean startOverlap = start.isAfter(o.getExhibitStartDate()) && start.isBefore(o.getExhibitEndDate());
            Boolean endOverlap = end.isAfter(o.getExhibitStartDate()) && end.isBefore(o.getExhibitEndDate());
            Boolean completeOverlap = start.isBefore(o.getExhibitStartDate()) && end.isAfter(o.getExhibitEndDate());

            if (startOverlap || endOverlap || completeOverlap) {
                return true;
            }
        }
        return false;
    }
}
