package cli;

import model.Exhibitor;
import model.Expo;

public class ExhibitorContext {
    private Exhibitor exhibitor;
    private Expo expo;

    public Exhibitor getExhibitor() {
        return exhibitor;
    }

    public void setExhibitor(Exhibitor exhibitor) {
        this.exhibitor = exhibitor;
    }

    public Expo getExpo() {
        return expo;
    }

    public void setExpo(Expo expo) {
        this.expo = expo;
    }
}
