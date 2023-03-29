package cli;

import jdbc.VisitorDAO;
import jdbc.EventDAO;
import jdbc.ExpoDAO;
import model.Visitor;
import model.Event;
import model.Exhibit;
import model.Ticket;

import java.util.List;
import java.util.Scanner;

public class VisitorCLI {
    private static VisitorContext context = null;
    // TODO refactoring: move service methods to dedicated classes
    public static Visitor signIn(String fiscalCode, String username, String password) {
        Visitor visitor = new VisitorDAO().getVisitorByFiscalCode(fiscalCode);
        if (visitor == null) {
            System.out.println("User not found.\nSign in failed.");
        }
        return visitor;
    }
    private static void viewCurrentExhibits() {
    	Integer id = context.getExpo().getExpoId();
    	ExpoDAO dao = new ExpoDAO();
    	List<Exhibit> exhibitList = dao.getStartedExhibits(id);
    	for (Exhibit e : exhibitList) {
    		System.out.println(e.toString());
    	}
    }
    
    private static void viewCurrentEvents() {
    	Integer id = context.getExpo().getExpoId();
    	ExpoDAO dao = new ExpoDAO();
    	List<Event> eventList = dao.getFutureEvents(id);
    	for (Event e : eventList) {
    		System.out.println(e.toString());
    	}
    }
    
    private static void viewBookedEvents() {
    	Integer expoId = context.getExpo().getExpoId();
    	String fiscalCode = context.getVisitor().getFiscalCode();
    	ExpoDAO dao = new ExpoDAO();
    	List<Event> eventList = dao.getBookedEvents(expoId, fiscalCode);
    	for (Event e : eventList) {
    		System.out.println(e.toString());
    	}
    }
    
    private static void bookEvent(Integer id) {
    	Event event = new EventDAO().getEventById(id);
    	Ticket ticket = context.getVisitor().purchaseEventTicket(event);
    	System.out.println("You purchased " + ticket.toString());
    }
    
    public static void main(String[] args) {
        context = new VisitorContext();
        context.setExpo(new ExpoDAO().getExpoById(1));
        System.out.println("LOGIN\nPlease input the correct data to access");
        do {
        	Visitor visitor = signIn("RMEG123", "", "");
            context.setVisitor(visitor);
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
            //TODO assegnare lo scanner ad una variabile dedicata
            Scanner scanner = new Scanner(System.in);
			input = scanner.nextInt();
            switch (input) {
                case 1:
                    viewCurrentExhibits();
                    break;
                case 2:
                    viewCurrentEvents();
                    break;
                case 3:
                    viewBookedEvents();
                    break;
                case 4:
                	System.out.println("Please insert the eventId to purchase");
                	int eventId = scanner.nextInt();
                	bookEvent(eventId);
                	break;
            }
            //scanner.close();
        } while (input != 0);
    }
}
