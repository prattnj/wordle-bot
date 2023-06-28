import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        WordleBot bot = new WordleBot("wordle-La.txt", "wordle-Ta.txt");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Wordle Bot!");
        System.out.println("Bot's first guess: " + bot.submit(null));

        while (true) {
            String input = scanner.next();
            // todo validate input
            String nextGuess = bot.submit(input);
            if (nextGuess == null) {
                System.out.println("No possible words remaining. Try again.");
            } else {
                if (bot.isSolved()) {
                    System.out.println("I solved it!");
                    break;
                }
                System.out.println("Next guess: " + nextGuess);
            }
        }

    }

}
