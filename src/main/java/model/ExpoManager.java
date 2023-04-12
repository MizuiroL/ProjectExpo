package model;

import jdbc.ExpoDAO;

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
import jakarta.persistence.Transient;

@Entity
@Table(name = "expo")
public class ExpoManager implements Expo {
	@Transient
    private final ExpoDAO expoDataAccess;
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
    @Column(name = "startDate")
    private LocalDate startDate;
    @Column(name = "endDate")
    private LocalDate endDate;
    @OneToMany(mappedBy = "expo")
    private List<ExhibitArea> exhibitAreaList;

    public ExpoManager(Integer expoId, String province, String comune, String address, String streetNumber, LocalDate startDate, LocalDate endDate) {
        this.expoId = expoId;
        this.province = province;
        this.comune = comune;
        this.address = address;
        this.streetNumber = streetNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        expoDataAccess = new ExpoDAO();
    }

    public ExpoManager() {
        expoDataAccess = new ExpoDAO();
    }

    public ExpoDAO getExpoDataAccess() {
		return expoDataAccess;
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

	public List<ExhibitArea> getExhibitAreaList() {
		return exhibitAreaList;
	}

	@Override
    public Exhibit assignExhibitArea(Exhibitor exhibitor, LocalDateTime start, LocalDateTime end) {
        for (ExhibitArea ea : exhibitAreaList) {
        	
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
