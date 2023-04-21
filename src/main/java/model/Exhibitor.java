package model;

import jdbc.ExhibitDAO;
import jdbc.ExhibitorDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "exhibitor")
public class Exhibitor implements EventObserver {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "exhibitorId")
    private Integer exhibitorId;
	@Column(name = "exhibitorName")
    private String exhibitorName;
	@OneToMany(mappedBy = "exhibitor")
    private List<Exhibit> exhibitList;

    public Integer getExhibitorId() {
        return exhibitorId;
    }

    public String getExhibitorName() {
        return exhibitorName;
    }

    public void setExhibitorId(Integer exhibitorId) {
		this.exhibitorId = exhibitorId;
	}

	public void setExhibitorName(String exhibitorName) {
		this.exhibitorName = exhibitorName;
	}

	public List<Exhibit> getExhibitList() {
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
        exhibitList.remove(exhibit);
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
