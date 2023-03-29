package model;

import jdbc.ExpoDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ExpoManager implements Expo {
    private final ExpoDAO expoDataAccess;
    private Integer expoId;
    private String province;
    private String comune;
    private String address;
    private String streetNumber;
    private LocalDate startDate;
    private LocalDate endDate;
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
        List<ExhibitArea> exhibitAreaList = expoDataAccess.getExhibitAreasByExpoId(this.expoId);
        for (ExhibitArea ea : exhibitAreaList) {
            if (!ea.isOccupied(start, end)) {
                return ea.occupy(exhibitor, exhibitor.getExhibitorName(), start, end);
            }
        }
        return null;
    }
}
