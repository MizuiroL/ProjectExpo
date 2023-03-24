package cli;

import jdbc.VisitorDAO;
import jdbc.ExpoDAO;
import model.Visitor;

import java.sql.SQLException;

public class VisitorCLI {
    private static VisitorContext context = null;
    public static Visitor signIn(String fiscalCode, String username, String password) {
        Visitor visitor = new VisitorDAO().getVisitorByFiscalCode(fiscalCode);
        if (visitor == null) {
            System.out.println("User not found.\nSign in failed.");
        }
        return visitor;
    }
    public static void main(String[] args) {
        context = new VisitorContext();
        context.setExpo(new ExpoDAO().getExpoById(1));
        System.out.println("LOGIN\nPlease input the correct data to access");
        do {
            context.setVisitor(signIn("ABC123", "", ""));
        } while (context.getVisitor() == null);
        System.out.println("Welcome\n" + context.getVisitor().toString());
    }
}
