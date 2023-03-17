package model;

import jdbc.ExpoDataAccess;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ExpoManager implements Expo {
    private final ExpoDataAccess expoDataAccess;
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
        expoDataAccess = new ExpoDataAccess();
    }

    public ExpoManager() {
        expoDataAccess = new ExpoDataAccess();
    }

    @Override
    public Exhibit concedeExhibitArea(Exhibitor exhibitor, LocalDateTime start, LocalDateTime end) {
        List<ExhibitArea> exhibitAreaList = expoDataAccess.getExhibitAreasByExpoId(this.expoId);
        for (ExhibitArea ea : exhibitAreaList) {
            if (!ea.isOccupied(start, end)) {
                return ea.occupy(exhibitor, exhibitor.getExhibitorName(), start, end);
            }
        }
        return null;
    }
}
