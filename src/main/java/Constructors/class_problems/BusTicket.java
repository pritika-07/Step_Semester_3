package Constructors.class_problems;
import java.util.Scanner;
public class BusTicket {
    private String passengerName;
    private String destination;
    private boolean checkedIn;
    public BusTicket(String passengerName, String destination) {
        if (passengerName == null || passengerName.trim().isEmpty())
            throw new IllegalArgumentException("Invalid passenger name");
        if (destination == null || destination.trim().isEmpty())
            throw new IllegalArgumentException("Invalid destination");
        for (int i = 0; i < passengerName.length(); i++) {
            char ch = passengerName.charAt(i);
            if (!Character.isLetter(ch) && ch != ' ')
                throw new IllegalArgumentException("Invalid passenger name");
        }
        this.passengerName = passengerName.trim();
        this.destination = destination.trim();
    }
    public void markCheckedIn() {
        if (checkedIn)
            throw new IllegalStateException("Already checked in");
        checkedIn = true;
    }
    static void processBatch(String[][] rawBookings) {
        BusTicket[] accepted = new BusTicket[rawBookings.length];
        int valid = 0;
        int rejected = 0;
        int duplicates = 0;
        for (int i = 0; i < rawBookings.length; i++) {
            String name = rawBookings[i][0];
            String destination = rawBookings[i][1];
            if (name == null || destination == null || name.trim().isEmpty() || destination.trim().isEmpty()) {
                rejected++;
                continue;
            }
            boolean duplicate = false;
            for (int j = 0; j < valid; j++) {
                if (accepted[j].passengerName.equals(name.trim()) && accepted[j].destination.equals(destination.trim())) {
                    duplicate = true;
                    break;
                }
            }
            if (duplicate) {
                duplicates++;
                continue;
            }
            try {
                accepted[valid] = new BusTicket(name, destination);
                valid++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        System.out.println("Valid: " + valid);
        System.out.println("Rejected: " + rejected);
        System.out.println("Duplicates skipped: " + duplicates);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of bookings: ");
        int n = sc.nextInt();
        sc.nextLine();
        String[][] bookings = new String[n][2];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter passenger name: ");
            bookings[i][0] = sc.nextLine();
            System.out.print("Enter destination: ");
            bookings[i][1] = sc.nextLine();
        }
        processBatch(bookings);
        sc.close();
    }
}