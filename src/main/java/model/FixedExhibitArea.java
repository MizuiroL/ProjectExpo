package model;

import jdbc.ExhibitAreaDAO;
import jdbc.ExhibitDAO;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/*
    This class identifies a space 'unit' likely to be occupied by an exhibitor
    It contains a list of all of its occupancies (0..*)
 */
@Entity
@Table(name = "exhibitArea")
public class FixedExhibitArea implements ExhibitArea {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "exhibitAreaId")
    private Integer exhibitAreaId;
	@ManyToOne
	@JoinColumn(name = "expoId")
	private Expo expo;
    
    public Integer getExhibitAreaId() {
		return exhibitAreaId;
	}

	public void setExhibitAreaId(Integer exhibitAreaId) {
		this.exhibitAreaId = exhibitAreaId;
	}

	public Expo getExpo() {
		return expo;
	}

	public void setExpo(Expo expo) {
		this.expo = expo;
	}

	// TODO reduce number of arguments
    public Exhibit occupy(Exhibit exhibit) {
        //new Exhibit(this.getExhibitAreaId(), exhibitor.getExhibitorId(), exhibitName, start, end);
        return new ExhibitDAO().newExhibit(exhibit);
    }

    public boolean isOccupied(LocalDateTime start, LocalDateTime end) {
        List<Exhibit> exhibitList = new ExhibitAreaDAO().getExhibitsByExhibitAreaId(this.exhibitAreaId);
        
        for (Exhibit e : exhibitList) {
        	LocalDateTime exhibitStartDateTime = LocalDateTime.of(e.getExhibitStartDate(), e.getExhibitStartTime());
        	LocalDateTime exhibitEndDateTime = LocalDateTime.of(e.getExhibitEndDate(), e.getExhibitEndTime());
        	
            Boolean startOverlap = start.isAfter(exhibitStartDateTime) && start.isBefore(exhibitEndDateTime);
            Boolean endOverlap = end.isAfter(exhibitStartDateTime) && end.isBefore(exhibitEndDateTime);
            Boolean completeOverlap = start.isBefore(exhibitStartDateTime) && end.isAfter(exhibitEndDateTime);

            if (startOverlap || endOverlap || completeOverlap) {
                return true;
            }
        }
        return false;
    }
}
