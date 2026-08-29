package javabasics.class_problems;
import java.util.Scanner;
import java.util.Random;
public class RockPaperScissors {
    static String playRound(String playerMove, String computerMove) {
        if (playerMove.equals(computerMove))
            return "Draw";
        if ((playerMove.equals("Rock") && computerMove.equals("Scissors")) || (playerMove.equals("Paper") && computerMove.equals("Rock")) || (playerMove.equals("Scissors") && computerMove.equals("Paper")))
            return "Player Wins";
        return "Computer Wins";
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        String[] moves = {"Rock", "Paper", "Scissors"};
        System.out.print("Enter number of rounds: ");
        int n = sc.nextInt();
        String[] playerMoves = new String[n];
        String[] computerMoves = new String[n];
        String[] results = new String[n];
        int wins = 0, losses = 0, draws = 0;
        for (int i = 0; i < n; i++) {
            System.out.print("Enter your move (Rock/Paper/Scissors): ");
            playerMoves[i] = sc.next();
            computerMoves[i] = moves[random.nextInt(3)];
            results[i] = playRound(playerMoves[i], computerMoves[i]);
            if (results[i].equals("Player Wins"))
                wins++;
            else if (results[i].equals("Computer Wins"))
                losses++;
            else
                draws++;
            System.out.println("Computer: " + computerMoves[i]);
            System.out.println("Result: " + results[i]);
        }
        double winPercentage = (wins * 100.0) / n;
        System.out.println("\nFinal Summary");
        System.out.println("Round\tPlayer Move\tComputer Move\tResult");
        for (int i = 0; i < n; i++)
            System.out.println((i + 1) + "\t" + playerMoves[i] + "\t\t" + computerMoves[i] + "\t\t" + results[i]);
        System.out.println("Wins: " + wins);
        System.out.println("Losses: " + losses);
        System.out.println("Draws: " + draws);
        System.out.println("Win Percentage: " + winPercentage + "%");
        sc.close();
    }
}