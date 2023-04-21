package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "expo")
public class ExpoManager implements Expo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expoId")
    private Integer expoId;
    @Column(name = "province")
    private String province;
    @Column(name = "comune")
    private String comune;
    @Column(name = "address")
    private String address;
    @Column(name = "streetNumber")
    private String streetNumber;
    @Column(name = "expoStartDate")
    private LocalDate startDate;
    @Column(name = "expoEndDate")
    private LocalDate endDate;
    @OneToMany(mappedBy = "expo")
    private List<FixedExhibitArea> exhibitAreaList;

    public ExpoManager(Integer expoId, String province, String comune, String address, String streetNumber, LocalDate startDate, LocalDate endDate) {
        this.expoId = expoId;
        this.province = province;
        this.comune = comune;
        this.address = address;
        this.streetNumber = streetNumber;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ExpoManager() {
    	exhibitAreaList = new ArrayList<>();
    }

	public Integer getExpoId() {
		return expoId;
	}

	public String getProvince() {
		return province;
	}

	public String getComune() {
		return comune;
	}

	public String getAddress() {
		return address;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public List<FixedExhibitArea> getExhibitAreaList() {
		return exhibitAreaList;
	}
	

	public void setExpoId(Integer expoId) {
		this.expoId = expoId;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public void setExhibitAreaList(List<FixedExhibitArea> exhibitAreaList) {
		this.exhibitAreaList = exhibitAreaList;
	}

	@Override
    public Exhibit assignExhibitArea(Exhibitor exhibitor, LocalDateTime start, LocalDateTime end) {
        for (FixedExhibitArea ea : exhibitAreaList) {
        	
            if (!ea.isOccupied(start, end)) {
            	
            	Exhibit exhibit = new Exhibit();
            	exhibit.setExhibitor(exhibitor);
            	exhibit.setExhibitArea(ea);
            	exhibit.setExhibitName(exhibitor.getExhibitorName()); // TODO Temporanea
            	exhibit.setExhibitStartDate(start.toLocalDate());
            	exhibit.setExhibitStartTime(start.toLocalTime());
            	exhibit.setExhibitEndDate(end.toLocalDate());
            	exhibit.setExhibitEndTime(end.toLocalTime());
            	
                return ea.occupy(exhibit);
            }
        }
        return null;
    }
}
