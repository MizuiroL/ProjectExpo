package model;

import jdbc.ExpoDataAccess;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ExpoManager implements Expo {
    private ExpoDataAccess expoDataAccess;
    private Integer expoId;
    private String province;
    private String comune;
    private String address;
    private String streetNumber;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<ExhibitionArea> exhibitionAreaList;

    public ExpoManager(Integer expoId, String province, String comune, String address, String streetNumber, LocalDateTime startDate, LocalDateTime endDate) {
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
    public Exhibition concedeExhibitionArea(Exhibitor exhibitor, LocalDateTime start, LocalDateTime end) throws SQLException {
        List<ExhibitionArea> exhibitionAreaList = expoDataAccess.getExhibitionAreasByExpoId(this.expoId);
        for (ExhibitionArea ea : exhibitionAreaList) {
            if (!ea.isOccupied(start, end)) {
                return ea.occupy(exhibitor, "", start, end);
            }
        }
        return null;
    }
}
