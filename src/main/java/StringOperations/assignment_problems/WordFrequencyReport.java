package StringOperations.assignment_problems;
import java.util.Scanner;
public class WordFrequencyReport {
    static void printFilteredWordFrequency(String feedback) {
        feedback = feedback.toLowerCase();
        feedback = feedback.replace(".", "");
        feedback = feedback.replace(",", "");
        String[] words = feedback.split("\\s+");
        String[] stopWords = {"the", "was", "and", "a", "is", "of", "in"};
        String[] uniqueWords = new String[words.length];
        int[] count = new int[words.length];
        int n = 0;
        for (int i = 0; i < words.length; i++) {
            boolean stop = false;
            for (int j = 0; j < stopWords.length; j++) {
                if (words[i].equals(stopWords[j])) {
                    stop = true;
                    break;
                }
            }
            if (stop)
                continue;
            int pos = -1;
            for (int j = 0; j < n; j++) {
                if (uniqueWords[j].equals(words[i])) {
                    pos = j;
                    break;
                }
            }
            if (pos == -1) {
                uniqueWords[n] = words[i];
                count[n] = 1;
                n++;
            } else {
                count[pos]++;
            }
        }
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (count[j] > count[i]) {
                    int temp = count[i];
                    count[i] = count[j];
                    count[j] = temp;
                    String tempWord = uniqueWords[i];
                    uniqueWords[i] = uniqueWords[j];
                    uniqueWords[j] = tempWord;
                }
            }
        }
        for (int i = 0; i < n; i++)
            System.out.println(uniqueWords[i] + ": " + count[i]);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter feedback: ");
        String feedback = sc.nextLine();
        printFilteredWordFrequency(feedback);
        sc.close();
    }
}

