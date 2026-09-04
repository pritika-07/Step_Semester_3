package Constructors.assignment_problems;
import java.util.Scanner;
public class Canteen {
    private String canteenCode;
    private String canteenName;
    private int trustScore;
    public Canteen(String canteenCode, String canteenName, int trustScore) {
        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }
    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, 3);
    }
    int compareTo(Canteen other) {
        if (trustScore != other.trustScore)
            return other.trustScore - trustScore;
        int code = canteenCode.compareToIgnoreCase(other.canteenCode);
        if (code != 0)
            return code;
        return canteenName.length() - other.canteenName.length();
    }
    static Canteen[] rankCanteens(Canteen[] canteens) {
        Canteen[] result = canteens.clone();
        for (int i = 0; i < result.length - 1; i++) {
            for (int j = i + 1; j < result.length; j++) {
                if (result[i].compareTo(result[j]) > 0) {
                    Canteen temp = result[i];
                    result[i] = result[j];
                    result[j] = temp;
                }
            }
        }
        return result;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of canteens: ");
        int n = sc.nextInt();
        sc.nextLine();
        Canteen[] canteens = new Canteen[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter canteen code: ");
            String code = sc.nextLine();
            System.out.print("Enter canteen name: ");
            String name = sc.nextLine();
            System.out.print("Enter trust score or -1 for default: ");
            int score = sc.nextInt();
            sc.nextLine();
            if (score == -1)
                canteens[i] = new Canteen(code, name);
            else
                canteens[i] = new Canteen(code, name, score);
        }
        Canteen[] ranked = rankCanteens(canteens);
        for (int i = 0; i < ranked.length; i++)
            System.out.println(ranked[i].canteenCode + " - " + ranked[i].canteenName + " - " + ranked[i].trustScore);
        sc.close();
    }
} 