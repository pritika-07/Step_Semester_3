package Constructors.assignment_problems;
import java.util.Scanner;
class DeliveryAccount {
    protected String studentId;
    protected double orderValue;
    static double minimumSurgePercent;
    static {
        minimumSurgePercent = 1.0;
    }
    public DeliveryAccount(String studentId, double orderValue) {
        this.studentId = studentId;
        this.orderValue = orderValue;
    }
    public DeliveryAccount(String studentId) {
        this(studentId, 0);
    }
    public final double calculateSurgeFee(int delayMinutes) {
        if (orderValue < 0 || delayMinutes < 0)
            throw new IllegalArgumentException("Invalid input");
        if (delayMinutes == 0)
            return 0;
        double fee;
        if (delayMinutes <= 5)
            fee = orderValue * 0.005 * delayMinutes;
        else if (delayMinutes <= 15)
            fee = orderValue * 0.005 * 5 + orderValue * 0.01 * (delayMinutes - 5);
        else
            fee = orderValue * 0.005 * 5 + orderValue * 0.01 * 10 + orderValue * 0.02 * (delayMinutes - 15);
        double minimumFee = orderValue * minimumSurgePercent / 100;
        return Math.max(fee, minimumFee);
    }
}
class Premium extends DeliveryAccount {
    public Premium(String studentId, double orderValue) {
        super(studentId, orderValue);
    }
    public Premium(String studentId) {
        super(studentId);
    }
}
public class FleetReconciliation {
    static double processAccount(DeliveryAccount account, double amount, int delayMinutes) {
        if (account == null)
            return 0;
        double fee = account.calculateSurgeFee(delayMinutes);
        if (account instanceof Premium)
            System.out.println("Premium account: " + account.studentId + " | Amount: " + amount + " | Surge Fee: " + fee);
        else
            System.out.println("Regular account: " + account.studentId + " | Amount: " + amount + " | Surge Fee: " + fee);
        return fee;
    }
    static void processBatch(DeliveryAccount[] accounts, double[] amounts, int[] delayMinutesArray) {
        int n = Math.min(accounts.length, Math.min(amounts.length, delayMinutesArray.length));
        int processed = 0;
        int nullSkipped = 0;
        int premium = 0;
        int regular = 0;
        double totalFee = 0;
        for (int i = 0; i < n; i++) {
            if (accounts[i] == null) {
                nullSkipped++;
                continue;
            }
            totalFee += processAccount(accounts[i], amounts[i], delayMinutesArray[i]);
            processed++;
            if (accounts[i] instanceof Premium)
                premium++;
            else
                regular++;
        }
        System.out.println("Processed: " + processed);
        System.out.println("Null skipped: " + nullSkipped);
        System.out.println("Premium: " + premium);
        System.out.println("Regular: " + regular);
        System.out.println("Grand total surge fees: " + totalFee);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of accounts: ");
        int n = sc.nextInt();
        sc.nextLine();
        DeliveryAccount[] accounts = new DeliveryAccount[n];
        double[] amounts = new double[n];
        int[] delays = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter 1 for Premium, 2 for Regular, 0 for null: ");
            int type = sc.nextInt();
            if (type == 0) {
                accounts[i] = null;
                continue;
            }
            sc.nextLine();
            System.out.print("Enter student ID: ");
            String id = sc.nextLine();
            System.out.print("Enter order value: ");
            double value = sc.nextDouble();
            System.out.print("Enter amount: ");
            amounts[i] = sc.nextDouble();
            System.out.print("Enter delay minutes: ");
            delays[i] = sc.nextInt();
            if (type == 1)
                accounts[i] = new Premium(id, value);
            else
                accounts[i] = new DeliveryAccount(id, value);
        }
        processBatch(accounts, amounts, delays);
        sc.close();
    }
}