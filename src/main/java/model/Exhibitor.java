package model;

import jdbc.ExhibitDAO;
import jdbc.ExhibitorDAO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Exhibitor implements EventObserver {
    private final Integer exhibitorId;
    private final String exhibitorName;
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

    public List<Exhibit> getExhibitList() {
        if (exhibitList.isEmpty()) {
            exhibitList = new ExhibitorDAO().getExhibitsByExhibitorId(this.getExhibitorId());
        }
        return exhibitList;
    }

    public Exhibit getExhibitById(Integer id) {
        for (Exhibit e : exhibitList) {
            if (e.getExhibitId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    public void printExhibits() {
        for (Exhibit e : this.getExhibitList()) {
            System.out.println(e);
        }
    }

    public Exhibit bookExhibitArea(Expo expo, LocalDateTime start, LocalDateTime end) {
        Exhibit exhibit = expo.assignExhibitArea(this, start, end);
        if (exhibit != null) {
            exhibitList.add(exhibit);
        }
        return exhibit;
    }

    @Override
    public String toString() {
        return "Exhibitor{" + "exhibitorId=" + exhibitorId + ", exhibitorName='" + exhibitorName + '\'' + '}';
    }

    public void removeExhibit(Exhibit exhibit) {
        boolean correctExhibitor = exhibit.getExhibitorId().equals(this.exhibitorId);
        boolean beforeExhibitDate = LocalDateTime.now().isBefore(exhibit.exhibitStartDate);
        if (correctExhibitor && beforeExhibitDate) {
            exhibitList.remove(exhibit);
            new ExhibitDAO().removeExhibit(exhibit);
        } else {
            System.out.println("Failed delete:");
            System.out.println(!correctExhibitor ? "Unauthorized delete" : "Cannot delete an exhibit after its start date");
        }
    }

    public Exhibit setExhibitStartDate(Exhibit exhibit, LocalDateTime exhibitStartDate) {
        //exhibitList.replaceAll(e -> e.getExhibitId().equals(exhibit.getExhibitId()) ? exhibit : e);
        exhibit = getExhibitById(exhibit.getExhibitId());
        exhibit.setExhibitStartDate(exhibitStartDate);
        new ExhibitDAO().updateStartDate(exhibit.getExhibitId(), exhibitStartDate);
        return exhibit;
    }

	@Override
	public void update(Event e) {
		ExhibitorDAO dao = new ExhibitorDAO();
		// Event sold out
		if(e.getEventAvailableSeats() == 0) {
			dao.newNotification(this.getExhibitorId(), "Event Sold Out: " + e.toString());
		}
		
	}
}
