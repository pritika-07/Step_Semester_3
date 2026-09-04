package Constructors.class_problems;
import java.util.Scanner;
class BusTicketAccount {
    static double minimumPenaltyPercent;
    static {
        minimumPenaltyPercent = 1.0;
    }
    protected String bookingId;
    protected double ticketFare;
    public BusTicketAccount(String bookingId, double ticketFare) {
        this.bookingId = bookingId;
        this.ticketFare = ticketFare;
    }
    public BusTicketAccount(String bookingId) {
        this(bookingId, 0);
    }
    public final double calculatePenalty(int minutesLate) {
        if (ticketFare < 0 || minutesLate < 0)
            throw new IllegalArgumentException("Invalid input");
        if (minutesLate == 0)
            return 0;
        double penalty;
        if (minutesLate <= 5)
            penalty = ticketFare * 0.005 * minutesLate;
        else if (minutesLate <= 15)
            penalty = ticketFare * 0.005 * 5 + ticketFare * 0.01 * (minutesLate - 5);
        else
            penalty = ticketFare * 0.005 * 5 + ticketFare * 0.01 * 10 + ticketFare * 0.02 * (minutesLate - 15);
        double minimum = ticketFare * minimumPenaltyPercent / 100;
        return Math.max(penalty, minimum);
    }
}
class Sleeper extends BusTicketAccount {
    public Sleeper(String bookingId, double ticketFare) {
        super(bookingId, ticketFare);
    }
    public Sleeper(String bookingId) {
        super(bookingId);
    }
}
public class FleetReconciliation {
    static void processAccount(BusTicketAccount account, double amount, int minutesLate) {
        if (account instanceof Sleeper) {
            System.out.println("Sleeper account processed. Amount: " + amount + ", Penalty: " + account.calculatePenalty(minutesLate));
        } else {
            System.out.println("Regular account processed. Amount: " + amount + ", Penalty: " + account.calculatePenalty(minutesLate));
        }
    }
    static void processBatch(BusTicketAccount[] accounts, double[] amounts, int[] minutesLateArray) {
        int n = Math.min(accounts.length, Math.min(amounts.length, minutesLateArray.length));
        int processed = 0;
        int nullSkipped = 0;
        int sleeper = 0;
        int regular = 0;
        double totalPenalty = 0;
        for (int i = 0; i < n; i++) {
            if (accounts[i] == null) {
                nullSkipped++;
                continue;
            }
            processAccount(accounts[i], amounts[i], minutesLateArray[i]);
            totalPenalty += accounts[i].calculatePenalty(minutesLateArray[i]);
            processed++;
            if (accounts[i] instanceof Sleeper)
                sleeper++;
            else
                regular++;
        }
        System.out.println("Processed: " + processed);
        System.out.println("Null skipped: " + nullSkipped);
        System.out.println("Sleeper: " + sleeper);
        System.out.println("Regular: " + regular);
        System.out.println("Grand total penalties: " + totalPenalty);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of accounts: ");
        int n = sc.nextInt();
        BusTicketAccount[] accounts = new BusTicketAccount[n];
        double[] amounts = new double[n];
        int[] minutesLate = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter 1 for Sleeper, 2 for Regular, 0 for null: ");
            int type = sc.nextInt();
            if (type == 0) {
                accounts[i] = null;
                System.out.println("Account skipped");
                continue;
            }
            sc.nextLine();
            System.out.print("Enter booking ID: ");
            String id = sc.nextLine();
            System.out.print("Enter ticket fare: ");
            double fare = sc.nextDouble();
            System.out.print("Enter amount: ");
            amounts[i] = sc.nextDouble();
            System.out.print("Enter minutes late: ");
            minutesLate[i] = sc.nextInt();
            if (type == 1)
                accounts[i] = new Sleeper(id, fare);
            else
                accounts[i] = new BusTicketAccount(id, fare);
        }
        processBatch(accounts, amounts, minutesLate);
        sc.close();
    }
} 