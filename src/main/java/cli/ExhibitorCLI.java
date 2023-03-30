package cli;

import jdbc.ExhibitDAO;
import jdbc.ExhibitorDAO;
import jdbc.ExpoDAO;
import model.Exhibit;
import model.Exhibitor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ExhibitorCLI {
	private static ExhibitorContext context = null;

	public static Exhibitor signIn(Integer exhibitorId, String username, String password) {
		Exhibitor exhibitor = new ExhibitorDAO().getExhibitorById(exhibitorId);
		if (exhibitor == null) {
			System.out.println("User not found.\nSign in failed.");
		}
		return exhibitor;
	}

	public static void newExhibit() {
		// TODO find a better naming for this
		int input;
		do {
			System.out.println("0: Back\n1: New Exhibit\n2: New Event");
			// TODO assign Scanner to dedicated variable
			Scanner scanner = new Scanner(System.in);
			input = scanner.nextInt();
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
		in.close();
		LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);
		return dateTime;
	}

	private static void modifyExhibit() {
		System.out.println("Please input the exhibitId to access it");
		Scanner in = new Scanner(System.in);
		int id = in.nextInt();
		Exhibit exhibit = new ExhibitDAO().getExhibitById(id);
		System.out.println(exhibit.toString());
		System.out.println("What do you want to change?");
		System.out.println("0: Go back");
		System.out.println("1: Change starting date");
		System.out.println("2: Change ending date");
		System.out.println("3: Delete exhibit");
		int option = in.nextInt();
		switch (option) {
		case 1:
			context.getExhibitor().setExhibitStartDate(exhibit, getDateTimeData());
			// exhibit.setExhibitStartDate(getDateTimeData());
			break;
		case 2:
			exhibit.setExhibitEndDate(getDateTimeData());
		case 3:
			context.getExhibitor().removeExhibit(exhibit);
			break;
		}
		in.close();
	}

	private static void checkNotifications() {
		List<ExhibitorNotification> notificationList = new ExhibitorDAO()
				.selectAllNotifications(context.getExhibitor().getExhibitorId());
		if (notificationList == null) {
			System.out.println("You have no notifications\n");
		} else {
			for (ExhibitorNotification notification : notificationList) {
				System.out.println(notification.toString());
				System.out.println();
			}
		}
	}

	public static void main(String[] args) {
		context = new ExhibitorContext();
		context.setExpo(new ExpoDAO().getExpoById(1));
		System.out.println("LOGIN\nPlease input the correct data to access");
		do {
			// TODO make this an actual login
			System.out.println("Input the exhibitorId");
			Scanner scanner = new Scanner(System.in);
			int exhibitorId = scanner.nextInt();
			context.setExhibitor(signIn(exhibitorId, "", ""));
		} while (context.getExhibitor() == null);
		System.out.println("Welcome\n" + context.getExhibitor().toString());
		System.out.println("Checking new notifications");
		checkNotifications();
		// CONSOLE LOOP
		int input;
		do {
			System.out.println("Please enter a valid input");
			System.out.println("0: Exit program");
			System.out.println("1: View your exhibits");
			System.out.println("2: Create new exhibit");
			System.out.println("3: Modify existing exhibit");
			Scanner scanner = new Scanner(System.in);
			input = scanner.nextInt();
			switch (input) {
			case 1:
				context.getExhibitor().printExhibits();
				break;
			case 2:
				newExhibit();
				break;
			case 3:
				modifyExhibit();
				break;
			}
		} while (input != 0);

	}
}
