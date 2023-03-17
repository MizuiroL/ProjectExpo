package cli;

import jdbc.ExhibitorDataAccess;
import jdbc.ExpoDataAccess;
import model.Exhibitor;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ExhibitorCLI {
    private static ExhibitorContext context = null;

    public static Exhibitor signIn(Integer exhibitorId, String username, String password) throws SQLException {
        Exhibitor exhibitor = new ExhibitorDataAccess().getExhibitorById(exhibitorId);
        if (exhibitor == null) {
            System.out.println("User not found.\nSign in failed.");
        }
        return exhibitor;
    }

    public static void newExhibit() {
        int input;
        do {
            System.out.println("0: Back\n1: New Exhibit\n2: New Event");
            input = new Scanner(System.in).nextInt();
            switch (input) {
                case 1:
                    bookExhibitArea();
                    break;
                case 2:
                    System.out.println("WORK IN PROGRESS");
                    break;
            }
        } while (input > 2);
    }

    public static void bookExhibitArea() {
        System.out.println("Input the start LocalDateTime");
        LocalDateTime start = getDateTimeData();
        System.out.println("Input the end LocalDateTime");
        LocalDateTime end = getDateTimeData();
        context.getExhibitor().bookExhibitArea(context.getExpo(), start, end);
    }

    public static LocalDateTime getDateTimeData() {
        Scanner in = new Scanner(System.in);
        int year;
        int month;
        int day;
        int hour;
        int minute;
        System.out.println("Year");
        year = in.nextInt();
        System.out.println("Month (int)");
        month = in.nextInt();
        System.out.println("Day");
        day = in.nextInt();
        System.out.println("Hour");
        hour = in.nextInt();
        System.out.println("Minute");
        minute = in.nextInt();
        return LocalDateTime.of(year, month, day, hour, minute);
    }

    public static void main(String[] args) throws SQLException {
        context = new ExhibitorContext();
        context.setExpo(new ExpoDataAccess().getExpoById(1));
        System.out.println("LOGIN\nPlease input the correct data to access");
        do {
            context.setExhibitor(signIn(1, "", ""));
        } while (context.getExhibitor() == null);
        System.out.println("Welcome\n" + context.getExhibitor().toString());

        // CONSOLE LOOP
        int input;
        do {
            System.out.println("Please enter a valid input");
            System.out.println("0: Exit program");
            System.out.println("1: View your exhibits");
            System.out.println("2: Create new exhibit");
            input = new Scanner(System.in).nextInt();
            switch (input) {
                case 1:
                    context.getExhibitor().printExhibits();
                    break;
                case 2:
                    newExhibit();
                    break;
            }
        } while (input != 0);

    }
}
