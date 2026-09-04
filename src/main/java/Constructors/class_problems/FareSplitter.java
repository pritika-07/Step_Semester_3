package Constructors.class_problems;
import java.util.Scanner;
public class FareSplitter {
    private String tripId;
    private double totalFare;
    private int passengerCount;
    public FareSplitter(String tripId, double totalFare, int passengerCount) {
        if (totalFare < 0 || passengerCount <= 0)
            throw new IllegalArgumentException("Invalid fare or passenger count");
        this.tripId = tripId;
        this.totalFare = totalFare;
        this.passengerCount = passengerCount;
    }
    public FareSplitter(String tripId, double totalFare) {
        this(tripId, totalFare, 2);
    }
    public FareSplitter(String tripId) {
        this(tripId, 0, 2);
    }
    double[] fareBreakdown() {
        double[] result = new double[passengerCount];
        if (totalFare == 0)
            return result;
        double amount = Math.floor((totalFare / passengerCount) * 100) / 100;
        double used = amount * passengerCount;
        for (int i = 0; i < passengerCount; i++)
            result[i] = amount;
        result[passengerCount - 1] += Math.round((totalFare - used) * 100) / 100.0;
        return result;
    }
    boolean isConfirmationOverdue(int confirmed, int expected) {
        return confirmed < expected;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter trip ID: ");
        String tripId = sc.nextLine();
        System.out.print("Enter total fare: ");
        double fare = sc.nextDouble();
        System.out.print("Enter passenger count: ");
        int count = sc.nextInt();
        FareSplitter split = new FareSplitter(tripId, fare, count);
        double[] result = split.fareBreakdown();
        System.out.println("Fare Breakdown:");
        for (int i = 0; i < result.length; i++)
            System.out.println("Passenger " + (i + 1) + ": " + result[i]);
        sc.close();
    }
} 