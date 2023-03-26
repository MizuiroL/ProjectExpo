package cli;

import jdbc.VisitorDAO;
import jdbc.ExhibitDAO;
import jdbc.ExpoDAO;
import model.Visitor;
import model.Exhibit;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class VisitorCLI {
    private static VisitorContext context = null;
    public static Visitor signIn(String fiscalCode, String username, String password) {
        Visitor visitor = new VisitorDAO().getVisitorByFiscalCode(fiscalCode);
        if (visitor == null) {
            System.out.println("User not found.\nSign in failed.");
        }
        return visitor;
    }
    private static void viewCurrentExhibits() {
    	List<Exhibit> exhibitList = new ExpoDAO().getStartedExhibits(context.getExpo().getExpoId());
    	for (Exhibit e : exhibitList) {
    		System.out.println(e.toString());
    	}
    }
    
    private static void viewCurrentEvents() {
    	
    }
    public static void main(String[] args) {
        context = new VisitorContext();
        context.setExpo(new ExpoDAO().getExpoById(1));
        System.out.println("LOGIN\nPlease input the correct data to access");
        do {
            context.setVisitor(signIn("ABC123", "", ""));
        } while (context.getVisitor() == null);
        System.out.println("Welcome\n" + context.getVisitor().toString());
        
        int input;
        do {
            System.out.println("Please enter a valid input");
            System.out.println("0: Exit program");
            System.out.println("1: View all exhibits");
            System.out.println("2: View all events");
            System.out.println("3: View your booked events");
            System.out.println("4: Book event");
            input = new Scanner(System.in).nextInt();
            switch (input) {
                case 1:
                    viewCurrentExhibits();
                    break;
                case 2:
                    viewCurrentEvents();
                    break;
                case 3:
                    //viewBookedEvents();
                    break;
                case 4:
                	//bookEvent();
                	break;
            }
        } while (input != 0);
    }
}
