package cli;

import model.Visitor;
import model.Expo;

public class VisitorContext {
    private Visitor visitor;
    private Expo expo;

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public Expo getExpo() {
        return expo;
    }

    public void setExpo(Expo expo) {
        this.expo = expo;
    }
}
